package com.re;

import com.re.annotation.MyEnableAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MyEnableAspect
//@ComponentScan("com.re")
public class MyAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAopApplication.class, args);
    }
}
