package com.robertBlog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Somewherej
 * @Date 2022-11-18 12:10
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    @TableId
    private Long id;
    //标题
    private String title;
    //所属分类id
    private Long categoryId;
    //所属分类id
    private String categoryName;
    //文章内容
    private String content;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;

}
