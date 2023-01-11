package com.robertBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robertBlog.domain.entity.Category;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.domain.vo.CategoryVo;
import com.robertBlog.domain.vo.PageVo;

import java.util.List;


/**
 * @author Somewherej
 * @Date 2022-12-12 9:21
 * @Description
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
