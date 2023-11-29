package com.course.server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil {
    /**
     * 将list列表中的对象都copy成T类型 然后形成T类型的列表返回
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List source, Class<T> clazz){
        List<T> target = new ArrayList<>();
        if(!CollectionUtils.isEmpty(source)){
            for(Object c: source){
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }

    /**
     * 将对象source copy成T类型的类对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> clazz){
        if (source == null){
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }
}
