package cn.luxun.blog.service;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.vo.params.ArticleParam;
import cn.luxun.blog.vo.params.PageParams;

public interface ArticleService {

	/**
	 * 分页查询 文章列表
	 *
	 * @param pageParams
	 * @return
	 */
	Result listArticle(PageParams pageParams);

	/**
	 * 首页 最热文章
	 *
	 * @param limit
	 * @return
	 */
	Result hotArticle(int limit);

	/**
	 * 首页 最新文章
	 *
	 * @param limit
	 * @return
	 */
	Result newArticle(int limit);

	/**
	 * 文章归档
	 *
	 * @return
	 */
	Result listArchives();

	/**
	 *  查看文章详情
	 * @param articleId
	 * @return
	 */
	Result findArticleById(Long articleId);

	/**
	 * 文章发布服务
	 * @param articleParam
	 * @return
	 */
	Result publish(ArticleParam articleParam);
}
