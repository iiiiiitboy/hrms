package ex.aaronfae.graduation.business.hr.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.service.DeptService;

@RestController
@RequestMapping("api")
public class DeptRestController {

	@Resource
	private DeptService deptService;

	/**
	 * 为创建招聘计划页面提供Options
	 * 
	 * @return 部门的ID，名称列表
	 */
	@GetMapping(value = "depts/options")
	public ResponseVO listDeptOptions() {
		return ResponseVO.ok("请求成功", deptService.listDeptsIdAndName());
	}

}
