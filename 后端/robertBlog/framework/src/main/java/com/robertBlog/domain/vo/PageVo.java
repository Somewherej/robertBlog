package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Somewherej
 * @date 2022-11-18 11:28
 * @description PageVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo{
    private List rows;
    private Long total;
}
