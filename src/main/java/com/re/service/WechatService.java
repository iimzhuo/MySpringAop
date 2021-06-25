package com.re.service;

import org.springframework.stereotype.Service;

@Service
public class WechatService {

    public void pay(){
        System.out.println("pay success");
    }

    public void receive(){
        System.out.println("receive success");
    }
}
