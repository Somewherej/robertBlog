package com.robertBlog.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author Somewherej
 * @Date 2022-12-12 11:39
 * @Description
 */
@Data
@Accessors(chain = true)
public class UserVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
}
