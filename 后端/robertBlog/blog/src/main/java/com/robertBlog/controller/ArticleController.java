package com.robertBlog.controller;


import com.robertBlog.domain.entity.Article;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Somewherej
 * @data 2022-10-31 11:51
 * @descrip tion
 */
@RestController
@RequestMapping("/article")

public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }



    @PutMapping("/updateViewCount/{id}")
    //查询博客浏览量这个数据是要从Path去取
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }



}
