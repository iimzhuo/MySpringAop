package com.re.config;

import com.re.Tools.MyAspectIn;
import com.re.Tools.MyTools;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义拦截器，用于返回代理对象
 */
public class MyInterceptor implements MethodInterceptor {

    private List<MyAspectIn> list=new ArrayList<>();

    public MyInterceptor(List<MyAspectIn> list) {
        this.list = list;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        for(MyAspectIn item:list){
            if (item.getAnnatatedType().equals(MyTools.MyBefore) || item.getAnnatatedType().equals(MyTools.MyAround)) {
                this.doProxy(item);
            }
        }

        Object result = null;
        try {
            result = methodProxy.invokeSuper(o, objects);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        for(MyAspectIn item:list){
            if(item.getAnnatatedType().equals(MyTools.MyAfter)||item.getAnnatatedType().equals(MyTools.MyAround)){
                this.doProxy(item);
            }
        }
        return result;
    }

    /**
     * 执行切面方法
     * @param myAspectIn 切面
     */
    public void doProxy(MyAspectIn myAspectIn){
        String className = myAspectIn.getClassName();
        String methodName = myAspectIn.getMethodName();
        try {
            Class<?> aClass = Class.forName(className);
            Method[] methods = aClass.getDeclaredMethods();
            for(Method method:methods){
                if(method.getName().equals(methodName)){
                    method.invoke(aClass.newInstance(),null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
