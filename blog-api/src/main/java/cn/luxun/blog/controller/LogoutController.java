package cn.luxun.blog.controller;


import cn.luxun.blog.common.Result;
import cn.luxun.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

	@Autowired
	private LoginService loginService;

	@GetMapping
	public Result logout(@RequestHeader("Authorization") String token) {
		// 登录 验证用户 访问用户表
		return loginService.logout(token);
	}
}
