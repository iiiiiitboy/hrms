package ex.aaronfae.graduation.business.hr.controller;

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
import ex.aaronfae.graduation.business.hr.entity.RecruitmentDO;
import ex.aaronfae.graduation.business.hr.model.dto.RecruitmentFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentVO;
import ex.aaronfae.graduation.business.hr.service.RecruitmentService;

@RestController
@RequestMapping("api")
public class RecruitmentRestController {

	@Resource
	private RecruitmentService recruitmentService;

	/**
	 * 保存一条“招聘计划”记录
	 * 
	 * @param entity 招聘计划
	 * @return
	 */
	@PostMapping(value = "recruitments")
	public ResponseVO saveRecruitment(@RequestBody RecruitmentDO entity) {
		int result = recruitmentService.saveRecruitment(entity);
		if (result > 0) {
			return ResponseVO.ok("创建成功");
		}
		return ResponseVO.error("创建失败", result);
	}

	/**
	 * 分页查找所有“招聘计划”
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping(value = "recruitments/page")
	public ResponseVO listRecruitmentsByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<RecruitmentVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", recruitmentService.listRecruitmentsByPage(page));
	}

	@GetMapping(value = "recruitments/filter")
	public ResponseVO listRecruitmentsByFilter(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size, RecruitmentFilterDTO filter) {
		Page<RecruitmentVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", recruitmentService.listRecruitmentsByFilter(page, filter));
	}

	/**
	 * 根据ID判断“招聘计划”是否存在应聘者
	 * 
	 * @param id 招聘计划ID
	 * @return
	 */
	@GetMapping(value = "recruitments/{id}/hadApplicant")
	public ResponseVO hadApplicant(@PathVariable("id") Long id) {
		int result = recruitmentService.countApplicant(id);
		if (result > 0) {
			return ResponseVO.ok("招聘计划已存在应聘者", Boolean.TRUE);
		}
		return ResponseVO.ok("招聘计划尚未有应聘者", Boolean.FALSE);
	}

	/**
	 * 根据ID更新“招聘计划”
	 * 
	 * @param entity 招聘计划
	 * @return
	 */
	@PutMapping(value = "recruitments")
	public ResponseVO updateRecruitment(@RequestBody RecruitmentDO entity) {
		int result = recruitmentService.updateRecruitment(entity);
		if (result > 0) {
			return ResponseVO.ok("更新成功");
		}
		return ResponseVO.error("更新失败", result);
	}

	/**
	 * 根据参数 id 逻辑删除“招聘计划”
	 * 
	 * @param id 主键
	 * @return
	 */
	@DeleteMapping(value = "recruitments")
	public ResponseVO deleteRecruitmentById(@RequestParam Long id) {
		int result = recruitmentService.deleteRecruitmentById(id);
		if (result > 0) {
			return ResponseVO.ok("删除成功");
		}
		return ResponseVO.error("删除失败", result);
	}

}
