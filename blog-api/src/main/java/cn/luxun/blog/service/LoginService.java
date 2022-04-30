package cn.luxun.blog.service;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.model.SysUser;
import cn.luxun.blog.vo.params.LoginParam;


public interface LoginService {

	/**
	 * 登录功能
	 *
	 * @param loginParam
	 * @return
	 */
	Result login(LoginParam loginParam);

	SysUser checkToken(String token);

	/**
	 * 退出登录
	 *
	 * @param token
	 * @return
	 */
	Result logout(String token);

	/**
	 * 注册
	 * @param loginParam
	 * @return
	 */
	Result register(LoginParam loginParam);
}
