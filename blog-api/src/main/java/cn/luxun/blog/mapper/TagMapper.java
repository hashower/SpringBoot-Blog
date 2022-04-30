package cn.luxun.blog.mapper;

import cn.luxun.blog.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

	List<Tag> findTagsByArticleId(Long articleId);

	/**
	 * 查询最热的标签 前limit个
	 * @param limit
	 * @return
	 */
	List<Long> findHotsTagIds(int limit);



	List<Tag> findTagByTagIds(List<Long> tagIds);
}
