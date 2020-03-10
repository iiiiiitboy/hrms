package ex.aaronfae.graduation.business.commute.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.commute.entity.CommuteExceptionDO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteExceptionVO;
import ex.aaronfae.graduation.business.commute.service.CommuteExceptionService;
import ex.aaronfae.graduation.jwt.SysUserService;

@RestController
@RequestMapping(value = "api")
public class CommuteExceptionRestController {

	@Resource
	private CommuteExceptionService CommuteExceptionService;

	@Resource
	private SysUserService sysUserService;

	@GetMapping("commuteExceptions/{id}")
	public CommuteExceptionDO getOneById(@PathVariable("id") Long id) {
		return CommuteExceptionService.getOneById(id);
	}

	@GetMapping("commuteExceptions")
	public IPage<CommuteExceptionDO> getAll(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<CommuteExceptionDO> page = new Page<>(current, size);
		return CommuteExceptionService.getAll(page);
	}

	// @PostMapping("commuteExceptions")
	public int saveCommuteException(@RequestBody CommuteExceptionDO entity) {
		return CommuteExceptionService.saveCommuteException(entity);
	}

	@PutMapping("commuteExceptions")
	public ResponseVO updateCommuteException(@RequestBody CommuteExceptionDO entity) {
		int result = CommuteExceptionService.updateCommuteException(entity);
		if (result > 0) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

	// @DeleteMapping("commuteExceptions/{id}")
	public int deleteCommuteExceptionById(@PathVariable("id") Long id) {
		return CommuteExceptionService.deleteCommuteExceptionById(id);
	}

	/**
	 * 为员工列出异常通勤信息
	 * 
	 * @return
	 */
	@GetMapping("commuteExceptions/employees")
	public ResponseVO listCommuteExceptionsForEmployee() {
		Long employeeId = sysUserService.currentSysUser().getEmployeeId();
		return ResponseVO.ok("请求成功", CommuteExceptionService.listCommuteExceptionsForEmployee(employeeId));
	}

	/**
	 * 员工快速签卡
	 * 
	 * @param entity
	 * @return
	 */
	@PutMapping("commuteExceptions/quickCommute")
	public ResponseVO quickCommute(@RequestBody CommuteExceptionVO entity) {
		int result = CommuteExceptionService.quickCommute(entity);
		if (result > 0) {
			return ResponseVO.ok("签卡成功");
		}
		return ResponseVO.error("签卡失败，请重试或联系系统管理员", result);
	}

}
