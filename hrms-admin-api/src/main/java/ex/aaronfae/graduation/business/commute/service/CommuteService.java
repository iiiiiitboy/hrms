package ex.aaronfae.graduation.business.commute.service;

import java.util.Date;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.commute.entity.CommuteDO;
import ex.aaronfae.graduation.business.commute.model.dto.CommuteListFilterDTO;
import ex.aaronfae.graduation.business.commute.model.vo.ClockVO;
import ex.aaronfae.graduation.business.commute.model.vo.CommuteVO;

public interface CommuteService {

	int saveCommute(CommuteDO entity);

	int updateCommute(CommuteDO entity);

	int deleteCommuteById(Long id);

	IPage<CommuteVO> listCommuteExceptionsByPage(Page<CommuteVO> page);

	IPage<CommuteVO> listTodyCommutesByPage(Page<CommuteVO> page);

	IPage<CommuteVO> listHistoryCommutesByPage(Page<CommuteVO> page);

	IPage<CommuteVO> listHistoryCommutesByFilter(Page<CommuteVO> page, CommuteListFilterDTO filter);

	int callMorningAttentionToEmployee(CommuteVO entity);

	int callAfternoonAttentionToEmployee(CommuteVO entity);

	/**
	 * 查询今天是否打卡
	 * 
	 * @param id
	 * @return
	 */
	ClockVO isClock();

	int toWork(Date date);

	int offWork(Date date);

	/**
	 * 周一到周五每日凌晨两点插入所有在职员工的预通勤记录
	 * 
	 */
	boolean insertCommuteDaily();

	/**
	 * 基础员工的月出勤天数
	 * 
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	Integer countDaysOfAttendance(Long employeeId, int year, int month);

}
