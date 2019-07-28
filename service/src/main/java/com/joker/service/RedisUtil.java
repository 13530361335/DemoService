package com.joker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * CreateTime: 2018-12-21 21:46
 * ClassName: RedisUtil
 * Package: com.ywh.common.utils
 * Describe:
 * Redis工具类
 *
 * @author YWH
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //---------------------- common --------------------------

    /**
     * 指定缓存失效时间
     *
     * @param key  key值
     * @param time 缓存时间
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);

    }

    /**
     * 判断key是否存在
     *
     * @param key 传入ke值
     * @return true 存在  false  不存在
     */
    public Boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断key存储的值类型
     *
     * @param key key值
     * @return DataType[string、list、set、zset、hash]
     */
    public DataType typeKey(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 删除指定的一个数据
     *
     * @param key key值
     * @return true 删除成功，否则返回异常信息
     */
    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);

    }

    /**
     * 删除多个数据
     *
     * @param keys key的集合
     * @return true删除成功，false删除失败
     */
    public Boolean deleteKey(Collection<String> keys) {
        return redisTemplate.delete(keys) != 0;
    }

    //-------------------- String ----------------------------

    /**
     * 普通缓存放入
     *
     * @param key   键值
     * @param value 值
     * @return true成功 要么异常
     */
    public Boolean setString(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object getString(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存存在时间
     *
     * @param key   key值
     * @param value value值
     * @param time  时间 秒为单位
     * @return 成功返回true，失败返回异常信息
     */
    public boolean setString(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        return true;
    }

    //----------------------------- list ------------------------------

    /**
     * 将list放入缓存
     *
     * @param key   key的值
     * @param value 放入缓存的数据
     * @return true 代表成功，否则返回异常信息
     */
    public Boolean setList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
        return true;
    }

    /**
     * 将Object数据放入List缓存，并设置时间
     *
     * @param key   key值
     * @param value 数据的值
     * @param time  缓存的时间
     * @return true插入成功，否则返回异常信息
     */
    public Boolean setList(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForList().rightPush(key, value);
            expire(key, time);
            return true;
        }
        return false;
    }

    /**
     * 将list集合放入List缓存，并设置时间
     *
     * @param key   key值
     * @param value 数据的值
     * @param time  缓存的时间
     * @return true插入成功，否则返回异常信息
     */
    public Boolean setListAll(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForList().rightPushAll(key, value);
            this.expire(key, time);
            return true;
        }
        return false;
    }

    /**
     * 根据索引获取缓存List中的内容
     *
     * @param key   key的值
     * @param start 索引开始
     * @param end   索引结束 0 到 -1代表所有值
     * @return 返回数据
     */
    public List<Object> getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 删除List缓存中多个list数据
     *
     * @param key   key值
     * @param count 移除多少个
     * @param value 可以传null  或者传入存入的Value的值
     * @return 返回删除了多少个
     */
    public long deleteListIndex(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 获取List缓存的数据
     *
     * @param key key值
     * @return 返回长度
     */
    public long getListSize(String key) {
        return redisTemplate.opsForList().size(key);

    }

}
