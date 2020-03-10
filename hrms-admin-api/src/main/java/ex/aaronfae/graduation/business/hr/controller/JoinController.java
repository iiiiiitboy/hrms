package ex.aaronfae.graduation.business.hr.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentForApplicantVO;
import ex.aaronfae.graduation.business.hr.service.RecruitmentService;

@RestController
public class JoinController {

	@Resource
	private RecruitmentService recruitmentService;

	@GetMapping(value = "recruitments/join")
	public ResponseVO listRecruitmentsForApplicant(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<RecruitmentForApplicantVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", recruitmentService.listRecruitmentForApplicant(page));
	}

}
