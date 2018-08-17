package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.Exception.LXParseException;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public abstract class LXHttpsModelTask<T> extends LXHttpsBaseTask<T> {
    private Exception exception;

    private Gson gson = new Gson();

    private Class<T> tClass;

    /**************
     * 构造器
     *  @param url
     *            地址
     * @param hashMap
     */
    public LXHttpsModelTask(String url, HashMap<String, Object> hashMap) {
        super(url, hashMap);
        //获取class
        Class<T> tClass = getTClass();
        //保存class
        this.tClass = tClass;
    }

    private Class<T> getTClass() {
        try {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return tClass;
        } catch (Exception exception) {
            this.exception=exception;
            return null;
        }
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public T preParseData(String data) throws Exception {
        try {
            //如果存在
            if(tClass!=null){
                //使用gson解析
                T t= gson.fromJson(data, tClass);
                //返回
                return t;
            }else{
                //解析失败
                parseFailed(exception);
            }
        }catch (Exception ex){
            //失败
            parseFailed(ex);
        }
        return null;
    }

    //解析失败
    public abstract void parseFailed(Exception excepiton);
}
