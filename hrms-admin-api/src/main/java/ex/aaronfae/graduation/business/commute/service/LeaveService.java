package ex.aaronfae.graduation.business.commute.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.LeaveDO;
import ex.aaronfae.graduation.business.commute.model.vo.LeaveVO;

public interface LeaveService {

	int saveLeave(LeaveDO entity);

	int updateLeave(LeaveDO entity);

	int deleteLeaveById(Long id);

	List<LeaveVO> listMyLeaves(Long employeeId);

	Page<LeaveVO> listLeaves(Page<LeaveVO> page);

}
