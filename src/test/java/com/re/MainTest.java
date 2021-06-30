package com.re;

import com.re.aop.LockAspect;
import com.re.service.WechatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyAopApplication.class);
        WechatService bean = context.getBean(WechatService.class);
        System.out.println(bean.getClass());
        bean.pay();
    }
}
