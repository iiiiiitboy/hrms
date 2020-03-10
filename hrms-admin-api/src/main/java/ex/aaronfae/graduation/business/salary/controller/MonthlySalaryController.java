package ex.aaronfae.graduation.business.salary.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.salary.entity.MonthlySalaryDO;
import ex.aaronfae.graduation.business.salary.model.vo.MonthlySalaryVO;
import ex.aaronfae.graduation.business.salary.service.MonthlySalaryService;

@RestController
@RequestMapping(value = "api")
public class MonthlySalaryController {

	@Resource
	private MonthlySalaryService monthlySalaryService;

	@GetMapping("monthly-salary/my")
	public ResponseVO listMyMonthlySalaryByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<MonthlySalaryVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", monthlySalaryService.listMyMonthlySalaryByPage(page));
	}

	@GetMapping("monthly-salary/no-reviewed")
	public ResponseVO listNoReviewedMonthlySalaryByPage(
			@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<MonthlySalaryVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", monthlySalaryService.listNoReviewedMonthlySalaryByPage(page));
	}

	@GetMapping("monthly-salary/all")
	public ResponseVO listAllMonthlySalaryByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<MonthlySalaryVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", monthlySalaryService.listAllMonthlySalaryByPage(page));
	}

	@PutMapping("monthly-salary")
	public ResponseVO updateMonthlySalary(@RequestBody MonthlySalaryDO entity) {
		int result = monthlySalaryService.updateMonthlySalary(entity);
		if (result > 0) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

	@PutMapping("monthly-salary/review")
	public ResponseVO reviewMonthlySalaryByBatch(@RequestBody List<Long> ids) {
		boolean result = monthlySalaryService.reviewMonthlySalaryByBatch(ids);
		if (result) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

}
