package com.re.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTools {
    public static final String MyAspect="com.re.annotation.MyAspect";

    public static final String MyBefore="com.re.annotation.MyBefore";

    public static final String MyAfter="com.re.annotation.MyAfter";

    public static final String MyAround="com.re.annotation.MyAround";

    //定义target class和advice，之间的对应关系，入过这个类被标记为target class,那么
    public static Map<String, List<MyAspectIn>> map=new HashMap<>();

}
