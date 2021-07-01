package com.re;

import com.re.annotation.MyEnableAspect;
import com.re.service.WechatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@MyEnableAspect
@ComponentScan("com.re")
public class MyAopApplication {

    public static void main(String[] args) {
        //SpringApplication.run(MyAopApplication.class, args);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyAopApplication.class);
        WechatService bean = context.getBean(WechatService.class);
        System.out.println(bean.getClass());
        bean.pay();
    }
}
