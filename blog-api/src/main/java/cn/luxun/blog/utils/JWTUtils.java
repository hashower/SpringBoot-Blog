package cn.luxun.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.lettuce.core.ScriptOutputType;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {


	/**
	 * 签发对象：这个用户的id
	 * 签发时间：现在
	 * 有效时间：30分钟
	 * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
	 * 加密密钥：这个人的id加上一串字符串
	 *
	 * @param userId
	 * @param realName
	 * @param userName
	 * @return
	 */

	private static final String jwtToken = "123456Mszlu!@#$$";

	public static String createToken(Long userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId);
		JwtBuilder jwtBuilder = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
				.setClaims(claims) // body数据，要唯一，自行设置
				.setIssuedAt(new Date()) // 设置签发时间
				.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
		String token = jwtBuilder.compact();
		return token;
	}

	public static Map<String, Object> checkToken(String token) {
		try {
			Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
			return (Map<String, Object>) parse.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {


		// BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		// //加密所需的salt
		// textEncryptor.setPassword("mszlu_blog_$#@wzb_&^%$#");
		// //要加密的数据（数据库的用户名或密码）
		// String username = textEncryptor.encrypt("root");
		// String password = textEncryptor.encrypt("123456");
		// System.out.println("username:"+username);
		// System.out.println("password:"+password);
		// System.out.println(textEncryptor.decrypt("29cZ+X9cNmECjbLXT2P/BBZWReVl30NS"));
		//

		String token = JWTUtils.createToken(100L);
		System.out.println(token);
		Map<String, Object> map = JWTUtils.checkToken(token);
		System.out.println(map.get("userId"));

	}


}
