package ex.aaronfae.graduation.business.hr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.InterviewResultDO;
import ex.aaronfae.graduation.business.hr.model.vo.InterviewResultVO;

public interface InterviewResultService {

	InterviewResultVO getOneByApplicantIdAndRecruitmentId(Long applicantId, Long recruitmentId);

	IPage<InterviewResultVO> listInterviewResultsByPage(Page<InterviewResultVO> page);

	int saveInterviewResult(InterviewResultDO entity);

	int updateInterviewResult(InterviewResultDO entity);

	int deleteInterviewById(Long id);

}
