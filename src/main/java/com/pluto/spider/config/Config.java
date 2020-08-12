package com.pluto.spider.config;

import com.pluto.spider.handler.MyHttpClientDownLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hehaijin
 * @date 2020/8/12 10:27
 * @description
 */
@Configuration
public class Config {

    @Bean
    public MyHttpClientDownLoader myHttpClientDownLoader(){
        return new MyHttpClientDownLoader();
    }
}
