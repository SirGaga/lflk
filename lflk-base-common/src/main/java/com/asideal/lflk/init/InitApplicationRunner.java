package com.asideal.lflk.init;

import com.asideal.lflk.config.DynamicDatasource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class InitApplicationRunner implements ApplicationRunner {
    @Autowired
    private DynamicDatasource dynamicDatasource;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("-------------初始化start-------------");
        //动态创建数据源
        log.info("动态创建数据源");
        //dynamicDatasource.createNewDataSource();
        log.info("-------------初始化end-------------");
    }
}
