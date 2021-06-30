package com.re.Tools;

/**
 * 自定义切面信息类
 */
public class MyAspectIn {
    private String className;

    private String methodName;

    private String annatatedType;

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
