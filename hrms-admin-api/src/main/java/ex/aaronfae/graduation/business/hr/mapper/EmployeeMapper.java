package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.EmployeeDO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeIdsAndSalariesDTO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeListFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.EmployeeVO;
import ex.aaronfae.graduation.business.hr.model.vo.IdsAndNamesForQuickCommuteVO;
import ex.aaronfae.graduation.business.hr.model.vo.NamesAndIdsForQuickCommuteVO;

public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

	List<EmployeeVO> listEmployeesByPage(Page<EmployeeVO> page);

	List<Long> listOnlineEmployeeIds();

	List<NamesAndIdsForQuickCommuteVO> listEmployeeNamesAndIds();

	List<IdsAndNamesForQuickCommuteVO> listEmployeeIdsAndNames();

	List<EmployeeIdsAndSalariesDTO> listAllEmployeeIdsAndSalaries();

	List<EmployeeVO> listEmployeesByFilter(Page<EmployeeVO> page, @Param("filter") EmployeeListFilterDTO filter);

}
