package ex.aaronfae.graduation.business.reimbursement.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.reimbursement.entity.ReimbursementDO;
import ex.aaronfae.graduation.business.reimbursement.model.dto.ReimbursementApplyDTO;
import ex.aaronfae.graduation.business.reimbursement.model.vo.ReimbursementVO;

public interface ReimbursementService {

	ReimbursementDO getOneById(Long id);

	IPage<ReimbursementDO> getAll(Page<ReimbursementDO> page);

	int saveReimbursement(ReimbursementDO entity);

	int updateReimbursement(ReimbursementDO entity);

	int deleteReimbursementById(Long id);

	boolean reimburseBusinessTrip(ReimbursementDO entity, MultipartFile[] files);

	int applyReimbursement(ReimbursementApplyDTO entity);

	Page<ReimbursementVO> listMyReimbursements(Page<ReimbursementVO> page);

}
