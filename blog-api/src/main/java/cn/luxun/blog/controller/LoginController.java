package cn.luxun.blog.controller;


import cn.luxun.blog.common.Result;
import cn.luxun.blog.service.LoginService;
import cn.luxun.blog.service.SysUserService;
import cn.luxun.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private LoginService loginService;

	@PostMapping
	public Result login(@RequestBody LoginParam loginParam) {
		// 登录 验证用户 访问用户表
		return loginService.login(loginParam);
	}
}
