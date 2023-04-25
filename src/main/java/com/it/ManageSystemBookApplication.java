package com.it;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.it.dao")
@Slf4j
//@EnableTransactionManagement
public class ManageSystemBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageSystemBookApplication.class, args);
    }

}
