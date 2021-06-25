package com.re.annotation;

import java.lang.annotation.*;

/**
 * 自定义环绕通知注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAround {
    String value() default "";
}
