package com.robertBlog.job;

/**
 * @author Somewherej
 * @date 2022-12-29 15:06
 * @description   更新博客浏览量定时任务
 */
import com.robertBlog.domain.entity.Article;
import com.robertBlog.service.ArticleService;
import com.robertBlog.utils.RedisCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;
    //设置定时执行的时间
    @Scheduled(cron = "*/30 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        //首先将Map转换成流对象   entrySet()元素是key value
        //entry去设置article的对象的id,viewCount
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue())) //String转Long  Integer转Long类型
                .collect(Collectors.toList()); //最后还要对这个流对象进行一次收集操作
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
