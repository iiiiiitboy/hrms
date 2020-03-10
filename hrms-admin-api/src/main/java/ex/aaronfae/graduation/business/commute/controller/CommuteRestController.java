package ex.aaronfae.graduation.business.commute.controller;

import java.util.Date;

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
import ex.aaronfae.graduation.business.commute.entity.CommuteDO;
import ex.aaronfae.graduation.business.commute.model.dto.CommuteListFilterDTO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteVO;
import ex.aaronfae.graduation.business.commute.service.CommuteService;

@RestController
@RequestMapping(value = "api")
public class CommuteRestController {

	@Resource
	private CommuteService commuteService;

	@PostMapping("commutes")
	public int saveCommute(@RequestBody CommuteDO entity) {
		return commuteService.saveCommute(entity);
	}

	@PutMapping("commutes")
	public int updateCommute(@RequestBody CommuteDO entity) {
		return commuteService.updateCommute(entity);
	}

	@DeleteMapping("commutes")
	public int deleteCommuteById(Long id) {
		return commuteService.deleteCommuteById(id);
	}

	/**
	 * 分页查找所有“通勤异常列表”
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping("commutes/exception")
	public ResponseVO listCommuteExceptionsByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<CommuteVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", commuteService.listCommuteExceptionsByPage(page));
	}

	/**
	 * 分页查找所有“今日通勤列表”
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping("commutes/today")
	public ResponseVO listTodayCommutesByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<CommuteVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", commuteService.listTodyCommutesByPage(page));
	}

	/**
	 * 分页查找所有“历史通勤列表”
	 * 
	 * @param current 当前页码
	 * @param size    每页记录数
	 * @return
	 */
	@GetMapping("commutes/history")
	public ResponseVO listHistoryCommutesByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<CommuteVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", commuteService.listHistoryCommutesByPage(page));
	}

	@GetMapping("commutes/history/filter")
	public ResponseVO listHistoryCommutesByFilter(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size, CommuteListFilterDTO filter) {
		Page<CommuteVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", commuteService.listHistoryCommutesByFilter(page, filter));
	}

	/**
	 * 提醒员工早上通勤异常
	 * 
	 * @param entity 通勤信息
	 * @return
	 */
	@PostMapping("commutes/morningCall")
	public ResponseVO callMorningAttentionToEmployee(@RequestBody CommuteVO entity) {
		int result = commuteService.callMorningAttentionToEmployee(entity);
		if (result > 0) {
			return ResponseVO.ok("提醒成功", Boolean.TRUE);
		}
		return ResponseVO.ok("提醒失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	/**
	 * 提醒员工下午通勤异常
	 * 
	 * @param entity 通勤信息
	 * @return
	 */
	@PostMapping("commutes/afternoonCall")
	public ResponseVO callAfternoonAttentionToEmployee(@RequestBody CommuteVO entity) {
		int result = commuteService.callAfternoonAttentionToEmployee(entity);
		if (result > 0) {
			return ResponseVO.ok("提醒成功", Boolean.TRUE);
		}
		return ResponseVO.ok("提醒失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	/**
	 * 为打卡页面提供数据
	 * 
	 * @return
	 */
	@GetMapping("commutes/clock")
	public ResponseVO isClock() {
		return ResponseVO.ok("请求成功", commuteService.isClock());
	}

	@PostMapping("commutes/to-work")
	public ResponseVO toWork() {
		Date date = new Date();
		int result = commuteService.toWork(date);
		if (result > 0) {
			return ResponseVO.ok("打卡成功", Boolean.TRUE);
		}
		return ResponseVO.ok("打卡失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@PostMapping("commutes/off-work")
	public ResponseVO offWork() {
		Date date = new Date();
		int result = commuteService.offWork(date);
		if (result > 0) {
			return ResponseVO.ok("打卡成功", Boolean.TRUE);
		}
		return ResponseVO.ok("打卡失败，请重试或联系系统管理员", Boolean.FALSE);
	}

}
