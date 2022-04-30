package cn.luxun.blog.controller;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.mapper.SysUserMapper;
import cn.luxun.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("currentUser")
	public Result currentUser(@RequestHeader("Authorization") String token) {
		return sysUserService.findUserByToken(token);

	}
}
