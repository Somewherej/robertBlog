package com.robertBlog.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Somewherej
 * @date 2022-12-31 2:44
 * @description  将字符串渲染到客户端
 *      * @param response 渲染对象
 *      * @param string 待渲染的字符串
 *      * @return null
 */

public class WebUtils
{

    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            //将http的状态码改为200 http的请求是成功的
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}