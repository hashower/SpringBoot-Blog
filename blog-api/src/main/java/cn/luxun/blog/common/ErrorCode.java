package cn.luxun.blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum ErrorCode {

	PARAMS_ERROR(10001, "参数有误"),
	ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),
	TOKEN_ERROR(10003, "token不合法"),
	ACCOUNT_EXIST(10004, "账号已存在"),
	NO_PERMISSION(70001, "无访问权限"),
	SESSION_TIME_OUT(90001, "会话超时"),
	NO_LOGIN(90002, "未登录"),
	;

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private int code;

	private String msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}


}
