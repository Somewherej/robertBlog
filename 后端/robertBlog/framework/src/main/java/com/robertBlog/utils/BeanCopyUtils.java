package com.robertBlog.utils;

import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Somewherej
 * @data 2022-11-16 17:04
 * @description Bean拷贝工具类
 * 简单来说就是把一个bean对象中的字段靠被到另一个bean对象的字段
 * 它在spring项目中常用来封装VO对象向前端传递数据
 * 例如：当前A对象有6个字段，而前端只需获取其中3个字段，
 * 我们则需将此3个字段封装(VO)返回前端，而不应将A中的6个字段(PO)返回前端
 * 但是我们每次都new 对象，然后get/set 赋值会很繁琐，代码复用性和效率不高，
 * 因此我们可以使用bean提供的BeanUtils工具类
 * https://blog.csdn.net/weixin_51799151/article/details/122815120
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }
    /** 1、单个对象
     *  Object source:要拷贝的对象
     *  Class<Generic> genericClass: 字节码对象  通过字节码文件对象获取对应的元素的对象(反射)
     *  每个类在加载的时候都会创建一个字节码对象
     *   <Generic> Generic: 定义使用方法泛型控制返回值类型(在不创建新的类型的情况下，通过泛型指定的不同类型来控制形参具体限制的类型)
     */
    public static <Generic> Generic copyBean(Object source,Class<Generic> genericClass) {
        //创建目标对象 实现属性拷贝
        Generic result = null;
        try {
            // newInstance()  使用对应类的无参构造方法来创建该类的实例，
            result = genericClass.newInstance();
            // 实现属性copy   从source拷贝到result
            BeanUtils.copyProperties(source, result);
        } catch (Exception exception) {
            // 如果出异常
            // printStackTrace() 在命令行打印异常信息在程序中出错的位置及原因
            exception.printStackTrace();
        }
        //实现结果的返回
        return result;
    }
    /** 2、集合*/
    public static <Object,Generic> List<Generic> copyBeanList(List<Object> list, Class<Generic> genericClass){
        //创建目标对象 实现属性拷贝
        return list.stream()  //转成流行时处理
                .map(o -> copyBean(o, genericClass)) //map()是对集合中的对象进行映射的,将对象从一种类型转换成另一种类型
                //使用copyBean直接转换o的类型
                //流中的元素o都是用copyBean方法
                .collect(Collectors.toList()); //再对最后的结果进行收集
    }
}