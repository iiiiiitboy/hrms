package ex.aaronfae.graduation.jwt.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ex.aaronfae.graduation.jwt.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

	int grantRoleToUser(@Param("roleId") Long roleId, @Param("sysUserId") Long sysUserId);

}
