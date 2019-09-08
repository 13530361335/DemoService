package com.joker.service;

/**
 * @author Joker Jing
 * @date 2019/8/10
 */
public interface TestService {

    /**
     * 启动
     * @param taskId
     */
    void start(String taskId);

    /**
     * 终止
     * @param taskId
     */
    void stop(String taskId);

}
