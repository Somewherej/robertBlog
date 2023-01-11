package com.robertBlog.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Somewherej
 * @data 2022-11-16 17:04
 * @descrip tion
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <Generic> Generic copyBean(Object source,Class<Generic> genericClass) {
        //创建目标对象
        Generic result = null;
        try {
            result = genericClass.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        //实现结果的返回
        return result;
    }
    public static <O,Generic> List<Generic> copyBeanList(List<O> list, Class<Generic> genericClassic){
        return list.stream()
                .map(o -> copyBean(o, genericClassic))
                .collect(Collectors.toList());
    }
}