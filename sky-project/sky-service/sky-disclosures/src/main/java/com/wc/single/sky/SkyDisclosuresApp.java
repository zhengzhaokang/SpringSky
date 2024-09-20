package com.wc.single.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动程序
 * 
 * @author lovefamily
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.wc.single.sky.*.mapper")
@EnableScheduling
@EnableAsync
public class SkyDisclosuresApp
{
    public static void main(String[] args)
    {
        try {
            SpringApplication.run(SkyDisclosuresApp.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}