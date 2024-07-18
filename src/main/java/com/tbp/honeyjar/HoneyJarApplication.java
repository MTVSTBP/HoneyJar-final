package com.tbp.honeyjar;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.tbp.honeyjar", annotationClass = Mapper.class)
public class HoneyJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoneyJarApplication.class, args);
    }
}
