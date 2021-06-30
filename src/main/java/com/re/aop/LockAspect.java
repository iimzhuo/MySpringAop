package com.re.aop;

import com.re.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 自定义切面
 */
@MyAspect
@Component
public class LockAspect {
    @MyJoin(value="com.re.service")
    public void MyJoins(){}

    @MyBefore("MyJoins()")
    public void MyBefore(){
        System.out.println("before advice");
    }

    @MyAfter("MyJoins()")
    public void MyAfter(){
        System.out.println("after advice");
    }

    @MyAround("MyJoins()")
    public void MyAround(){
        System.out.println("around advice");
    }
}
