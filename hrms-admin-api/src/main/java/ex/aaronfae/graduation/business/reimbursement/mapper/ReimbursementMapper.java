package ex.aaronfae.graduation.business.reimbursement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.reimbursement.entity.ReimbursementDO;
import ex.aaronfae.graduation.business.reimbursement.model.vo.ReimbursementVO;

public interface ReimbursementMapper extends BaseMapper<ReimbursementDO> {

	List<ReimbursementVO> listMyReimbursements(Page<ReimbursementVO> page, @Param("employeeId") Long employeeId);

}
