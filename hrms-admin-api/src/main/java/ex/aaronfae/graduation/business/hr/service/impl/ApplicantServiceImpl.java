package ex.aaronfae.graduation.business.hr.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.hr.entity.ApplicantDO;
import ex.aaronfae.graduation.business.hr.mapper.ApplicantMapper;
import ex.aaronfae.graduation.business.hr.model.dto.LookApplicantsFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.ApplicantVO;
import ex.aaronfae.graduation.business.hr.service.ApplicantService;

@Service
@Transactional
public class ApplicantServiceImpl extends ServiceImpl<ApplicantMapper, ApplicantDO> implements ApplicantService {

	@Override
	public int saveApplicant(ApplicantDO applicant) {
		Date newDate = new Date();
		applicant.setGmtCreate(newDate);
		applicant.setGmtModified(newDate);
		applicant.setLogicDeleted(Boolean.FALSE);
		applicant.setInterviewed(Boolean.FALSE);
		applicant.setInterviewed(Boolean.FALSE);
		applicant.setPassed(Boolean.FALSE);
		applicant.setEmployed(Boolean.FALSE);
		return baseMapper.insert(applicant);
	}

	@Override
	public IPage<ApplicantVO> listNoEmployedApplicantsByPage(Page<ApplicantVO> page) {
		return page.setRecords(baseMapper.listApplicantsNoEmployedByPage(page));
	}

	@Override
	public IPage<ApplicantVO> listApplicantsByPage(Page<ApplicantVO> page) {
		return baseMapper.listApplicantsByPage(page);
	}

	@Override
	public IPage<ApplicantVO> listApplicantsByFilter(Page<ApplicantVO> page, LookApplicantsFilterDTO filter) {
		return page.setRecords(baseMapper.listApplicantsByFilter(page, filter));
	}

	@Override
	public String getResumePathById(Long id) {
		return baseMapper.getResumePathById(id);
	}

	@Override
	public int updateApplicant(ApplicantDO applicant) {
		applicant.setGmtModified(new Date());
		return baseMapper.updateById(applicant);
	}

	@Override
	public int delApplicantById(Long id) {
		return baseMapper.deleteById(id);
	}

}
