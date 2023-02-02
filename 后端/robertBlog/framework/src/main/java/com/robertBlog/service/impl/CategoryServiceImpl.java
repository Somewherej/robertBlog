package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robertBlog.constants.SystemConstants;
import com.robertBlog.domain.entity.Article;
import com.robertBlog.domain.entity.Category;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.domain.vo.CategoryVo;
import com.robertBlog.domain.vo.PageVo;
import com.robertBlog.mapper.CategoryMapper;
import com.robertBlog.service.ArticleService;
import com.robertBlog.service.CategoryService;
import com.robertBlog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Somewherej
 * @Date 2022-11-17 11:00
 * @Description
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * CategoryServiceImpl不能查询Article表
     * 所以需要注入articleService
     */
    @Autowired
    private ArticleService articleService;


    /**
     *  函数说明:
     *  查询文章分类 封装成ResponseResult返回
     */
    @Override
    public ResponseResult getCategoryList() {
        //queryWrapper是mybatis plus中实现查询的对象封装操作类
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章(草稿 删除文章不能显示)
        //使用字面量而不是字面值（比较好维护)
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //CategoryServiceImpl不能查询Article表  所以需要注入articleService
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id 但是需要去重
        Set<Long> categoryIds = articleList.stream()    //转换成stream流形式
                .map(article -> article.getCategoryId())  //获取每个元素article的文章分类
                .collect(Collectors.toSet());   //流整成集合(他会自动去重)
        //根据分类id  查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                //必须这个分ID是正常的可以显示的
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }



    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Category> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}