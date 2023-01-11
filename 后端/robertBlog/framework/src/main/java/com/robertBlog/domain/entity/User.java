package com.robertBlog.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2022-11-20 15:29:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User  {
    //主键@TableId
    private Long id;
    //用户名
    private String userName;
    //密码
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //关联角色id数组，非user表字段
    @TableField(exist = false)
    private Long[] roleIds;

}
