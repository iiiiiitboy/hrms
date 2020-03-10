package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.RecruitmentDO;
import ex.aaronfae.graduation.business.hr.model.dto.RecruitmentFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentForApplicantVO;
import ex.aaronfae.graduation.business.hr.model.vo.RecruitmentVO;

public interface RecruitmentMapper extends BaseMapper<RecruitmentDO> {

	List<RecruitmentVO> listRecruitments(Page<RecruitmentVO> page);

	int countApplicant(Long recruitmentId);

	List<RecruitmentVO> listRecruitmentsByFilter(Page<RecruitmentVO> page,
			@Param("filter") RecruitmentFilterDTO filter);

	List<RecruitmentForApplicantVO> listRecruitmentForApplicant(Page<RecruitmentForApplicantVO> page);

}
