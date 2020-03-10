package ex.aaronfae.graduation.business.trip.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import ex.aaronfae.graduation.business.trip.entity.BusinessTripDO;
import ex.aaronfae.graduation.business.trip.model.dto.BusinessTripConfirmDTO;
import ex.aaronfae.graduation.business.trip.model.dto.BusinessTripsDTO;
import ex.aaronfae.graduation.business.trip.model.vo.BusinessTripVO;
import ex.aaronfae.graduation.business.trip.service.BusinessTripService;
import ex.aaronfae.graduation.jwt.SysUserService;

@RestController
@RequestMapping(value = "api")
public class BusinessTripController {

	@Resource
	private BusinessTripService businessTripService;

	@Resource
	private SysUserService sysUserService;

	@PostMapping(value = "business-trips")
	public ResponseVO saveBusinessTrip(@RequestBody BusinessTripDO entity) {
		entity.setEmployeeId(sysUserService.currentSysUser().getEmployeeId());
		int result = businessTripService.saveBusinessTrip(entity);
		if (result > 0) {
			return ResponseVO.ok("提交成功", Boolean.TRUE);
		}
		return ResponseVO.ok("提交失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@GetMapping(value = "business-trips/my")
	public ResponseVO myLeave() {
		return ResponseVO.ok("请求成功",
				businessTripService.listMyBusinessTrips(sysUserService.currentSysUser().getEmployeeId()));
	}

	@GetMapping("business-trips/page")
	public ResponseVO listBusinessTripsByPage(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<BusinessTripVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", businessTripService.listBusinessTripsByPage(page));
	}

	@PostMapping(value = "business-trips/arranging")
	public ResponseVO arrangingBusinessTrip(@RequestBody BusinessTripsDTO entity) {
		Date nowDate = new Date();
		List<BusinessTripDO> list = new ArrayList<BusinessTripDO>();
		for (int i = 0; i < entity.getEmployeeIds().size(); i++) {
			BusinessTripDO item = new BusinessTripDO(entity.getEmployeeIds().get(i), entity.getStartDate(),
					entity.getEndDate(), entity.getExpenses(), entity.getReason(), Boolean.FALSE, Boolean.TRUE,
					Boolean.FALSE, null, Boolean.FALSE);
			item.setGmtCreate(nowDate);
			item.setGmtModified(nowDate);
			item.setLogicDeleted(Boolean.FALSE);
			list.add(item);
		}
		int result = businessTripService.saveBusinessTripBatch(list);
		if (result > 0) {
			return ResponseVO.ok("提交成功", Boolean.TRUE);
		}
		return ResponseVO.ok("提交失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@PutMapping(value = "business-trips/confirm")
	public ResponseVO confirmBusinessTrip(@RequestBody BusinessTripConfirmDTO entity) {
		int result = businessTripService.confirmBusinessTrip(entity);
		if (result > 0) {
			return ResponseVO.ok("确认成功", Boolean.TRUE);
		}
		return ResponseVO.ok("确认失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@PutMapping(value = "business-trips/check-in")
	public ResponseVO checkInBusinessTrip(@RequestBody BusinessTripConfirmDTO entity) {
		int result = businessTripService.checkInBusinessTrip(entity);
		if (result > 0) {
			return ResponseVO.ok("报道成功", Boolean.TRUE);
		}
		return ResponseVO.ok("报道失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@PutMapping(value = "business-trips")
	public ResponseVO updateBusinessTrip(@RequestBody BusinessTripDO entity) {
		int result = businessTripService.updateBusinessTrip(entity);
		if (result > 0) {
			return ResponseVO.ok("修改成功", Boolean.TRUE);
		}
		return ResponseVO.ok("修改失败，请重试或联系系统管理员", Boolean.FALSE);
	}

	@DeleteMapping(value = "business-trips")
	public ResponseVO deleteBusinessTripById(Long id) {
		int result = businessTripService.deleteBusinessTripById(id);
		if (result > 0) {
			return ResponseVO.ok("删除成功", Boolean.TRUE);
		}
		return ResponseVO.ok("删除失败，请重试或联系系统管理员", Boolean.FALSE);
	}

}
