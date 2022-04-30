package cn.luxun.blog.service.impl;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.controller.ThreadService;
import cn.luxun.blog.mapper.Archives;
import cn.luxun.blog.mapper.ArticleBodyMapper;
import cn.luxun.blog.mapper.ArticleMapper;
import cn.luxun.blog.mapper.ArticleTagMapper;
import cn.luxun.blog.model.Article;
import cn.luxun.blog.model.ArticleBody;
import cn.luxun.blog.model.ArticleTag;
import cn.luxun.blog.model.SysUser;
import cn.luxun.blog.service.ArticleService;
import cn.luxun.blog.service.CategoryService;
import cn.luxun.blog.service.SysUserService;
import cn.luxun.blog.service.TagService;
import cn.luxun.blog.utils.UserThreadLocal;
import cn.luxun.blog.vo.ArticleBodyVo;
import cn.luxun.blog.vo.ArticleVo;
import cn.luxun.blog.vo.TagVo;
import cn.luxun.blog.vo.params.ArticleParam;
import cn.luxun.blog.vo.params.PageParams;
import cn.luxun.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private TagService tagService;


	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ArticleTagMapper articleTagMapper;

	/*@Override
	public Result listArticle(PageParams pageParams) {

		*//*
	 * 1） 分页查询 article数据库表
	 *//*
		Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

		if (pageParams.getCategoryId() != null) {
			queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
		}

		ArrayList<Long> articleIdList = new ArrayList<>();
		if (pageParams.getTagId() != null) {
			// 加入标签 条件查询
			// article表中 没有tag字段  一篇文章有多个标签
			LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
			articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
			List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
			for (ArticleTag articleTag : articleTags) {
				articleIdList.add(articleTag.getArticleId());
			}
			if (articleIdList.size() > 0) {
				queryWrapper.in(Article::getId, articleIdList);
			}
		}
		// //是否指定进行排序
		// // order by create_date desc
		queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
		Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);

		List<Article> records = articlePage.getRecords();

		List<ArticleVo> articleVoList = copyList(records, true, true);
		return Result.success(articleVoList);
	}
*/

	@Override
	public Result listArticle(PageParams pageParams) {
		Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
		IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
		List<Article> records = articleIPage.getRecords();

		return Result.success(copyList(records, true, true));
	}

	@Override
	public Result hotArticle(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getViewCounts);
		queryWrapper.select(Article::getId, Article::getTitle);
		queryWrapper.last("limit " + limit);
		//select id,title from article order by view_counts desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);

		return Result.success(copyList(articles, false, false));
	}

	@Override
	public Result newArticle(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getCreateDate);
		queryWrapper.select(Article::getId, Article::getTitle);
		queryWrapper.last("limit " + limit);
		//select id,title from article order by create_date desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);

		return Result.success(copyList(articles, false, false));
	}

	@Override
	public Result listArchives() {
		List<Archives> archivesList = articleMapper.listArchives();
		return Result.success(archivesList);
	}


	@Autowired
	private ThreadService threadService;

	@Override
	public Result findArticleById(Long articleId) {
		/**
		 * 1 根据id查询 文章信息
		 * 2 根据bodyId 和 categoryId 去做关系
		 */
		Article article = articleMapper.selectById(articleId);
		ArticleVo articleVo = copy(article, true, true, true, true);


		// 查看完文章  新增阅读数，
		// 查看完文章之后 本应直接返回数据 这时候做了一个更新的操作  更新时加写锁， 阻塞其他的读操作 性能就会比较低
		// 更新    增加此次接口的  耗时
		// 线程池 可以把 更新操作 扔到线程池中去执行 和主线程就无关了
		threadService.updateArticleViewCount(articleMapper, article);


		return Result.success(articleVo);
	}

	@Override
	public Result publish(ArticleParam articleParam) {

		// 此接口 要加入到登录拦截中
		SysUser sysUser = UserThreadLocal.get();

		/**
		 * 1 发布文章 构建Article对象
		 * 2 作者id 当前的登录用户
		 * 3 标签 要将标签加入到 关联列表当中
		 * 4 body 内容存储 article bodyId
		 */
		Article article = new Article();
		article = new Article();
		article.setAuthorId(sysUser.getId());
		article.setWeight(Article.Article_Common);
		article.setViewCounts(0);
		article.setTitle(articleParam.getTitle());
		article.setSummary(articleParam.getSummary());
		article.setCommentCounts(0);
		article.setCreateDate(System.currentTimeMillis());
		article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
		//插入之后 会生成一个文章id
		this.articleMapper.insert(article);


		//tag
		List<TagVo> tags = articleParam.getTags();
		if (tags != null) {
			for (TagVo tag : tags) {
				Long articleId = article.getId();
				ArticleTag articleTag = new ArticleTag();
				articleTag.setTagId(Long.parseLong(tag.getId()));
				articleTag.setArticleId(articleId);
				articleTagMapper.insert(articleTag);
			}

		}

		//body
		ArticleBody articleBody = new ArticleBody();
		articleBody.setArticleId(article.getId());
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBodyMapper.insert(articleBody);

		article.setBodyId(articleBody.getId());
		articleMapper.updateById(article);

		Map<String, String> map = new HashMap<>();
		map.put("id", article.getId().toString());
		return Result.success(map);
	}


	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article record : records) {
			// System.out.println("@@@@" + record);
			articleVoList.add(copy(record, isTag, isAuthor, false, false));
		}
		return articleVoList;
	}

	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article record : records) {
			articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
		}
		return articleVoList;
	}

	@Autowired
	private CategoryService categoryService;

	private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(String.valueOf(article.getId()));
		BeanUtils.copyProperties(article, articleVo);
		articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

		// 并不是所有的接口，都需要标签和作者信息
		if (isTag) {
			Long articleId = article.getId();
			articleVo.setTags(tagService.findTagsByArticleId(articleId));
		}
		if (isAuthor) {
			Long authorId = article.getAuthorId();
			SysUser sysUser = sysUserService.findUserById(authorId);
			UserVo userVo = new UserVo();
			userVo.setAvatar(sysUser.getAvatar());
			userVo.setId(String.valueOf(sysUser.getId()));
			userVo.setNickname(sysUser.getNickname());
			articleVo.setAuthor(userVo);
		}
		if (isBody) {
			Long bodyId = article.getBodyId();
			articleVo.setBody(findArticleBodyById(bodyId));
		}

		if (isCategory) {
			Long categoryId = article.getCategoryId();
			System.out.println("@" + categoryService.findCategoryById(categoryId));
			articleVo.setCategory(categoryService.findCategoryById(categoryId));
		}
		return articleVo;
	}

	@Autowired
	private ArticleBodyMapper articleBodyMapper;

	private ArticleBodyVo findArticleBodyById(Long bodyId) {
		ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
		ArticleBodyVo articleBodyVo = new ArticleBodyVo();
		articleBodyVo.setContent(articleBody.getContent());
		return articleBodyVo;
	}


}
