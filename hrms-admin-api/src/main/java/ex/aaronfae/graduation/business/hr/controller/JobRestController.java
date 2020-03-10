package ex.aaronfae.graduation.business.hr.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.service.JobService;

@RestController
@RequestMapping(value = "api")
public class JobRestController {

	@Resource
	private JobService jobService;

	/**
	 * 为创建招聘计划页面提供Options
	 * 
	 * @param deptId 部门编号
	 * 
	 * @return 岗位的ID，名称列表
	 */
	@GetMapping(value = "jobs/options")
	public ResponseVO listJobOptions(@RequestParam("deptId") Long deptId) {
		return ResponseVO.ok("岗位ID，岗位名称列表", jobService.listJobsIdAndNameByDeptId(deptId));
	}

}
