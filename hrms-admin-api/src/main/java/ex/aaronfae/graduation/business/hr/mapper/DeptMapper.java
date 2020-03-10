package ex.aaronfae.graduation.business.hr.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ex.aaronfae.graduation.business.hr.entity.DeptDO;
import ex.aaronfae.graduation.business.organization.model.vo.DeptVO;

public interface DeptMapper extends BaseMapper<DeptDO> {

	List<DeptDO> listDeptsIdAndName();

	List<DeptVO> listDeptByPage(Page<DeptVO> page);

}
