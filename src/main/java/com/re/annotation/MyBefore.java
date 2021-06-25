package com.re.annotation;

import java.lang.annotation.*;

/**
 * 自定义前置通知注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyBefore {
    String value() default "";
}
