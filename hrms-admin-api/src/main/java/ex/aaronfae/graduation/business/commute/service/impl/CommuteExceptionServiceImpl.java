package ex.aaronfae.graduation.business.commute.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.commute.entity.CommuteDO;
import ex.aaronfae.graduation.business.commute.entity.CommuteExceptionDO;
import ex.aaronfae.graduation.business.commute.entity.SysCommuteDO;
import ex.aaronfae.graduation.business.commute.mapper.CommuteExceptionMapper;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteExceptionVO;
import ex.aaronfae.graduation.business.commute.service.CommuteExceptionService;
import ex.aaronfae.graduation.business.commute.service.CommuteService;
import ex.aaronfae.graduation.business.commute.service.SysCommuteService;

@Service
@Transactional
public class CommuteExceptionServiceImpl extends ServiceImpl<CommuteExceptionMapper, CommuteExceptionDO>
		implements CommuteExceptionService {

	@Resource
	private CommuteService commuteService;

	@Resource
	private SysCommuteService sysCommuteService;

	@Override
	public CommuteExceptionDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<CommuteExceptionDO> getAll(Page<CommuteExceptionDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveCommuteException(CommuteExceptionDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateCommuteException(CommuteExceptionDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteCommuteExceptionById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public List<CommuteExceptionVO> listCommuteExceptionsForEmployee(Long employeeId) {
		return baseMapper.listCommuteExceptionsForEmployee(employeeId);
	}

	@Override
	public int quickCommute(CommuteExceptionVO entity) {
		SysCommuteDO enabledRecord = sysCommuteService.getEnabledRecord();

		CommuteDO commute = new CommuteDO();
		commute.setId(entity.getCommuteId());
		switch (entity.getCommuteExceptionType()) {
		case BE_LATE:
			// 将原通勤记录修改为正常上班
			commute.setToWork(Boolean.TRUE);
			commute.setToWorkTime(enabledRecord.getStartTime());
			break;
		case LEAVE_EARLY:
			// 将原通勤记录修改为正常下班
			commute.setOffWork(Boolean.TRUE);
			commute.setOffWorkTime(enabledRecord.getEndTime());
			break;
		case NO_TO_WORK:
			// 将原通勤记录修改为正常上班
			commute.setToWork(Boolean.TRUE);
			commute.setToWorkTime(enabledRecord.getStartTime());
			break;
		case NO_TO_LEAVE:
			// 将原通勤记录修改为正常下班
			commute.setOffWork(Boolean.TRUE);
			commute.setOffWorkTime(enabledRecord.getEndTime());
			break;
		default:
			break;
		}
		commuteService.updateCommute(commute);

		CommuteExceptionDO commuteException = new CommuteExceptionDO();
		commuteException.setId(entity.getId());
		commuteException.setWitness(entity.getWitnessId());
		commuteException.setDone(Boolean.TRUE);
		updateCommuteException(commuteException);

		return updateCommuteException(commuteException);
	}

	@Override
	public Integer countDeductedWorkingHours(Long employeeId, int year, int month) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, 1);
		Date firstDay = calendar.getTime();

		calendar.set(5, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDay = calendar.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Integer result = baseMapper.countDeductedWorkingHours(employeeId, simpleDateFormat.format(firstDay),
				simpleDateFormat.format(lastDay));

		return result != null ? result : 0;
	}

}
