package com.re.config;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义selector注解，用于配合@MyEnableAspect使用,想容器中注入两个组件
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MyBeanFactoryPostProcessor.class.getName(),MyBeanPostProcessor.class.getName()};
    }
}
