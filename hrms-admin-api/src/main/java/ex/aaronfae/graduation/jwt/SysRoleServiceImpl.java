package ex.aaronfae.graduation.jwt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.jwt.mapper.SysRoleMapper;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Override
	public int grantRoleToUser(Long roleId, Long sysUserId) {
		return baseMapper.grantRoleToUser(roleId, sysUserId);
	}

}
