package com.re.annotation;

import java.lang.annotation.*;

/**
 * 开启切面注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyEnableAspect {
}
