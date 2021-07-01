package com.re.config;

import com.re.MyAopApplication;
import com.re.Tools.MyAspectIn;
import com.re.Tools.MyTools;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

import java.util.List;
import java.util.Map;

/**
 * ctrl+I+O 重写快捷键
 * 自定义Bean的后置处理器，用于干预Bean的生命周期，返回对应的代理对象
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result=bean;
        String name = bean.getClass().getName();
        if(MyTools.map.containsKey(name)){
            Enhancer enhancer=new Enhancer();
            MyInterceptor interceptor = new MyInterceptor(MyTools.map.get(name));
            enhancer.setCallback(interceptor);
            enhancer.setSuperclass(bean.getClass());
            result= enhancer.create();
        }
        return result;
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
        return bean;
    }
}
