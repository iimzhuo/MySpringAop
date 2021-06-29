package com.re;

import com.re.annotation.MyEnableAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MyEnableAspect
public class MyAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAopApplication.class, args);
    }
}
