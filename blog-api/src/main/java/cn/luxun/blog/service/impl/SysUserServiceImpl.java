package cn.luxun.blog.service.impl;

import cn.luxun.blog.common.ErrorCode;
import cn.luxun.blog.common.Result;
import cn.luxun.blog.mapper.SysUserMapper;
import cn.luxun.blog.model.SysUser;
import cn.luxun.blog.service.LoginService;
import cn.luxun.blog.service.SysUserService;
import cn.luxun.blog.vo.LoginUserVo;
import cn.luxun.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private LoginService loginService;

	@Override
	public UserVo findUserVoById(Long id) {
		SysUser sysUser = sysUserMapper.selectById(id);
		if (sysUser == null) {
			sysUser = new SysUser();
			sysUser.setId(1L);
			sysUser.setAvatar("/static/img/logo.b3a48c0.png");
			sysUser.setNickname("少年闰土");
		}
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(sysUser, userVo);
		userVo.setId(String.valueOf(sysUser.getId   () ));
		return userVo;
	}

	@Override
	public SysUser findUserById(Long id) {

		SysUser sysUser = sysUserMapper.selectById(id);
		if (sysUser == null) {
			sysUser = new SysUser();
			sysUser.setNickname("少年闰土");
		}
		return sysUserMapper.selectById(id);

	}

	@Override
	public SysUser findUser(String account, String password) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.eq(SysUser::getPassword, password);
		queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
		queryWrapper.last("limit 1");

		return sysUserMapper.selectOne(queryWrapper);
	}

	@Override
	public Result findUserByToken(String token) {

		/**
		 * 1 token合法性校验
		 *      是否为空，解析是否成功 redis是否存在
		 * 2 如果校验失败 返回错误
		 * 3 如果成功 返回对应的结果   LoginUserVo
		 */
		SysUser sysUser = loginService.checkToken(token);
		if (sysUser == null) {
			return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
		}
		LoginUserVo loginUserVo = new LoginUserVo();
		loginUserVo.setId(String.valueOf(sysUser.getId()));
		loginUserVo.setNickname(sysUser.getNickname());
		loginUserVo.setAvatar(sysUser.getAvatar());
		loginUserVo.setAccount(sysUser.getAccount());
		return Result.success(loginUserVo);
	}

	@Override
	public SysUser findUserByAccount(String account) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.last("limit 1");
		return sysUserMapper.selectOne(queryWrapper);
	}

	@Override
	public void save(SysUser sysUser) {
		// 保存用户 id 会自动生成
		// 默认生成的id 是分布式iid 雪花算法
		sysUserMapper.insert(sysUser);
	}

}
