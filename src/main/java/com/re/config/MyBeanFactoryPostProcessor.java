package com.re.config;

import com.re.Tools.MyTools;
import com.re.annotation.MyJoin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 自定义BeanFactoryPostProcessor,在bean实例化前遍历所有的BeanDefinition，获取所有的自定义切面
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        for(String beanName:beanDefinitionNames){
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
            if(beanDefinition instanceof AnnotatedBeanDefinition){ //如果是一个注解bean
                AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();//获取所有bean定义信息
                Set<String> annotationTypes = metadata.getAnnotationTypes();
                for(String item:annotationTypes){
                    if(item.equals(MyTools.MyAspect)){   //如果上面标有切面注解
                        doScan(beanDefinition);
                    }
                }
            }
        }
    }

    public void doScan(BeanDefinition beanDefinition){
        String className = beanDefinition.getBeanClassName();
        Method[] methods = beanDefinition.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(MyJoin.class)){  //如果method上面标注了自定义切入点注解

            }
        }
    }
}
