package ex.aaronfae.graduation.business.hr.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.base.util.HanyuPinyinHelper;
import ex.aaronfae.graduation.business.hr.entity.ApplicantDO;
import ex.aaronfae.graduation.business.hr.entity.EmployeeDO;
import ex.aaronfae.graduation.business.hr.mapper.EmployeeMapper;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeIdsAndSalariesDTO;
import ex.aaronfae.graduation.business.hr.model.dto.EmployeeListFilterDTO;
import ex.aaronfae.graduation.business.hr.model.vo.EmployeeVO;
import ex.aaronfae.graduation.business.hr.model.vo.IdsAndNamesForQuickCommuteVO;
import ex.aaronfae.graduation.business.hr.model.vo.NamesAndIdsForQuickCommuteVO;
import ex.aaronfae.graduation.business.hr.service.ApplicantService;
import ex.aaronfae.graduation.business.hr.service.EmployeeService;
import ex.aaronfae.graduation.jwt.SysRoleService;
import ex.aaronfae.graduation.jwt.SysUser;
import ex.aaronfae.graduation.jwt.SysUserService;

@Service
@Transactional
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeDO> implements EmployeeService {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private ApplicantService applicantService;

	@Override
	public EmployeeDO getOneById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public IPage<EmployeeVO> listEmployeesByPage(Page<EmployeeVO> page) {
		return page.setRecords(baseMapper.listEmployeesByPage(page));
	}

	@Override
	public int saveEmployee(EmployeeDO entity) {
		Date newDate = new Date();

		ApplicantDO applicant = new ApplicantDO();
		applicant.setId(entity.getApplicantId());
		applicant.setEmployed(Boolean.TRUE);
		applicantService.updateApplicant(applicant);

		entity.setGmtCreate(newDate);
		entity.setGmtModified(newDate);
		entity.setLogicDeleted(Boolean.FALSE);
		baseMapper.insert(entity);

		SysUser sysUser = new SysUser();
		sysUser.setEmployeeId(entity.getId());
		sysUser.setName(entity.getName());
		sysUser.setUsername(HanyuPinyinHelper.toHanyuPinyin(entity.getName()) + entity.getId());// 用户名规则：姓名拼音+ID
		sysUser.setPassword("123456");// 默认密码
		sysUser.setRemarks("");
		sysUser.setEnabled(Boolean.TRUE);
		sysUser.setGmtCreate(newDate);
		sysUser.setGmtModified(newDate);
		sysUser.setLogicDeleted(Boolean.FALSE);
		sysUserService.saveSysUser(sysUser);
		return sysRoleService.grantRoleToUser(1L, sysUser.getId());
	}

	@Override
	public int updateEmployee(EmployeeDO entity) {
		entity.setGmtModified(new Date());
		return baseMapper.updateById(entity);
	}

	@Override
	public int deleteEmployeeById(Long id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public List<Long> listOnlineEmployeeIds() {
		return baseMapper.listOnlineEmployeeIds();
	}

	@Override
	public List<EmployeeIdsAndSalariesDTO> listAllEmployeeIdsAndSalaries() {
		return baseMapper.listAllEmployeeIdsAndSalaries();
	}

	@Override
	public List<NamesAndIdsForQuickCommuteVO> listEmployeeNamesAndIds() {
		return baseMapper.listEmployeeNamesAndIds();
	}

	@Override
	public List<IdsAndNamesForQuickCommuteVO> listEmployeeIdsAndNames() {
		return baseMapper.listEmployeeIdsAndNames();
	}

	@Override
	public IPage<EmployeeVO> listEmployeesByFilter(Page<EmployeeVO> page, EmployeeListFilterDTO filter) {
		return page.setRecords(baseMapper.listEmployeesByFilter(page, filter));
	}

}
