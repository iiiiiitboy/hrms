package ex.aaronfae.graduation.business.commute.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.LeaveDO;
import ex.aaronfae.graduation.business.commute.model.vo.LeaveVO;

public interface LeaveMapper extends BaseMapper<LeaveDO> {

	List<LeaveVO> listMyLeaves(Long employeeId);

	List<LeaveVO> listLeaves(Page<LeaveVO> page);

}
