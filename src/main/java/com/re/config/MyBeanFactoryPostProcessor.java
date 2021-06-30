package com.re.config;

import com.re.Tools.MyAspectIn;
import com.re.Tools.MyTools;
import com.re.annotation.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义BeanFactoryPostProcessor,在bean实例化前遍历所有的BeanDefinition，获取所有的自定义切面
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private String Pointcut;   //切面注解上的类，代表目标类

    private String PointMethodName;  //切入点方法

    /**
     * 如果发现了某个类被标注 MyAspect注解（自定义切面注解），那么调用doScan(BeanDefinition beanDefinition)扫描这个类
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
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

    /**
     * 在扫描切面类的时候，首先获取切入点注解中的内容，（@MyJoin），
     * 之后扫描所有的方法，如果方面上面标有自定义注解，（@MyAfter,@MyBefore,@MyAround）
     * 如果存在以上注解，调用doScan(className,method,annotation) 切面类名，advice,advice annotation
     * 获取对应的切面信息
     * @param beanDefinition
     */
    public void doScan(BeanDefinition beanDefinition){
        String className = beanDefinition.getBeanClassName();
        Method[] methods = beanDefinition.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(MyJoin.class)){  //如果method上面标注了自定义切入点注解
                Pointcut = method.getAnnotation(MyJoin.class).value();
                PointMethodName=method.getName();
                break;
            }
        }
        for(Method method:methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation item:annotations){
                Class<? extends Annotation> type = item.annotationType();
                if(type.equals(MyTools.MyAfter)||type.equals(MyTools.MyAround)||type.equals(MyTools.MyBefore)){
                    doScan(className,method,item);
                }
            }
        }
    }

    /**
     * 建立目标类和切面类之间的对应关系
     * @param className  切面类
     * @param adviceMethod  advice
     * @param annotation 方法注解
     */
    public void doScan(String className,Method adviceMethod,Annotation annotation){
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        for(Method method:methods){
            if(method.getName().equals("value")){
                try {
                    String value =(String)method.invoke(annotation.getClass().newInstance(), null);
                    if(value.split(",")[0].equals(PointMethodName)){
                        List<MyAspectIn> list = MyTools.map.get(PointMethodName);
                        if(list==null) list=new ArrayList<>();
                        MyAspectIn myAspectIn = new MyAspectIn();
                        myAspectIn.setClassName(className);
                        myAspectIn.setMethodName(adviceMethod.getName());
                        myAspectIn.setAnnatatedType(annotation.annotationType().getName());
                        MyTools.map.put(PointMethodName,list);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    break; //只要找到了注解中value属性的值，那么就跳出循环
                }
            }
        }
    }
}
