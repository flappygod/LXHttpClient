package com.flappygod.lipo.lxhttpclient.Generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Response<T> {

    public Class<T> getTClass() {
        //获取super
        Type type = getClass().getGenericSuperclass();
        // Type可以转换成ParameterizedType类型,然后调用getActualTypeArguments类型可以返回泛型数组
        // 由于这里泛型只有1个,所以是0,如果是<T,K>,那么获取K的类型就是1
        Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        //得到泛型类型
        return clazz;
    }

}
