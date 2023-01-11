package com.robertBlog.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Somewherej
 * @Date 2022-11-20 18:00
 * @Description
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    //主键
    private Long id;
    //昵称
    private String nickName;
}
