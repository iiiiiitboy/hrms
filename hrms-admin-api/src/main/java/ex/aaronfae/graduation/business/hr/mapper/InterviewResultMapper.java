package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.InterviewResultDO;
import ex.aaronfae.graduation.business.hr.model.vo.InterviewResultVO;

public interface InterviewResultMapper extends BaseMapper<InterviewResultDO> {

	InterviewResultVO getOneByApplicantIdAndRecruitmentId(@Param("applicantId") Long applicantId,
			@Param("recruitmentId") Long recruitmentId);

	List<InterviewResultVO> listInterviewResultsByPage(Page<InterviewResultVO> page);

}
