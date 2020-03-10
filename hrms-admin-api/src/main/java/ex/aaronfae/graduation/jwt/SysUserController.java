package ex.aaronfae.graduation.jwt;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;

@RestController
@RequestMapping("api")
public class SysUserController {

	@Resource
	private SysUserService sysUserService;

	@GetMapping("getUserInfo")
	public ResponseVO getUserInfo() {
		CurrentSysUserVO currentSysUser = sysUserService.currentSysUser();
		ArrayList<String> roles = new ArrayList<String>();
		for (SysRole role : currentSysUser.getRoles()) {
			roles.add(role.getName().substring(role.getName().indexOf('_') + 1));
		}
		return ResponseVO.ok("登录成功", new UserInfoVO(currentSysUser.getName(),
				"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", "测试账号", roles));
	}

	@GetMapping("current")
	public ResponseVO currentSysUser() {
		return ResponseVO.ok("请求成功", sysUserService.currentSysUser());
	}

}
