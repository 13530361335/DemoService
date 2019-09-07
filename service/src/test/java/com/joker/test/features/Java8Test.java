package com.joker.test.features;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Joker Jing
 * @date: 2019/8/24
 */
public class Java8Test {

    int num = 0;

    @Test
    public void executorsTest() {
        int threadSize = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                while (num < 9) {
                    count();
                }
            });
        }
    }

    public synchronized void count() {
        if (num < 9) {
            num++;
            System.out.println(Thread.currentThread().getId() + ":" + num);
        }
    }

}
