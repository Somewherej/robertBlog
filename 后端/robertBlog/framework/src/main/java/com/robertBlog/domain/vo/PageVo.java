package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Somewherej
 * @Date 2022-11-18 11:28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo{
    private List rows;
    private Long total;
}
