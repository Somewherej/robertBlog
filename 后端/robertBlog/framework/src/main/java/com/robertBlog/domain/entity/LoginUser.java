package com.robertBlog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * @author Somewherej
 * @Date 2022-11-20 15:43
 * @Description
 */

@Data   //为类提供读写属性
@AllArgsConstructor //会生成一个包含所有变量，同时如果变量使用了NotNull annotation ， 会进行是否为空的校验，
//全部参数的构造函数的自动生成，该注解的作用域也是只有在实体类上，参数的顺序与属性定义的顺序一致。
@NoArgsConstructor //无参构造函数
public class LoginUser implements UserDetails {
    private  User user;
    // 构造器 初始化 用户名 密码 权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    // 获取账户是否过期状态
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 获取账户是否锁定状态
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 获取凭证是否过期状态
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 获取是否启用状态
    @Override
    public boolean isEnabled() {
        return true;
    }
}
