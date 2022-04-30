package cn.luxun.blog.mapper;

import cn.luxun.blog.model.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

	List<Archives> listArchives();

	IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}