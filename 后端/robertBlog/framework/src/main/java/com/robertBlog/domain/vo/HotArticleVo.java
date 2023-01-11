package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Somewherej
 * @data 2022-11-16 16:48
 * @descrip tion
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    //文章ID
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;

}
