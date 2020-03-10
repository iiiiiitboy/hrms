package ex.aaronfae.graduation.business.salary.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.salary.entity.MonthlySalaryDO;
import ex.aaronfae.graduation.business.salary.model.vo.MonthlySalaryVO;

public interface MonthlySalaryService {

	MonthlySalaryDO getOneById(Long id);

	IPage<MonthlySalaryDO> getAll(Page<MonthlySalaryDO> page);

	int saveMonthlySalary(MonthlySalaryDO entity);

	int updateMonthlySalary(MonthlySalaryDO entity);

	int deleteMonthlySalaryById(Long id);

	boolean saveMonthlySalarys(List<MonthlySalaryDO> workCalendarDOs);

	/**
	 * 每月14日凌晨两点计算所以员工的月薪
	 */
	boolean calculateSalaryMonthly();

	IPage<MonthlySalaryVO> listMyMonthlySalaryByPage(Page<MonthlySalaryVO> page);

	IPage<MonthlySalaryVO> listNoReviewedMonthlySalaryByPage(Page<MonthlySalaryVO> page);

	IPage<MonthlySalaryVO> listAllMonthlySalaryByPage(Page<MonthlySalaryVO> page);

	boolean reviewMonthlySalaryByBatch(List<Long> ids);

}
