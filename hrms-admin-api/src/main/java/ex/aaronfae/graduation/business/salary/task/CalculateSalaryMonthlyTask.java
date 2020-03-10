package ex.aaronfae.graduation.business.salary.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ex.aaronfae.graduation.business.salary.service.MonthlySalaryService;

@Component
public class CalculateSalaryMonthlyTask {

	@Resource
	private MonthlySalaryService monthlySalaryService;

	/**
	 * 每月14日凌晨两点计算所以员工的月薪
	 */
	@Scheduled(cron = "0 0 2 14 * ?")
	public void calculateSalaryMonthly() {
		monthlySalaryService.calculateSalaryMonthly();
	}
}
