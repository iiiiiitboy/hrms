package ex.aaronfae.graduation.jwt;

public interface SysUserService {

	SysUser login(SysUserVO sysUser);

	SysUser getSysUserByUsername(String username);
	
	int saveSysUser(SysUser sysUser);
	
	CurrentSysUserVO currentSysUser();

}
