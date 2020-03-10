package ex.aaronfae.graduation.business.commute.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ex.aaronfae.graduation.business.commute.service.CommuteService;

@Component
public class InsertCommuteDailyTask {

	@Resource
	private CommuteService commuteService;

	/**
	 * 周一到周五每日凌晨两点插入所有在职员工的预通勤记录
	 */
	@Scheduled(cron = "0 0 2 * * MON-FRI")
	public void insertCommute() {
		commuteService.insertCommuteDaily();
	}

}
