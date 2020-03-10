package ex.aaronfae.graduation.business.salary.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.salary.entity.WorkCalendarDO;
import ex.aaronfae.graduation.business.salary.mapper.WorkCalendarMapper;
import ex.aaronfae.graduation.business.salary.service.WorkCalendarService;

@Service
@Transactional
public class WorkCalendarServiceImpl extends ServiceImpl<WorkCalendarMapper, WorkCalendarDO>
		implements WorkCalendarService {

	@Override
	public WorkCalendarDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<WorkCalendarDO> getAll(Page<WorkCalendarDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveWorkCalendar(WorkCalendarDO entity) {
		Date newDate = new Date();
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateWorkCalendar(WorkCalendarDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteWorkCalendarById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public boolean saveWorkCalendars(List<WorkCalendarDO> workCalendarDOs) {
		return saveBatch(workCalendarDOs, workCalendarDOs.size());
	}

	@Override
	public Integer countWeekdays(int year, int month) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, 1);
		Date firstDay = calendar.getTime();

		calendar.set(5, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDay = calendar.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Integer result = baseMapper.countWeekdays(simpleDateFormat.format(firstDay), simpleDateFormat.format(lastDay));

		return result != null ? result : 0;
	}

}
