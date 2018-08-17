package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.Exception.LXParseException;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public abstract class LXHttpsModelTask<T> extends LXHttpsBaseTask<T> {

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
        Class<T> tClass = getTClass();
        this.tClass = tClass;
    }

    private Class<T> getTClass() {
        try {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return tClass;
        } catch (Exception exception) {
            throw new LXParseException("can't parse unknown type T");
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
        return gson.fromJson(data, tClass);
    }
}
