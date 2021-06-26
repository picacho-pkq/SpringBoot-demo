package com.pikacho;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.pikacho.fileStorage.dao")
public class FilesStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesStorageApplication.class, args);
    }

}
