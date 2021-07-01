package com.re;

import com.re.service.WechatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Demo_01 {

    @Autowired
    private WechatService service;

    @Test
    public void Test_01(){
        System.out.println(service.getClass());
    }
}
