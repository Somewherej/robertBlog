package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Somewherej
 * @date 2022-12-12 17:57
 * @description   ArticleVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    //文章id
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    private List<Long> tags;
}