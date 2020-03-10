package ex.aaronfae.graduation.business.hr.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.EmployeeDO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeIdsAndSalariesDTO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeListFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.EmployeeVO;
import ex.aaronfae.graduation.business.hr.model.vo.IdsAndNamesForQuickCommuteVO;
import ex.aaronfae.graduation.business.hr.model.vo.NamesAndIdsForQuickCommuteVO;

public interface EmployeeService {

	EmployeeDO getOneById(Long id);

	IPage<EmployeeVO> listEmployeesByPage(Page<EmployeeVO> page);

	int saveEmployee(EmployeeDO entity);

	int updateEmployee(EmployeeDO entity);

	int deleteEmployeeById(Long id);

	/**
	 * @return 所有在职员工ID列表
	 */
	List<Long> listOnlineEmployeeIds();

	/**
	 * @return 所有员工ID、薪资列表
	 */
	List<EmployeeIdsAndSalariesDTO> listAllEmployeeIdsAndSalaries();

	/**
	 * @return 所有员工姓名、ID列表
	 */
	List<NamesAndIdsForQuickCommuteVO> listEmployeeNamesAndIds();

	/**
	 * @return 所有员工ID、姓名列表
	 */
	List<IdsAndNamesForQuickCommuteVO> listEmployeeIdsAndNames();

	IPage<EmployeeVO> listEmployeesByFilter(Page<EmployeeVO> page, EmployeeListFilterDTO filter);

}
