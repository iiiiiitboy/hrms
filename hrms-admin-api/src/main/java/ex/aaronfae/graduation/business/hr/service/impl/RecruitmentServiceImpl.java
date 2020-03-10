package ex.aaronfae.graduation.business.hr.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.hr.entity.RecruitmentDO;
import ex.aaronfae.graduation.business.hr.mapper.RecruitmentMapper;
import ex.aaronfae.graduation.business.hr.model.dto.RecruitmentFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentForApplicantVO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentVO;
import ex.aaronfae.graduation.business.hr.service.RecruitmentService;

@Service
@Transactional
public class RecruitmentServiceImpl extends ServiceImpl<RecruitmentMapper, RecruitmentDO>
		implements RecruitmentService {

	@Override
	public int saveRecruitment(RecruitmentDO recruitment) {
		Date newDate = new Date();
		recruitment.setGmtCreate(newDate);
		recruitment.setGmtModified(newDate);
		recruitment.setLogicDeleted(Boolean.FALSE);
		recruitment.setReviewed(Boolean.FALSE);
		recruitment.setFinished(Boolean.FALSE);
		return baseMapper.insert(recruitment);
	}

	@Override
	public IPage<RecruitmentVO> listRecruitmentsByPage(Page<RecruitmentVO> page) {
		return page.setRecords(baseMapper.listRecruitments(page));
	}

	@Override
	public int countApplicant(Long recruitmentId) {
		return baseMapper.countApplicant(recruitmentId);
	}

	@Override
	public int updateRecruitment(RecruitmentDO recruitment) {
		if ((recruitment.getReviewed() != null) || (recruitment.getFinished() != null)
				|| (recruitment.getDeptId() != null) || (recruitment.getJobId() != null)
				|| (recruitment.getTypeOfWorking() != null) || (recruitment.getYearsOfWorking() != null)
				|| (recruitment.getNumberOf() != null) || (recruitment.getAddressOfWorking() != null)
				|| (recruitment.getEducation() != null) || (recruitment.getRequirement() != null)) {
			recruitment.setGmtModified(new Date());
			return baseMapper.updateById(recruitment);
		} else {
			return 0;
		}
	}

	@Override
	public int deleteRecruitmentById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public IPage<RecruitmentVO> listRecruitmentsByFilter(Page<RecruitmentVO> page, RecruitmentFilterDTO filter) {
		return page.setRecords(baseMapper.listRecruitmentsByFilter(page, filter));
	}

	@Override
	public IPage<RecruitmentForApplicantVO> listRecruitmentForApplicant(Page<RecruitmentForApplicantVO> page) {
		return page.setRecords(baseMapper.listRecruitmentForApplicant(page));
	}

}
