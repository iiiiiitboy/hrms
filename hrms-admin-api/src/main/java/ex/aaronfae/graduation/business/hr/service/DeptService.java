package ex.aaronfae.graduation.business.hr.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.DeptDO;
import ex.aaronfae.graduation.business.organization.model.vo.DeptVO;

public interface DeptService {

	DeptDO getOneById(Long id);

	DeptDO getOneByName(String deptName);

	IPage<DeptDO> getAll(Page<DeptDO> page);

	int saveDept(DeptDO entity);

	int updateDept(DeptDO entity);

	int delDeptById(Long id);

	List<DeptDO> listDeptsIdAndName();

	IPage<DeptVO> listDeptByPage(Page<DeptVO> page);

}
