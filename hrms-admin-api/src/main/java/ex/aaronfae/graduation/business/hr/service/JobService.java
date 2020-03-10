package ex.aaronfae.graduation.business.hr.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.JobDO;
import ex.aaronfae.graduation.business.organization.model.vo.JobVO;

public interface JobService {

	JobDO getJobById(Long id);

	List<JobDO> getJobByDeptId(Long deptId);

	List<JobDO> getJobByDeptName(String deptName);

	List<JobDO> getJobByName(String jobName);

	List<JobDO> getAllJob();

	List<JobDO> getAllJobWithoutDel();

	int saveJob(JobDO job);

	int updateJob(JobDO job);

	int delJob(Long id);

	List<JobDO> listJobsIdAndNameByDeptId(Long deptId);

	IPage<JobVO> listJobByPage(Page<JobVO> page);

}
