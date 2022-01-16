package com.wq.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 王强
 * @create: 2021-12-23 09:30
 */
public class BeanUtils {

    /**
     * 去当前类 所有成员变量名（不包含父类）
     * @param clazz
     * @return
     */
    public static List<String> getPropertyNames(Class clazz){
        Field[]  fields = clazz.getDeclaredFields();
        List<String> results = new ArrayList<>(fields.length);

        for(Field field: fields){
            results.add(field.getName());
        }
        return results;
    }
}
