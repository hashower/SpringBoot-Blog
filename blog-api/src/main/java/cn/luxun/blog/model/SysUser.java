package cn.luxun.blog.model;

import lombok.Data;

@Data
public class SysUser {

	private Long id;

	private Integer admin;

	private String avatar;

	private Long createDate;

	private Integer deleted;

	private String email;

	private Long lastLogin;

	private String mobilePhoneNumber;

	private String nickname;

	private String password;

	private String salt;

	private String status;

	private String account;


}