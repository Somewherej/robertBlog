package com.robertBlog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Somewherej
 * @Date 2022-11-18 11:24
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    @TableId
    private Long id;
    //标题
    private String title;
    //所属分类id
    private String categoryName;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;
    //内容
    private String content;

}
