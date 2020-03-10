package ex.aaronfae.graduation.business.organization.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.service.JobService;
import ex.aaronfae.graduation.business.organization.model.vo.JobVO;

@RestController
@RequestMapping("api")
public class OrgJobController {

	@Resource
	private JobService jobService;

	@GetMapping("jobs/page")
	public ResponseVO listDeptByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<JobVO> page = new Page<JobVO>(current, size);
		return ResponseVO.ok("请求成功", jobService.listJobByPage(page));
	}

}
