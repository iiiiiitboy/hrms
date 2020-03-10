package ex.aaronfae.graduation.business.commute.controller;

import javax.annotation.Resource;

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

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.commute.entity.LeaveDO;
import ex.aaronfae.graduation.business.commute.model.vo.LeaveVO;
import ex.aaronfae.graduation.business.commute.service.LeaveService;
import ex.aaronfae.graduation.jwt.SysUserService;

@RestController
@RequestMapping(value = "api")
public class LeaveRestController {

	@Resource
	private LeaveService leaveService;

	@Resource
	private SysUserService sysUserService;

	@PostMapping(value = "leaves")
	public ResponseVO saveLeave(@RequestBody LeaveDO entity) {
		entity.setEmployeeId(sysUserService.currentSysUser().getEmployeeId());
		int result = leaveService.saveLeave(entity);
		if (result > 0) {
			return ResponseVO.ok("提交成功", Boolean.TRUE);
		}
		return ResponseVO.ok("提交失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@GetMapping(value = "leaves/my")
	public ResponseVO myLeave() {
		return ResponseVO.ok("请求成功", leaveService.listMyLeaves(sysUserService.currentSysUser().getEmployeeId()));
	}

	@GetMapping(value = "leaves/page")
	public ResponseVO listLeaves(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<LeaveVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", leaveService.listLeaves(page));
	}

	@PutMapping(value = "leaves")
	public ResponseVO updateLeave(@RequestBody LeaveDO entity) {
		int result = leaveService.updateLeave(entity);
		if (result > 0) {
			return ResponseVO.ok("修改成功", Boolean.TRUE);
		}
		return ResponseVO.ok("修改失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@DeleteMapping("leaves/{id}")
	public int deleteLeaveById(@PathVariable("id") Long id) {
		return leaveService.deleteLeaveById(id);
	}

}
