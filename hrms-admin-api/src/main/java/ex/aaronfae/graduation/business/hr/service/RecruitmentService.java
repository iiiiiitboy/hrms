package ex.aaronfae.graduation.business.hr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.RecruitmentDO;
import ex.aaronfae.graduation.business.hr.model.dto.RecruitmentFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentForApplicantVO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentVO;

public interface RecruitmentService {

	int saveRecruitment(RecruitmentDO entity);

	IPage<RecruitmentVO> listRecruitmentsByPage(Page<RecruitmentVO> page);

	int countApplicant(Long recruitmentId);

	int updateRecruitment(RecruitmentDO entity);

	int deleteRecruitmentById(Long id);

	IPage<RecruitmentVO> listRecruitmentsByFilter(Page<RecruitmentVO> page, RecruitmentFilterDTO filter);

	IPage<RecruitmentForApplicantVO> listRecruitmentForApplicant(Page<RecruitmentForApplicantVO> page);

}
