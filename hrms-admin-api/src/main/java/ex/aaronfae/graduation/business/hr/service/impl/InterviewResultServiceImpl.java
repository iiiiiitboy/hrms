package ex.aaronfae.graduation.business.hr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.hr.entity.InterviewResultDO;
import ex.aaronfae.graduation.business.hr.mapper.InterviewResultMapper;
import ex.aaronfae.graduation.business.hr.model.vo.InterviewResultVO;
import ex.aaronfae.graduation.business.hr.service.InterviewResultService;
import ex.aaronfae.graduation.jwt.SysUserService;

@Service
@Transactional
public class InterviewResultServiceImpl extends ServiceImpl<InterviewResultMapper, InterviewResultDO>
		implements InterviewResultService {
	
	@Resource
	private SysUserService sysUserService;

	@Override
	public InterviewResultVO getOneByApplicantIdAndRecruitmentId(Long applicantId, Long recruitmentId) {
		return baseMapper.getOneByApplicantIdAndRecruitmentId(applicantId, recruitmentId);
	}

	@Override
	public IPage<InterviewResultVO> listInterviewResultsByPage(Page<InterviewResultVO> page) {
		return page.setRecords(baseMapper.listInterviewResultsByPage(page));
	}

	@Override
	public int saveInterviewResult(InterviewResultDO entity) {
		Date newDate = new Date();
		entity.setInterviewerId(sysUserService.currentSysUser().getEmployeeId());
		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(entity);
	}

	@Override
	public int updateInterviewResult(InterviewResultDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteInterviewById(Long id) {
		return baseMapper.deleteById(id);
	}

}
