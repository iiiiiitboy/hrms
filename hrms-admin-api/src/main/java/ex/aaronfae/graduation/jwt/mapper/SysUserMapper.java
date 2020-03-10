package ex.aaronfae.graduation.jwt.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ex.aaronfae.graduation.jwt.CurrentSysUserVO;
import ex.aaronfae.graduation.jwt.SysRole;
import ex.aaronfae.graduation.jwt.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

	SysUser getSysUserByUsername(String username);

	List<SysRole> listRolesByUserId(Long id);

	int saveSysUser(SysUser sysUser);

	CurrentSysUserVO getCurrentSysUserByUsername(String username);

}
