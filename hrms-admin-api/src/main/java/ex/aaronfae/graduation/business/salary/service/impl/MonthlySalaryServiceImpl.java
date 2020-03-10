package ex.aaronfae.graduation.business.salary.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

import ex.aaronfae.graduation.business.commute.service.CommuteExceptionService;
import ex.aaronfae.graduation.business.commute.service.CommuteService;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeIdsAndSalariesDTO;
import ex.aaronfae.graduation.business.hr.service.EmployeeService;
import ex.aaronfae.graduation.business.salary.entity.MonthlySalaryDO;
import ex.aaronfae.graduation.business.salary.mapper.MonthlySalaryMapper;
import ex.aaronfae.graduation.business.salary.model.vo.MonthlySalaryVO;
import ex.aaronfae.graduation.business.salary.service.MonthlySalaryService;
import ex.aaronfae.graduation.business.salary.service.WorkCalendarService;
import ex.aaronfae.graduation.jwt.SysUserService;

@Service
@Transactional
public class MonthlySalaryServiceImpl extends ServiceImpl<MonthlySalaryMapper, MonthlySalaryDO>
		implements MonthlySalaryService {

	@Resource
	private CommuteService commuteService;

	@Resource
	private CommuteExceptionService commuteExceptionService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private WorkCalendarService workCalendarService;

	@Resource
	private SysUserService sysUserService;

	@Override
	public MonthlySalaryDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<MonthlySalaryDO> getAll(Page<MonthlySalaryDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveMonthlySalary(MonthlySalaryDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateMonthlySalary(MonthlySalaryDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteMonthlySalaryById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public boolean saveMonthlySalarys(List<MonthlySalaryDO> monthlySalaryDOs) {
		return saveBatch(monthlySalaryDOs, monthlySalaryDOs.size());
	}

	@Override
	public boolean calculateSalaryMonthly() {
		// 薪资公式：基础薪资/月应出勤工时*员工月出勤工时

		// 从员工表中获取基础薪资/（从工作日程表中获取上月应出勤天数*8=上月应出勤工时） = 上月时薪

		// 从通勤异常记录表获取上月被扣工时+从请假表中获取上月请假工时 = 上月被扣总工时

		// 上月时薪*（上月应出勤工时-上月被扣总工时） = 上月薪资

		// 1.获取当年year，当月month，需要出勤天数daysOfWorking，算出出勤工时hoursOfWorking
		Calendar calendar = Calendar.getInstance();
		Date newDate = calendar.getTime();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		Integer daysOfWorking = workCalendarService.countWeekdays(year, month);// 应出勤天数
		BigDecimal hoursOfWorking = new BigDecimal(daysOfWorking).multiply(new BigDecimal(8)); // 应出勤工时

		// 员工ID employee_id 基础工资 salary
		List<EmployeeIdsAndSalariesDTO> allEmployeeIdsAndSalaries = employeeService.listAllEmployeeIdsAndSalaries();

		List<MonthlySalaryDO> monthlySalaryList = new ArrayList<MonthlySalaryDO>(allEmployeeIdsAndSalaries.size());// 员工薪资列表

		for (int i = 0; i < allEmployeeIdsAndSalaries.size(); i++) {
			Integer daysOfAttendance = commuteService.countDaysOfAttendance(allEmployeeIdsAndSalaries.get(i).getId(),
					year, month);// 出勤天数
			Integer dayOfAbsence = daysOfWorking - daysOfAttendance;// 缺勤天数
			Integer deductedWorkingHours = commuteExceptionService
					.countDeductedWorkingHours(allEmployeeIdsAndSalaries.get(i).getId(), year, month);// 通勤被扣工时
//			BigDecimal hoursOfWorked = hoursOfWorking.subtract(new BigDecimal(deductedWorkingHours));// 出勤工时
			BigDecimal hoursOfWorked = new BigDecimal(daysOfAttendance).multiply(new BigDecimal("8"))
					.subtract(new BigDecimal(deductedWorkingHours));// 出勤工时
			BigDecimal hourlySalary = allEmployeeIdsAndSalaries.get(i).getSalary().divide(hoursOfWorking, 2,
					RoundingMode.HALF_DOWN);// 时薪
//			BigDecimal salary = allEmployeeIdsAndSalaries.get(i).getSalary().subtract(hourlySalary.multiply(new BigDecimal(deductedWorkingHours)));
			BigDecimal salary = hoursOfWorked.multiply(hourlySalary);

			MonthlySalaryDO monthlySalary = new MonthlySalaryDO(allEmployeeIdsAndSalaries.get(i).getId(),
					String.valueOf(year), String.valueOf(month), String.valueOf(daysOfWorking),
					hoursOfWorking.intValue(), String.valueOf(daysOfAttendance), String.valueOf(dayOfAbsence),
					hoursOfWorked.intValue(), new BigDecimal("0"), salary, Boolean.FALSE);
			monthlySalary.setGmtCreate(newDate);
			monthlySalary.setGmtModified(newDate);
			monthlySalary.setLogicDeleted(Boolean.FALSE);
			monthlySalaryList.add(monthlySalary);
		}
		return saveBatch(monthlySalaryList, monthlySalaryList.size());
	}

	@Override
	public IPage<MonthlySalaryVO> listMyMonthlySalaryByPage(Page<MonthlySalaryVO> page) {
		return page.setRecords(
				baseMapper.listMyMonthlySalaryByPage(page, sysUserService.currentSysUser().getEmployeeId()));
	}

	@Override
	public IPage<MonthlySalaryVO> listNoReviewedMonthlySalaryByPage(Page<MonthlySalaryVO> page) {
		return page.setRecords(baseMapper.listNoReviewedMonthlySalaryByPage(page));
	}

	@Override
	public IPage<MonthlySalaryVO> listAllMonthlySalaryByPage(Page<MonthlySalaryVO> page) {
		return page.setRecords(baseMapper.listAllMonthlySalaryByPage(page));
	}

	@Override
	public boolean reviewMonthlySalaryByBatch(List<Long> ids) {
		Date nowDate = new Date();
		List<MonthlySalaryDO> entityList = new LinkedList<MonthlySalaryDO>();
		for (Long id : ids) {
			MonthlySalaryDO e = new MonthlySalaryDO();
			e.setId(id);
			e.setGmtModified(nowDate);
			e.setReviewed(Boolean.TRUE);
			entityList.add(e);
		}
		return updateBatchById(entityList);
	}

}
