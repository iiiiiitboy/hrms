package ex.aaronfae.graduation.business.reimbursement.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;
import ex.aaronfae.graduation.business.reimbursement.entity.ReimbursementDO;
import ex.aaronfae.graduation.business.reimbursement.model.dto.ReimbursementApplyDTO;
import ex.aaronfae.graduation.business.reimbursement.model.vo.ReimbursementVO;
import ex.aaronfae.graduation.business.reimbursement.service.ReimbursementService;

@RestController
@RequestMapping(value = "api")
public class ReimbursementController {

	@Resource
	private ReimbursementService reimbursementService;

	@PostMapping(value = "reimbursements/business-trip")
	public ResponseVO saveReimbursementForBusinessTrip(@RequestParam("files") MultipartFile[] files,
			@RequestParam("id") Long id, @RequestParam("loan") BigDecimal loan,
			@RequestParam("expenses") BigDecimal expenses,
			@RequestParam(value = "debt", defaultValue = "0") BigDecimal debt,
			@RequestParam(value = "reimbursements", defaultValue = "0") BigDecimal reimbursements,
			@RequestParam("description") String description) {
		ReimbursementDO reimbursement = new ReimbursementDO();
		reimbursement.setBusinessTripId(id);
		reimbursement.setLoan(loan);
		reimbursement.setExpenses(expenses);
		reimbursement.setDebt(debt);
		reimbursement.setReimbursements(reimbursements);
		reimbursement.setDescription(description);
		if (reimbursementService.reimburseBusinessTrip(reimbursement, files)) {
			return ResponseVO.ok("提交成功");
		}
		return ResponseVO.error("提交失败");
	}

	@PostMapping(value = "reimbursements/apply")
	public ResponseVO applyReimbursement(ReimbursementApplyDTO entity) {
		int resutl = reimbursementService.applyReimbursement(entity);
		if (resutl > 0) {
			return ResponseVO.ok("提交成功", resutl);
		}
		return ResponseVO.error("提交失败", resutl);
	}

	@GetMapping(value = "reimbursements/my")
	public ResponseVO listMyReimbursements(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<ReimbursementVO> page = new Page<>(current, size);
		return ResponseVO.ok("请求成功", reimbursementService.listMyReimbursements(page));
	}

	@GetMapping(value = "reimbursements")
	public IPage<ReimbursementDO> getAll(@RequestParam(value = "current", defaultValue = "1") long current,
			@RequestParam(value = "size", defaultValue = "10") long size) {
		Page<ReimbursementDO> page = new Page<>(current, size);
		return reimbursementService.getAll(page);
	}

//	@PostMapping(value = "reimbursements")
	public int saveReimbursement(@RequestBody ReimbursementDO entity) {
		return reimbursementService.saveReimbursement(entity);
	}

	@PutMapping(value = "reimbursements")
	public int updateReimbursement(@RequestBody ReimbursementDO entity) {
		return reimbursementService.updateReimbursement(entity);
	}

//	@DeleteMapping("reimbursements")
	public int deleteReimbursementById(Long id) {
		return reimbursementService.deleteReimbursementById(id);
	}

}
