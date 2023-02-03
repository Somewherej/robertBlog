package com.robertBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robertBlog.domain.dto.AddArticleDto;
import com.robertBlog.domain.dto.ArticleDto;
import com.robertBlog.domain.entity.Article;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.domain.vo.ArticleVo;
import com.robertBlog.domain.vo.PageVo;


/**
 * @author Somewherej
 * @Date 2022-12-11 17:46
 * @Description
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    /**
     *  函数说明:
     *    更新博客浏览量
     */
    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto article);
}
