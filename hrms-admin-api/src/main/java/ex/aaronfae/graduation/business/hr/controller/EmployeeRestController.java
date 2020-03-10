package ex.aaronfae.graduation.business.hr.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.enumerate.WorkingStatusEnum;
import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.entity.EmployeeDO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeListFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.EmployeeVO;
import ex.aaronfae.graduation.business.hr.service.EmployeeService;

@RestController
@RequestMapping("api")
public class EmployeeRestController {

	@Resource
	private EmployeeService employeeService;

	/**
	 * 办理入职，保存员工信息，同时为员工注册账号，并赋予员工角色
	 * 
	 * @param entity 员工信息
	 * @return
	 */
	@PostMapping("employees")
	public ResponseVO register(@RequestBody EmployeeDO entity) {
		int result = employeeService.saveEmployee(entity);
		if (result > 0) {
			return ResponseVO.ok("办理入职成功");
		}
		return ResponseVO.error("办理入职失败", result);
	}

	/**
	 * 查看员工列表
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping("employees/page")
	public ResponseVO listEmployeesByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<EmployeeVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", employeeService.listEmployeesByPage(page));
	}

	@GetMapping("employees/filter")
	public ResponseVO listEmployeesByFilter(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size, EmployeeListFilterDTO filter,
			String workingStatusId) {
		Page<EmployeeVO> page = new Page<>(current, size);
		if (!StringUtils.isEmpty(workingStatusId)) {
			filter.setWorkingStatus(WorkingStatusEnum.valueOf(workingStatusId));
		}
		return ResponseVO.ok("请求成功", employeeService.listEmployeesByFilter(page, filter));
	}

	/**
	 * 根据ID更新“员工”
	 * 
	 * @param entity 员工信息
	 * @return
	 */
	@PutMapping("employees")
	public ResponseVO updateEmployee(@RequestBody EmployeeDO entity) {
		int result = employeeService.updateEmployee(entity);
		if (result > 0) {
			return ResponseVO.ok("修改成功");
		}
		return ResponseVO.error("修改失败", result);
	}

	/**
	 * 根据参数 id 逻辑删除“员工”
	 * 
	 * @param id 主键
	 * @return
	 */
	@DeleteMapping("employees/{id}")
	public ResponseVO deleteEmployeeById(@PathVariable("id") Long id) {
		int result = employeeService.deleteEmployeeById(id);
		if (result > 0) {
			return ResponseVO.ok("删除成功");
		}
		return ResponseVO.error("删除失败", result);
	}

	@GetMapping("employees/ids/online")
	public List<Long> listOnlineEmployeeIds() {
		return employeeService.listOnlineEmployeeIds();
	}

	@GetMapping("employees/namesAndIds")
	public ResponseVO listEmployeeNamesAndIdsForQuickCommute() {
		return ResponseVO.ok("请求成功", employeeService.listEmployeeNamesAndIds());
	}

	@GetMapping("employees/idsAndNames")
	public ResponseVO listEmployeeIdsAndNamesForQuickCommute() {
		return ResponseVO.ok("请求成功", employeeService.listEmployeeIdsAndNames());
	}

}
