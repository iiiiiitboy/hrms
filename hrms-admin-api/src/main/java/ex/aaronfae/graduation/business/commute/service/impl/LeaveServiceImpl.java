package ex.aaronfae.graduation.business.commute.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.commute.entity.LeaveDO;
import ex.aaronfae.graduation.business.commute.mapper.LeaveMapper;
import ex.aaronfae.graduation.business.commute.model.vo.LeaveVO;
import ex.aaronfae.graduation.business.commute.service.LeaveService;

@Service
@Transactional
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, LeaveDO> implements LeaveService {

	@Override
	public int saveLeave(LeaveDO entity) {
		entity.setReviewed(Boolean.FALSE);
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateLeave(LeaveDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteLeaveById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public List<LeaveVO> listMyLeaves(Long employeeId) {
		return baseMapper.listMyLeaves(employeeId);
	}

	@Override
	public Page<LeaveVO> listLeaves(Page<LeaveVO> page) {
		return page.setRecords(baseMapper.listLeaves(page));
	}

}
