package com.robertBlog.runner;

import com.robertBlog.domain.entity.Article;
import com.robertBlog.mapper.ArticleMapper;
import com.robertBlog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Somewherej
 * @date 2022-12-29 15:01
 * @description
 *  CommandLineRunner实现项目启动时预处理功能(初始化缓存)
 * 交给Springboot容器去读取
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询所有的博客信息
        List<Article> articles = articleMapper.selectList(null);
        // collect是一个能够把stream管道中的结果集装进一个List集合的终极操作。
        // collect是一个把stream规约成一个value的规约操作
        // Collectors.toMap(获取key和value) (id ViewCount)
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        //存储到redis中  id viewCount
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
