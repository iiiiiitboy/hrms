package ex.aaronfae.graduation.business.commute.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.CommuteExceptionDO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteExceptionVO;

public interface CommuteExceptionService {

	CommuteExceptionDO getOneById(Long id);

	IPage<CommuteExceptionDO> getAll(Page<CommuteExceptionDO> page);

	int saveCommuteException(CommuteExceptionDO entity);

	int updateCommuteException(CommuteExceptionDO entity);

	int deleteCommuteExceptionById(Long id);

	List<CommuteExceptionVO> listCommuteExceptionsForEmployee(Long employeeId);

	int quickCommute(CommuteExceptionVO entity);

	Integer countDeductedWorkingHours(Long employeeId, int year, int month);

}
