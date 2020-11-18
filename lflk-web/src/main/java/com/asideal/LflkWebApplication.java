package com.asideal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.asideal.**.mapper")
//开启SwaggerUI
@EnableSwagger2
public class LflkWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LflkWebApplication.class,args);
        //test
    }
}
