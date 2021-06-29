package com.re.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义切面信息类
 */
public class MyAspectIn {
    private String className;

    private String methodName;

    private String annatatedType;
    //用于存储目标类，和对应的切面信息
    public static Map<String, List<MyAspectIn>> map=new HashMap<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAnnatatedType() {
        return annatatedType;
    }

    public void setAnnatatedType(String annatatedType) {
        this.annatatedType = annatatedType;
    }
}
