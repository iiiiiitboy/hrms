package ex.aaronfae.graduation.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ex.aaronfae.graduation.jwt.mapper.SysUserMapper;

@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Override
	public SysUser login(SysUserVO sysUserVO) {
		SysUser sysUser = getSysUserByUsername(sysUserVO.getUsername());
		if (sysUser != null && new BCryptPasswordEncoder().matches(sysUserVO.getPassword(), sysUser.getPassword())) {
			return sysUser;
		}
		return null;
	}

	@Override
	public SysUser getSysUserByUsername(String username) {
		return baseMapper.getSysUserByUsername(username);
	}

	@Override
	public int saveSysUser(SysUser sysUser) {
		sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
		return baseMapper.insert(sysUser);
	}

	@Override
	public CurrentSysUserVO currentSysUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return baseMapper.getCurrentSysUserByUsername(authentication.getPrincipal().toString());
	}

}
