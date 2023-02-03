package com.robertBlog.controller;
import com.robertBlog.domain.entity.LoginUser;
import com.robertBlog.domain.entity.User;
import com.robertBlog.domain.entity.Menu;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.domain.vo.AdminUserInfoVo;
import com.robertBlog.domain.vo.RoutersVo;
import com.robertBlog.domain.vo.UserInfoVo;
import com.robertBlog.enums.AppHttpCodeEnum;
import com.robertBlog.exception.SystemException;
import com.robertBlog.service.LoginService;
import com.robertBlog.service.MenuService;
import com.robertBlog.service.RoleService;
import com.robertBlog.utils.BeanCopyUtils;
import com.robertBlog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author Somewherej
 * @date 2022-11-23 11:38
 * @description  后台管理系统的LoginController
 */
// @Controller 一般应用在有返回界面的应用场景下
// @ResponseBody注解的作用简短截说就是指该类中所有的API接口返回的数据，不管你对应的方法返回Map或是其他Object，它会以Json字符串的形式返回给客户端
// @RestController 如果只是接口，那么就用 RestController 来注解.
// @ResponseBody注解的作用简短截说就是指该类中所有的API接口返回的数据，不管你对应的方法返回Map或是其他Object，它会以Json字符串的形式返回给客户端。
@RestController
public class LoginController {
    @Autowired  //注入
    private LoginService loginService;
    @Autowired  //注入
    private MenuService menuService;

    @Autowired  //注入
    private RoleService roleService;

    /**
     * 函数说明:
     *   后台管理系统登录
     *
     * @RequestBody 主要用来接收前端传递给后端的json字符串中的数据
     * json字符串{userName password},
     * 直接用User对象来接受
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //后端的表单校验  只用前端没用
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            //说人话就是捕捉异常封装给前端
            //抛出异常-->SystemException-->GlobalExceptionHandler(捕获到响应给前端)
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    /**
     * 函数说明:
     *   后台管理系退出
     *   删除redis中的用户信息
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }





    /**
     *函数说明:查询当前登录用户的权限信息  角色信息  用户信息
     *请求返回的数据
     *AdminUserInfoVo
     *{
     *"code": 200,
     *"data": {
     *   "permissions": [
     *       "system:user:list",
     *       "system:role:list",
     *       "system:menu:list",
     *       "system:user:query",
     *       "system:user:add",
     *       "system:user:edit",
     *       "system:user:remove",
     *       "system:user:export",
     *       "system:user:import",
     *       "system:user:resetPwd",
     *       "system:role:query",
     *       "system:role:add",
     *       "system:role:edit",
     *       "system:role:remove",
     *       "system:role:export",
     *       "system:menu:query",
     *       "system:menu:add",
     *       "system:menu:edit",
     *       "system:menu:remove",
     *       "content:category:list",
     *       "content:article:list",
     *       "content:article:writer"
     *   ],
     *   "roles": [
     *       "admin"
     *   ],
     *   "user": {
     *       "id": "1",
     *       "nickName": "sg333"
     *   }
     *},
     *"msg": "操作成功"
     *}
     * */
     @GetMapping("getInfo")
     public  ResponseResult<AdminUserInfoVo> getInfo(){
         //获取当前登录的用户
         LoginUser loginUser = SecurityUtils.getLoginUser();

         //根据用户id查询权限信息
         //通过menuService行查询
         //查询返回的是一个字符串集合"permissions"
         List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

         //根据用户id查询角色信息
         List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());


         //获取用户信息
         User user = loginUser.getUser();
         //封装Vo对象向前端传递数据
         UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
         //再封装一次Vo返回
         AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
         //封装数据返回
         return ResponseResult.okResult(adminUserInfoVo);
     }



    /**
     *函数说明:
     * 前端为了实现动态路由的效果,需要后端有接口能返回用户所能访问的菜单数据。
     *请求返回的数据
     *AdminUserInfoVo
     {
        "code":200,
        "data":{
        "menus":[
        {
          "children":[],
          "component":"content/article/write/index",
           "createTime":"2022-01-08 11:39:58",
           "icon":"build",
           "id":2023,
           "menuName":"写博文",
           "menuType":"C",
           "orderNum":"0",
           "parentId":0,
           "path":"write",
           "perms":"content:article:writer",
           "status":"0",
           "visible":"0"
        },
        {
           "children":[
            {
              "children":[],
               "component":"system/user/index",
               "createTime":"2021-11-12 18:46:19",
               "icon":"user",
               "id":100,
               "menuName":"用户管理",
               "menuType":"C",
               "orderNum":"1",
               "parentId":1,
               "path":"user",
               "perms":"system:user:list",
               "status":"0",
               "visible":"0"
        },
     },
     "msg":"操作成功"
     }
     * */
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
         //获取用户的Id
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式(为什么是tree,因为要有子菜单的层级形式)
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
