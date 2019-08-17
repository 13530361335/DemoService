package com.joker.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 */
@EnableTransactionManagement
@MapperScan("com.joker.sql.dao")
@Configuration
public class MybatisPlusConfig {

    /**
     * mybatis分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
