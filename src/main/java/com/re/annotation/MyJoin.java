package com.re.annotation;

import java.lang.annotation.*;

/**
 * 自定义切入点注解
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJoin {
    String value() default "";
}
