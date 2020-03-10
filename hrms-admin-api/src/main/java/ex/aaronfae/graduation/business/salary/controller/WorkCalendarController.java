package ex.aaronfae.graduation.business.salary.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.enumerate.CalendarTypeEnum;
import ex.aaronfae.graduation.base.util.DateUtil;
import ex.aaronfae.graduation.business.salary.entity.WorkCalendarDO;
import ex.aaronfae.graduation.business.salary.service.WorkCalendarService;

@RestController
public class WorkCalendarController {

	@Resource
	private WorkCalendarService workCalendarService;

	@GetMapping("workCalendars/{id}")
	public WorkCalendarDO getOneById(@PathVariable("id") Long id) {
		return workCalendarService.getOneById(id);
	}

	@GetMapping("workCalendars")
	public IPage<WorkCalendarDO> getAll(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<WorkCalendarDO> page = new Page<>(current, size);
		return workCalendarService.getAll(page);
	}

	@PostMapping("workCalendars")
	public int saveWorkCalendar(@RequestBody WorkCalendarDO entity) {
		return workCalendarService.saveWorkCalendar(entity);
	}

	@PutMapping("workCalendars")
	public int updateWorkCalendar(@RequestBody WorkCalendarDO entity) {
		return workCalendarService.updateWorkCalendar(entity);
	}

	@DeleteMapping("workCalendars/{id}")
	public int deleteWorkCalendarById(@PathVariable("id") Long id) {
		return workCalendarService.deleteWorkCalendarById(id);
	}

	@GetMapping("workCalendars/test")
	public int test(Integer year) {
		Calendar calendar = Calendar.getInstance();
		Date newDate = calendar.getTime();

		calendar.set(year, 0, 1);

		int maxDaysOfYear = DateUtil.getMaxDaysOfYear(calendar);

		List<WorkCalendarDO> workCalendarDOs = new ArrayList<WorkCalendarDO>(maxDaysOfYear);

		for (int i = 1; i <= maxDaysOfYear; i++) {
			WorkCalendarDO workCalendarDO = new WorkCalendarDO();
			if (DateUtil.isWeekend(calendar)) {
				workCalendarDO.setCalendarType(CalendarTypeEnum.ORDINARY_HOLIDAYS);
			} else {
				workCalendarDO.setCalendarType(CalendarTypeEnum.WORKING_DAY);
			}
			workCalendarDO.setDays(calendar.getTime());
			workCalendarDO.setGmtCreate(newDate);
			workCalendarDO.setGmtModified(newDate);
			workCalendarDO.setLogicDeleted(Boolean.FALSE);
			workCalendarDOs.add(workCalendarDO);
			calendar.add(5, 1);
		}

		if (workCalendarService.saveWorkCalendars(workCalendarDOs)) {
			return 1;
		}
		return 0;
	}

}
