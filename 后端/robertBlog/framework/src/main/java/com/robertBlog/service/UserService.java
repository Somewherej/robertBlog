package com.robertBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robertBlog.domain.entity.User;
import com.robertBlog.domain.entity.domain.ResponseResult;


/**
 * @author Somewherej
 * @Date 2022-12-9 16:22
 * @Description
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    void updateUser(User user);
}

