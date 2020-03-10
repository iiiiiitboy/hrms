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

import ex.aaronfae.graduation.business.hr.entity.DeptDO;
import ex.aaronfae.graduation.business.hr.mapper.DeptMapper;
import ex.aaronfae.graduation.business.hr.service.DeptService;
import ex.aaronfae.graduation.business.organization.model.vo.DeptVO;

@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptDO> implements DeptService {

	@Override
	public DeptDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public DeptDO getOneByName(String deptName) {
		Wrapper<DeptDO> queryWrapper = new QueryWrapper<DeptDO>().eq("name", deptName);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IPage<DeptDO> getAll(Page<DeptDO> page) {
		return baseMapper.selectPage(page, null);
	}

	@Override
	public int saveDept(DeptDO dept) {
		Date newDate = new Date();
		dept.setGmtCreate(newDate);
		dept.setGmtModified(newDate);
		dept.setLogicDeleted(Boolean.FALSE);
		return baseMapper.insert(dept);
	}

	@Override
	public int updateDept(DeptDO dept) {
		dept.setGmtModified(new Date());
		return baseMapper.updateById(dept);
	}

	@Override
	public int delDeptById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public List<DeptDO> listDeptsIdAndName() {
		return baseMapper.listDeptsIdAndName();
	}

	@Override
	public IPage<DeptVO> listDeptByPage(Page<DeptVO> page) {
		return page.setRecords(baseMapper.listDeptByPage(page));
	}

}
