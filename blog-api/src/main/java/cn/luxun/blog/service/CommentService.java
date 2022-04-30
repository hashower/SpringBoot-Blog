package cn.luxun.blog.service;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.vo.params.CommentParam;

public interface CommentService {


	/**
	 * 根据文章id 查询所有的评论列表
	 *
	 * @param id
	 * @return
	 */
	Result commentsByArticleId(Long id);

	Result comment(CommentParam commentParam);
}
