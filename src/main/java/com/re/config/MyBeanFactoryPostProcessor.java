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

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
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
        Class<?> aClass=null;
        try {
             aClass= Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = aClass.getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(MyJoin.class)){  //如果method上面标注了自定义切入点注解
                Pointcut = method.getAnnotation(MyJoin.class).value();
                PointMethodName=method.getName()+"()";
                break;
            }
        }
        for(Method method:methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation item:annotations){
                String type = item.annotationType().getName();
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
                    String value =(String)method.invoke(annotation, null);
                    if(value.equals(PointMethodName)){
                        URL resource = this.getClass().getClassLoader().getResource("./");
                        String path = resource.getPath()+Pointcut.replace(".","/");
                        path = path.substring(1);
                        getFiles(new File(path));
                        for(File item:FileList){
                            String class_Name = item.getAbsolutePath().split("classes")[1].substring(1);
                            class_Name=class_Name.substring(0,class_Name.indexOf(".")).replace("\\",".");
                            List<MyAspectIn> list = MyTools.map.get(class_Name);
                            if(list==null) list=new ArrayList<>();
                            MyAspectIn myAspectIn = new MyAspectIn();
                            myAspectIn.setClassName(className);
                            myAspectIn.setMethodName(adviceMethod.getName());
                            myAspectIn.setAnnatatedType(annotation.annotationType().getName());
                            list.add(myAspectIn);
                            MyTools.map.put(class_Name,list);
                        }
                        FileList.clear();  //情况之前存放的文件夹
                        break;//只要找到value一次，那么就直接break
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<File> FileList=new ArrayList<>();

    /**
     * 获取文件夹下所有文件
     * @param file 文件
     */
    public void getFiles(File file){
        File[] files = file.listFiles();
        for(File item:files){
            if(item.isFile()&&item.getName().endsWith(".class")){
                FileList.add(item);
            }else if(item.isDirectory()){
                getFiles(item);
            }
        }
    }
}
