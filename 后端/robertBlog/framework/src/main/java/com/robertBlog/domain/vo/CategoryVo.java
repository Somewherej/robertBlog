package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Somewherej
 * @date 2022-11-17 11:29
 * @description CategoryVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    //文章id
    private Long id;
    //名字
    private String name;
    //描述
    private String description;
}