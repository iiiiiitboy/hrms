package ex.aaronfae.graduation.business.hr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.ApplicantDO;
import ex.aaronfae.graduation.business.hr.model.dto.LookApplicantsFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.ApplicantVO;

public interface ApplicantService {

	int saveApplicant(ApplicantDO entity);

	IPage<ApplicantVO> listNoEmployedApplicantsByPage(Page<ApplicantVO> page);

	IPage<ApplicantVO> listApplicantsByPage(Page<ApplicantVO> page);

	String getResumePathById(Long id);

	int updateApplicant(ApplicantDO entity);

	int delApplicantById(Long id);

	IPage<ApplicantVO> listApplicantsByFilter(Page<ApplicantVO> page, LookApplicantsFilterDTO filter);

}
