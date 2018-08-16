package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.Generic.Response;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpBaseTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class LXHttpModelTask<T>  extends LXHttpBaseTask<T> {

    /**************
     * 构造器
     *  @param url
     *            地址
     * @param hashMap
     */
    public LXHttpModelTask(String url, HashMap<String, Object> hashMap) {
        super(url, hashMap);
    }


    @Override
    public T preParseData(String data) throws Exception {
        //创建一个对象，用来获取泛型类型
        Response<T> response=new Response<T>(){
        };
        //得到类型
        Class<T> clazz = response.getTClass();
        //使用gson
        Gson gson=new Gson();
        //进行转换
        T t=gson.fromJson(data,clazz);
        //返回数据
        return t;
    }
}
