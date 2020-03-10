package ex.aaronfae.graduation.business.reimbursement.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.reimbursement.entity.ReimbursementDO;
import ex.aaronfae.graduation.business.reimbursement.entity.ReimbursementPictureDO;
import ex.aaronfae.graduation.business.reimbursement.mapper.ReimbursementMapper;
import ex.aaronfae.graduation.business.reimbursement.mapper.ReimbursementPictureMapper;
import ex.aaronfae.graduation.business.reimbursement.model.dto.ReimbursementApplyDTO;
import ex.aaronfae.graduation.business.reimbursement.model.vo.ReimbursementVO;
import ex.aaronfae.graduation.business.reimbursement.service.ReimbursementService;
import ex.aaronfae.graduation.business.trip.entity.BusinessTripDO;
import ex.aaronfae.graduation.business.trip.service.BusinessTripService;
import ex.aaronfae.graduation.jwt.SysUserService;

@Service
@Transactional
public class ReimbursementServiceImpl extends ServiceImpl<ReimbursementMapper, ReimbursementDO>
		implements ReimbursementService {

	@Resource
	private ReimbursementPictureMapper reimbursementPictureMapper;

	@Resource
	private BusinessTripService businessTripService;

	@Resource
	private SysUserService sysUserService;

	@Override
	public ReimbursementDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<ReimbursementDO> getAll(Page<ReimbursementDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveReimbursement(ReimbursementDO entity) {
		Date newDate = new Date();
		entity.setReviewed(Boolean.FALSE);
		entity.setVerified(Boolean.FALSE);
		entity.setFinished(Boolean.FALSE);
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateReimbursement(ReimbursementDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteReimbursementById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public boolean reimburseBusinessTrip(ReimbursementDO entity, MultipartFile[] files) {
		// 信息保存到t_reimbursement
		// 更新t_business_trip
		// 文件保存到t_reimbursement_picture
		entity.setEmployeeId(sysUserService.currentSysUser().getEmployeeId());
		int result = 0;
		result += saveReimbursement(entity);
		BusinessTripDO businessTrip = new BusinessTripDO();
		businessTrip.setId(entity.getBusinessTripId());
		businessTrip.setReimbursed(Boolean.TRUE);
		result += businessTripService.updateBusinessTrip(businessTrip);
		result += uploadReimbursementPicture(files, entity.getId());
		return retBool(result);
	}

	@Override
	public int applyReimbursement(ReimbursementApplyDTO entity) {
		int result = 0;
		ReimbursementDO reimbursement = new ReimbursementDO();
		BeanUtils.copyProperties(entity, reimbursement);
		reimbursement.setEmployeeId(sysUserService.currentSysUser().getEmployeeId());
		reimbursement.setReimbursements(reimbursement.getExpenses());
		BigDecimal zero = new BigDecimal("0");
		reimbursement.setLoan(zero);
		reimbursement.setDebt(zero);
		result += saveReimbursement(reimbursement);
		result += uploadReimbursementPicture(entity.getFiles(), reimbursement.getId());
		return result;
	}

	private int uploadReimbursementPicture(MultipartFile[] files, Long reimbursementId) {
		int result = 0;
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				Date nowDate = calendar.getTime();
				String filename = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5) + "-"
						+ calendar.get(11) + "-" + calendar.get(12) + "-" + calendar.get(13) + "-"
						+ file.getOriginalFilename();
				File dest = new File("D:/upload/reimburse-picture", filename);
				try {
					file.transferTo(dest);
					ReimbursementPictureDO reimbursementPicture = new ReimbursementPictureDO(filename, reimbursementId);
					reimbursementPicture.setGmtCreate(nowDate);
					reimbursementPicture.setGmtModified(nowDate);
					reimbursementPicture.setLogicDeleted(Boolean.FALSE);
					result += reimbursementPictureMapper.insert(reimbursementPicture);
				} catch (IllegalStateException e) {
					e.printStackTrace();
					throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "服务器错误，提交失败");
				} catch (IOException e) {
					e.printStackTrace();
					throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "服务器错误，提交失败");
				}
			}
		}
		return result;
	}

	@Override
	public Page<ReimbursementVO> listMyReimbursements(Page<ReimbursementVO> page) {
		Long employeeId = sysUserService.currentSysUser().getEmployeeId();
		return page.setRecords(baseMapper.listMyReimbursements(page, employeeId));
	}

}
