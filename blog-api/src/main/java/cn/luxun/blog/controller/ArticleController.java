package cn.luxun.blog.controller;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.common.aop.LogAnnotation;
import cn.luxun.blog.common.cache.Cache;
import cn.luxun.blog.model.Article;
import cn.luxun.blog.service.ArticleService;
import cn.luxun.blog.vo.params.ArticleParam;
import cn.luxun.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 首页 文章列表
	 *
	 * @param pageParams
	 * @return
	 */
	@PostMapping
	// 加上此注解 代表对此接口记录日志
	@LogAnnotation(module = "文章", operator = "获取文章列表")
	@Cache(expire = 5 * 60 * 1000,name = "listArticle")
	public Result listArticle(@RequestBody PageParams pageParams) {
		return articleService.listArticle(pageParams);
	}

	/**
	 * 首页 最热文章
	 *
	 * @return
	 */
	@PostMapping("hot")
	@Cache(expire = 5 * 60 * 1000,name = "hot_article")
	public Result hotArticle() {
		int limit = 5;
		return articleService.hotArticle(limit);
	}


	/**
	 * 首页 最新文章
	 *
	 * @return
	 */
	@PostMapping("new")
	@Cache(expire = 5 * 60 * 1000,name = "news_article")
	public Result newArticle() {
		int limit = 5;
		return articleService.newArticle(limit);
	}


	/**
	 * 首页 最新文章
	 *
	 * @return
	 */
	@PostMapping("listArchives")
	public Result listArchives() {
		int limit = 5;
		return articleService.listArchives();
	}


	@PostMapping("view/{id}")
	@Cache(expire = 5 * 60 * 1000,name = "view_article")
	public Result findArticleById(@PathVariable("id") Long articleId) {
		return articleService.findArticleById(articleId);

	}

	@PostMapping("publish")
	public Result publish(@RequestBody ArticleParam articleParam) {
		return articleService.publish(articleParam);
	}

}
