package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.JobDO;
import ex.aaronfae.graduation.business.organization.model.vo.JobVO;

public interface JobMapper extends BaseMapper<JobDO> {

	List<JobDO> getJobByDeptName(String deptName);

	List<JobDO> listJobsIdAndNameByDeptId(Long deptId);

	List<JobVO> listJobByPage(Page<JobVO> page);

}
