package com.robertBlog.controller;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Somewherej
 * @data 2022-10-31 11:51
 * @description   blog的ArticleController
 */
@RestController
@RequestMapping("/article")

public class ArticleController {
    /**
     * @Autowired 是一个注释
     * 对类成员变量、方法及构造函数进行标注
     * 让 spring 完成 bean 自动装配的工作。
     */
     //service层对自动调用mapper层去查询数据库
    @Autowired
    private ArticleService articleService;

    /**
     *  函数说明:
     *  查询热门文章 封装成ResponseResult返回
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    /**
     *  函数说明:
     *  分页查询文章
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }


    /**
     *  函数说明:
     *    更新博客浏览量
     */
    @PutMapping("/updateViewCount/{id}")
    //查询博客浏览量这个数据是要从Path去取
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }


    /**
     *  函数说明:
     *  查询文章详情
     *  Spring会对@PathVariable注解的变量进行自动赋值
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }



}
