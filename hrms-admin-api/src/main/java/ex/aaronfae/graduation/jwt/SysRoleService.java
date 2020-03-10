package ex.aaronfae.graduation.jwt;

public interface SysRoleService {

	int grantRoleToUser(Long roleId, Long sysUserId);

}
