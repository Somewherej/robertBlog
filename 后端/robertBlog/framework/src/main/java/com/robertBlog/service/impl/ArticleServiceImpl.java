package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robertBlog.constants.SystemConstants;

import com.robertBlog.domain.dto.AddArticleDto;
import com.robertBlog.domain.dto.ArticleDto;
import com.robertBlog.domain.entity.Article;

import com.robertBlog.domain.entity.Category;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.domain.vo.*;
import com.robertBlog.mapper.ArticleMapper;
import com.robertBlog.service.ArticleService;
import com.robertBlog.service.CategoryService;
import com.robertBlog.utils.BeanCopyUtils;
import com.robertBlog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Somewherej
 * @data 2022-10-31 11:47
 * @description
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;


    /**
     *  函数说明:
     *  查询热门文章 封装成ResponseResult返回
     */
    @Override
    public ResponseResult hotArticleList() {
        //queryWrapper是mybatis plus中实现查询的对象封装操作类
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章(草稿 删除文章不能显示)
        //使用字面量而不是字面值（比较好维护)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        //将查询的结果封装进page里面
        page(page,queryWrapper);
        //取出page里面的信息   page.getRecords()就是获取对应的数据
        List<Article> articles = page.getRecords();
        /**
         *  封装Vo的原因?
         *  当前我们查询出来的结果是Article来封装的(无用的字段太多了, 会泄露信息)
         *  一个接口对应一个Vo封装(1.只给前端看 他们该看的    2.后续修改这个接口的返回数据的时候  增删属性  直接修改Vo就行)
         *
         * 初代版本的bean拷贝(bean:bean是计算机自动生成的类 bean拷贝把一个bean对象中的字段拷贝到另一个bean对象的字段)
         * List<HotArticleVo> articleVos = new ArrayList<>();
         * for (Article article : articles) {
         *    HotArticleVo vo = new HotArticleVo();
         *    BeanUtils.copyProperties(article,vo);
         *    articleVos.add(vo);
         * }
         * 改进版本的bean拷贝  直接使用自定义BeanCopyUtils工具类
         */
         List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
         return ResponseResult.okResult(vs);
    }



    /**
     *  函数说明:
     *  分页查询文章
     *  分类id有可能传  有可能不传
     *  查询全部文章 那就不传
     *  查询固定分类的文章  那就传
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // queryWrapper是mybatis plus中实现查询的对象封装操作类
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        /**
         * 是否有categoryId?
         * 如果有   查询的要和传入的相同
         * 参数第一个就是一个控制条件
         * 判断是否有categoryId并且categoryId不为-1(设置为不需要的时候)
         */
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 根据是否置顶进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 控制分页查询大小
        Page<Article> page = new Page<>(pageNum,pageSize);
        // 将查询的结果封装进page里面
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        // 查询categoryName 填回去
        for (Article article : articles) {
           Category category = categoryService.getById(article.getCategoryId());
           article.setCategoryName(category.getName());
        }
        //ArticleListVo封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        /** 返回的结果如下
         * "code":0,
         * "data":{
         *     "rows":[
         *     {
         *     }
         *   ]
         *  "total":0
         * }
         * 还需要用pageVo封装一次
         */
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     *  函数说明:
     *  查询id文章详情
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount(设置定时任务了)
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        //类似要转换一下,Integer转long
        article.setViewCount(viewCount.longValue());
        //articleDetailVo封装响应返回
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }


    /**
     *  函数说明:
     *    更新博客浏览量
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应id的浏览量(已经将数据库中的浏览量存在redis里面了)
        //每次浏览一个文章都会调用updateViewCount这个接口，都会自增1
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        return ResponseResult.okResult();
    }

    @Override
    public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle, article.getTitle());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Article> articles = page.getRecords();

        //这里偷懒没写VO的转换 应该转换完在设置到最后的pageVo中

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(articles);
        return pageVo;
    }

    @Override
    public ArticleVo getInfo(Long id) {
        Article article = getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article,ArticleVo.class);
        return articleVo;
    }
    /**
     *  函数说明:
     *   更新文章信息
     */
    @Override
    public void edit(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
    }
}
