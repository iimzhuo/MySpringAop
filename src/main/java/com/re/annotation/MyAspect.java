package com.re.annotation;

import java.lang.annotation.*;

/**
 * 自定义切面标识注解，
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAspect {
}
