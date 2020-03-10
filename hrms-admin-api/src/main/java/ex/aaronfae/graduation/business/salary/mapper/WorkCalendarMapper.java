package ex.aaronfae.graduation.business.salary.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ex.aaronfae.graduation.business.salary.entity.WorkCalendarDO;

public interface WorkCalendarMapper extends BaseMapper<WorkCalendarDO> {

	Integer countWeekdays(@Param("firstDay") String firstDay, @Param("lastDay") String lastDay);

}
