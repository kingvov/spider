package com.pluto.spider;

import com.pluto.spider.processor.NationalAreaFillProcessor;
import com.pluto.spider.processor.NationalAreaProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.Spider;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.pluto.spider.dao", "com.pluto.spider.entity"})
public class SpiderApplication {
    public static ApplicationContext APPLICATION_CONTEXT;

    public static void main(String[] args) {
        APPLICATION_CONTEXT = SpringApplication.run(SpiderApplication.class, args);


//        Spider.create(APPLICATION_CONTEXT.getBean(NationalAreaProcessor.class))
//                .addUrl(NationalAreaProcessor.NATIONNAL_AREA_URL)
//                .thread(1)
//                .run();
        Spider.create(APPLICATION_CONTEXT.getBean(NationalAreaFillProcessor.class))
                .addUrl(NationalAreaProcessor.NATIONNAL_AREA_URL)
                .thread(1)
                .run();
    }
}
