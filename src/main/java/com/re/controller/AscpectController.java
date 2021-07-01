package com.re.controller;

import com.re.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.WeakCacheKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AscpectController {
    @Autowired
    private WechatService service;

    @GetMapping
    public String Test(){
        System.out.println(service.getClass());
        service.pay();
        return "success";
    }
}
