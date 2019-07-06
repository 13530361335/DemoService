package com.joker.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class JSONUtil {

    public static String toString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public static <T> T change(Object object, Class<T> clazz) {
        return toObject(toString(object),clazz);
    }

//    public static List getDeclaredFields(Class<?> clazz) throws IllegalArgumentException {
//        List<String> fieldNames = new LinkedList();
//        Field[] fields = clazz.getDeclaredFields();
//        for(int i = 0 ; i < fields.length ; i++) {
//            fieldNames.add(fields[i].getName());
//        }
//    }

}