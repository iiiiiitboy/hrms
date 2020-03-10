package ex.aaronfae.graduation.business.commute.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ex.aaronfae.graduation.business.commute.entity.CommuteExceptionDO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteExceptionVO;

public interface CommuteExceptionMapper extends BaseMapper<CommuteExceptionDO> {

	List<CommuteExceptionVO> listCommuteExceptionsForEmployee(Long employeeId);

	Integer countDeductedWorkingHours(@Param("employeeId") Long employeeId, @Param("firstDay") String firstDay,
			@Param("lastDay") String lastDay);

}
