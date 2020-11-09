package com.yjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:system.properties",encoding = "utf-8")
@EnableAspectJAutoProxy
public class GuguanjiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuguanjiaApplication.class, args);
    }

}
