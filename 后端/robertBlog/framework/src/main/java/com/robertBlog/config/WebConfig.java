package com.robertBlog.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Somewherej
 * @data 2022-11-16 11:42
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     *  函数说明:
     *    解决跨域问题
     *    浏览器出于安全考虑，限制访问本站点以为的资源
     *    URL由协议、域名、端口和路径组成
     *    只要协议、域名、端口有任何一个不同，就是跨域
     *    例如:
     *      访问 127.0.0.1:8080 的资源不会受到限制
     *      访问 127.0.0.1:8081 的资源就会受到限制
     *   我们的项目是前后端分离的项目
     *   简单的来说就是后台提供数据，前端负责显示
     *   在前后端分离之前，用的都是一个端口号，不存在跨域问题
     *   但是前后端分离之后前端端口(8080)，后端端口(8888)就会产生跨域问题
     *
     *
     *   解决方法:
     *     通过实现WebMvcConfigurer接口然后重写addCorsMappings方法解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径  项目中所有接口都支持跨域
        registry.addMapping("/**")
                // 设置允许跨域请求的域名  前端哪些域名可以跨域（这里是全部都可以）
                .allowedOriginPatterns("*")
                // 是否允许cookie  自己的的项目需要带cookie凭证
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }


    /**
     *  函数说明:
     *    修改时间日期的格式
     *    原来的Date类型的展示的默认的json的时间日期格式yyyy-MM-dd HH:mm:ss
     */
    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //先定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //添加fastjson的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }
    /**
     *  函数说明:
     *  配置消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //将fastJsonHttpMessageConverters添加到converters当中
        converters.add(fastJsonHttpMessageConverters());
    }


}