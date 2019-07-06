package com.joker.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

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


}