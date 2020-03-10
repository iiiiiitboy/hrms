package ex.aaronfae.graduation.business.hr.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.business.hr.entity.JobDO;
import ex.aaronfae.graduation.business.hr.mapper.JobMapper;
import ex.aaronfae.graduation.business.hr.service.JobService;
import ex.aaronfae.graduation.business.organization.model.vo.JobVO;

@Service
@Transactional
public class JobServiceImpl extends ServiceImpl<JobMapper, JobDO> implements JobService {

	@Override
	public JobDO getJobById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public List<JobDO> getJobByDeptId(Long deptId) {
		Wrapper<JobDO> queryWrapper = new QueryWrapper<JobDO>().eq("dept_id", deptId).orderByAsc("id");
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<JobDO> getJobByDeptName(String deptName) {
		return baseMapper.getJobByDeptName(deptName);
	}

	@Override
	public List<JobDO> getJobByName(String jobName) {
		Wrapper<JobDO> queryWrapper = new QueryWrapper<JobDO>().eq("name", jobName);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<JobDO> getAllJob() {
		return baseMapper.selectList(null);
	}

	@Override
	public List<JobDO> getAllJobWithoutDel() {
		Wrapper<JobDO> queryWrapper = new QueryWrapper<JobDO>().eq("logic_delete", 0);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public int saveJob(JobDO job) {
		Date newDate = new Date();
		job.setGmtCreate(newDate);
		job.setGmtModified(newDate);
		job.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(job);
	}

	@Override
	public int updateJob(JobDO job) {
		job.setGmtModified(new Date());
		return baseMapper.updateById(job);
	}

	@Override
	public int delJob(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public List<JobDO> listJobsIdAndNameByDeptId(Long deptId) {
		return baseMapper.listJobsIdAndNameByDeptId(deptId);
	}

	@Override
	public IPage<JobVO> listJobByPage(Page<JobVO> page) {
		return page.setRecords(baseMapper.listJobByPage(page));
	}

}
