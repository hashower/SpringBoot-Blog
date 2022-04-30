package cn.luxun.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import cn.luxun.blog.model.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
	//防止前端 精度损失 把id转为string
	// @JsonSerialize(using = ToStringSerializer.class)
	private String id;

	private UserVo author;

	private String content;

	private List<CommentVo> childrens;

	private Long createDate;

	private Integer level;

	private UserVo toUser;
}
