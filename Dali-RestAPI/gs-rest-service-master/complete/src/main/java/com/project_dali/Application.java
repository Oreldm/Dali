package com.project_dali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import Controllers.ViewerContoller;

@SpringBootApplication
@ComponentScan(basePackageClasses=ViewerContoller.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
