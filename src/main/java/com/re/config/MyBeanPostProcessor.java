package com.re.config;

import com.re.Tools.MyTools;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * ctrl+I+O 重写快捷键
 * 自定义Bean的后置处理器，用于干预Bean的生命周期，返回对应的代理对象
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在bean实例化后，通过后置处理器返回代理对象
     * @param bean  bean
     * @param beanName  beanName
     * @return proxy bean
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(MyTools.map.containsKey(beanName)){
            Enhancer enhancer=new Enhancer();
            enhancer.setCallback(new MyInterceptor(MyTools.map.get(beanName)));
            enhancer.setSuperclass(bean.getClass());
            enhancer.setClassLoader(bean.getClass().getClassLoader());
            return enhancer.create();
        }
        return bean;
    }
}
