package ex.aaronfae.graduation.jwt;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;

@RestController
public class LoginController {

	@Resource
	private SysUserService sysUserService;

	@PostMapping("/login")
	public Object login(HttpServletResponse response, @RequestBody SysUserVO sysUserVO) throws IOException {
		SysUser sysUser = sysUserService.login(sysUserVO);
		if (sysUser != null) {
			String jwt = JwtUtil.generateToken(sysUser.getUsername(), sysUser.getRoles());
			return ResponseVO.ok("登录成功", jwt);
		} else {
			return new ResponseVO(401, "用户名或密码错误", null);
		}
	}

}
