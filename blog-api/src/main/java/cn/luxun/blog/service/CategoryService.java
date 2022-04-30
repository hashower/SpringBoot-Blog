package cn.luxun.blog.service;

import cn.luxun.blog.common.Result;
import cn.luxun.blog.vo.CategoryVo;

public interface CategoryService {
	/**
	 *
	 * @param categoryId
	 * @return
	 */
	CategoryVo findCategoryById(Long categoryId);

	Result findAll();

	Result findAllDetail();

	Result categoryDetailById(Long id);
}
