package ex.aaronfae.graduation.business.commute.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.CommuteDO;
import ex.aaronfae.graduation.business.commute.model.dto.CommuteListFilterDTO;
import ex.aaronfae.graduation.business.commute.model.vo.ClockVO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteVO;
import ex.aaronfae.graduation.business.commute.model.vo.RecentCommuteRecordVO;

public interface CommuteMapper extends BaseMapper<CommuteDO> {

	List<CommuteVO> listCommuteExceptionsByPage(Page<CommuteVO> page);

	List<CommuteVO> listTodyCommutesByPage(Page<CommuteVO> page);

	List<CommuteVO> listHistoryCommutesByPage(Page<CommuteVO> page);

	List<CommuteVO> listHistoryCommutesByFilter(Page<CommuteVO> page, @Param("filter") CommuteListFilterDTO filter);

	Integer countDaysOfAttendance(@Param("employeeId") Long employeeId, @Param("firstDay") String firstDay,
			@Param("lastDay") String lastDay);

	ClockVO isClock(@Param("employeeId") Long id);

	List<RecentCommuteRecordVO> getRecentCommuteRecord(Long employeeId);

	int toWork(@Param("employeeId") Long id, @Param("date") Date date);

	int offWork(@Param("employeeId") Long id, @Param("date") Date date);

}
