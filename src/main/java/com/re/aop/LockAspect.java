package com.re.aop;

import com.re.annotation.MyAspect;
import com.re.annotation.MyJoin;
import org.springframework.stereotype.Component;

/**
 * 自定义切面
 */
@MyAspect
@Component
public class LockAspect {
    @MyJoin(value="com.re.service")
    public void MyJoins(){}
}
