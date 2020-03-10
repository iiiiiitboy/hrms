package ex.aaronfae.graduation.business.salary.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.salary.entity.WorkCalendarDO;

public interface WorkCalendarService {

	WorkCalendarDO getOneById(Long id);

	IPage<WorkCalendarDO> getAll(Page<WorkCalendarDO> page);

	int saveWorkCalendar(WorkCalendarDO entity);

	int updateWorkCalendar(WorkCalendarDO entity);

	int deleteWorkCalendarById(Long id);

	boolean saveWorkCalendars(List<WorkCalendarDO> workCalendarDOs);

	Integer countWeekdays(int year, int month);

}
