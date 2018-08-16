package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.Generic.Response;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LXHttpsModelTask<T> extends LXHttpsBaseTask<T> {


    /**************
     * 构造器
     *  @param url     地址
     * @param hashMap 请求参数
     */
    public LXHttpsModelTask(String url, HashMap<String, Object> hashMap) {
        super(url, hashMap);
    }

    /*****************
     * 预处理解析数据
     * @param data          数据
     * @return
     * @throws Exception
     */
    public  T  preParseData(String data) throws Exception{
        //创建一个对象，用来获取类型
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
