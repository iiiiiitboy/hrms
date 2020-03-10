package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.ApplicantDO;
import ex.aaronfae.graduation.business.hr.model.dto.LookApplicantsFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.ApplicantVO;

public interface ApplicantMapper extends BaseMapper<ApplicantDO> {

	List<ApplicantVO> listApplicantsNoEmployedByPage(Page<ApplicantVO> page);

	IPage<ApplicantVO> listApplicantsByPage(Page<ApplicantVO> page);

	String getResumePathById(Long id);

	List<ApplicantVO> listApplicantsByFilter(Page<ApplicantVO> page, @Param("filter") LookApplicantsFilterDTO filter);

}
