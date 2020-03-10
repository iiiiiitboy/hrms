package ex.aaronfae.graduation.business.hr.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.hr.entity.InterviewResultDO;
import ex.aaronfae.graduation.business.hr.model.vo.InterviewResultVO;
import ex.aaronfae.graduation.business.hr.service.InterviewResultService;

@RestController
@RequestMapping("api")
public class InterviewResultRestController {

	@Resource
	private InterviewResultService interviewResultService;

	/**
	 * 根据参数 applicantId 应聘者ID 和 recruitmentId 招聘计划ID 查找匹配的“面试结果”
	 * 
	 * @param applicantId   应聘者ID
	 * @param recruitmentId 招聘计划ID
	 * @return
	 */
	@GetMapping(value = "interview-results/by")
	public ResponseVO getOneByApplicantIdAndRecruitmentId(Long applicantId, Long recruitmentId) {
		return ResponseVO.ok("请求成功",
				interviewResultService.getOneByApplicantIdAndRecruitmentId(applicantId, recruitmentId));
	}

	/**
	 * 分页查找所有“面试结果”
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping(value = "interview-results/page")
	public ResponseVO listInterviewResultsByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<InterviewResultVO> page = new Page<InterviewResultVO>(current, size);
		return ResponseVO.ok("请求成功", interviewResultService.listInterviewResultsByPage(page));
	}

	/**
	 * 保存一条“面试结果”
	 * 
	 * @param entity 面试结果
	 * @return
	 */
	@PostMapping(value = "interview-results")
	public ResponseVO saveInterviewResult(@RequestBody InterviewResultDO entity) {
		int result = interviewResultService.saveInterviewResult(entity);
		if (result > 0) {
			return ResponseVO.ok("创建成功");
		}
		return ResponseVO.error("创建失败", result);
	}

	/**
	 * 根据ID更新“面试结果”
	 * 
	 * @param entity 面试结果
	 */
	@PutMapping(value = "interview-results")
	public ResponseVO updateInterviewResult(@RequestBody InterviewResultDO entity) {
		int result = interviewResultService.updateInterviewResult(entity);
		if (result > 0) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

	/**
	 * 根据参数 id 逻辑删除“面试结果”
	 * 
	 * @param id 主键
	 * @return
	 */
	@DeleteMapping(value = "interview-results")
	public ResponseVO delInterview(Long id) {
		int result = interviewResultService.deleteInterviewById(id);
		if (result > 0) {
			return ResponseVO.ok("删除成功");
		}
		return ResponseVO.error("删除失败", result);
	}

}
