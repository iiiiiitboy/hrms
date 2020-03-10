package ex.aaronfae.graduation.business.commute.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.base.enumerate.CommuteExceptionTypeEnum;
import ex.aaronfae.graduation.base.util.DateUtil;
import ex.aaronfae.graduation.business.commute.entity.CommuteDO;
import ex.aaronfae.graduation.business.commute.entity.CommuteExceptionDO;
import ex.aaronfae.graduation.business.commute.entity.SysCommuteDO;
import ex.aaronfae.graduation.business.commute.mapper.CommuteMapper;
import ex.aaronfae.graduation.business.commute.model.dto.CommuteListFilterDTO;
import ex.aaronfae.graduation.business.commute.model.vo.ClockVO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteVO;
import ex.aaronfae.graduation.business.commute.service.CommuteExceptionService;
import ex.aaronfae.graduation.business.commute.service.CommuteService;
import ex.aaronfae.graduation.business.commute.service.SysCommuteService;
import ex.aaronfae.graduation.business.hr.service.EmployeeService;
import ex.aaronfae.graduation.jwt.SysUserService;

@Service
@Transactional
public class CommuteServiceImpl extends ServiceImpl<CommuteMapper, CommuteDO> implements CommuteService {

	@Resource
	private SysCommuteService sysCommuteService;

	@Resource
	private CommuteExceptionService commuteExceptionService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private SysUserService sysUserService;

	@Override
	public int saveCommute(CommuteDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateCommute(CommuteDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteCommuteById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public Page<CommuteVO> listCommuteExceptionsByPage(Page<CommuteVO> page) {
		return page.setRecords(baseMapper.listCommuteExceptionsByPage(page));
	}

	@Override
	public IPage<CommuteVO> listTodyCommutesByPage(Page<CommuteVO> page) {
		return page.setRecords(baseMapper.listTodyCommutesByPage(page));
	}

	@Override
	public IPage<CommuteVO> listHistoryCommutesByPage(Page<CommuteVO> page) {
		return page.setRecords(baseMapper.listHistoryCommutesByPage(page));
	}

	@Override
	public IPage<CommuteVO> listHistoryCommutesByFilter(Page<CommuteVO> page, CommuteListFilterDTO filter) {
		return page.setRecords(baseMapper.listHistoryCommutesByFilter(page, filter));
	}

	@Override
	public int callMorningAttentionToEmployee(CommuteVO entity) {
		Date newDate = new Date();

		// 确认被通知
		CommuteDO commute = new CommuteDO();
		commute.setId(entity.getId());
		commute.setGmtModified(newDate);
		commute.setMorningCalled(Boolean.TRUE);
		baseMapper.updateById(commute);

		SysCommuteDO enabledSysCommute = sysCommuteService.getEnabledRecord();
		String remark = "";
		Integer deductedWorkingHours = 0;
		CommuteExceptionTypeEnum commuteExceptionTypeEnum = null;
		// 打了上班卡
		if (entity.getToWork()) {
			// 迟到
			if (entity.getToWorkTime().after(enabledSysCommute.getStartTime())) {
				deductedWorkingHours = DateUtil.deductWorkingHours(entity.getToWorkTime(),
						enabledSysCommute.getStartTime());
				remark = "迟到：" + DateUtil.diffTime(entity.getToWorkTime(), enabledSysCommute.getStartTime()) + "，扣"
						+ deductedWorkingHours + "个工时";
				commuteExceptionTypeEnum = CommuteExceptionTypeEnum.BE_LATE;
			}
		} else {
			// 没打卡
			deductedWorkingHours = 4;
			remark = "没打上班卡，扣4个工时";
			commuteExceptionTypeEnum = CommuteExceptionTypeEnum.NO_TO_WORK;
		}
		CommuteExceptionDO commuteException = new CommuteExceptionDO(entity.getEmployeeId(), entity.getId(), null,
				entity.getToday(), entity.getToWorkTime(), entity.getToWork(), entity.getOffWorkTime(),
				entity.getOffWork(), deductedWorkingHours, commuteExceptionTypeEnum, remark, Boolean.FALSE);
		commuteException.setGmtCreate(newDate);
		commuteException.setGmtModified(newDate);
		commuteException.setLogicDeleted(Boolean.FALSE);
		return commuteExceptionService.saveCommuteException(commuteException);
	}

	@Override
	public int callAfternoonAttentionToEmployee(CommuteVO entity) {
		Date newDate = new Date();

		CommuteDO commute = new CommuteDO();
		commute.setId(entity.getId());
		commute.setGmtModified(newDate);
		commute.setAfternoonCalled(Boolean.TRUE);
		baseMapper.updateById(commute);

		SysCommuteDO enabledSysCommute = sysCommuteService.getEnabledRecord();
		String remark = "";
		Integer deductedWorkingHours = 0;
		CommuteExceptionTypeEnum commuteExceptionTypeEnum = null;
		// 打了下班卡
		if (entity.getOffWork()) {
			// 早退
			if (entity.getOffWorkTime().before(enabledSysCommute.getEndTime())) {
				deductedWorkingHours = DateUtil.deductWorkingHours(enabledSysCommute.getEndTime(),
						entity.getOffWorkTime());
				remark = "早退：" + DateUtil.diffTime(enabledSysCommute.getEndTime(), entity.getOffWorkTime()) + "，扣"
						+ deductedWorkingHours + "个工时";
				commuteExceptionTypeEnum = CommuteExceptionTypeEnum.LEAVE_EARLY;
			}
		} else {
			// 没打卡
			deductedWorkingHours = 4;
			remark = "没打下班卡，扣4个工时";
			commuteExceptionTypeEnum = CommuteExceptionTypeEnum.NO_TO_LEAVE;
		}
		CommuteExceptionDO commuteException = new CommuteExceptionDO(entity.getEmployeeId(), entity.getId(), null,
				entity.getToday(), entity.getToWorkTime(), entity.getToWork(), entity.getOffWorkTime(),
				entity.getOffWork(), deductedWorkingHours, commuteExceptionTypeEnum, remark, Boolean.FALSE);
		commuteException.setGmtCreate(newDate);
		commuteException.setGmtModified(newDate);
		commuteException.setLogicDeleted(Boolean.FALSE);
		return commuteExceptionService.saveCommuteException(commuteException);
	}

	@Override
	public ClockVO isClock() {
		ClockVO clockVO = baseMapper.isClock(sysUserService.currentSysUser().getEmployeeId());
		clockVO.setRecord(baseMapper.getRecentCommuteRecord(sysUserService.currentSysUser().getEmployeeId()));
		return clockVO;
	}

	@Override
	public int toWork(Date date) {
		return baseMapper.toWork(sysUserService.currentSysUser().getEmployeeId(), date);
	}

	@Override
	public int offWork(Date date) {
		return baseMapper.offWork(sysUserService.currentSysUser().getEmployeeId(), date);
	}

	@Override
	public boolean insertCommuteDaily() {
		// 1.请求员工服务，拿到所有在职员工ID列表
		List<Long> onlineEmployeeIds = employeeService.listOnlineEmployeeIds();
		// 2.获取今日时间，遍历员工，为该员工生成预通勤记录
		// 预通勤记录要求：通勤时间、退勤时间为空，是否通勤、是否退勤为0（假）
		Date todayDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = simpleDateFormat.format(todayDate);
		List<CommuteDO> commutes = new LinkedList<CommuteDO>();
		for (Long id : onlineEmployeeIds) {
			CommuteDO entity = new CommuteDO(id, today, null, Boolean.FALSE, null, Boolean.FALSE, Boolean.FALSE,
					Boolean.FALSE);
			entity.setGmtCreate(todayDate);
			entity.setGmtModified(todayDate);
			entity.setLogicDeleted(Boolean.FALSE);
			commutes.add(entity);
		}
		return saveBatch(commutes);
	}

	@Override
	public Integer countDaysOfAttendance(Long employeeId, int year, int month) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, 1);
		Date firstDay = calendar.getTime();

		calendar.set(5, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDay = calendar.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Integer result = baseMapper.countDaysOfAttendance(employeeId, simpleDateFormat.format(firstDay),
				simpleDateFormat.format(lastDay));
		return result != null ? result : 0;
	}

}
