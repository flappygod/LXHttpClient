package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LXHttpsModelTask<T> extends LXHttpsBaseTask<T> {

    private Class<T> mClass;

    /**************
     * 构造器
     *  @param url
     *            地址
     * @param hashMap
     */
    public LXHttpsModelTask(String url, HashMap<String, Object> hashMap ,Class<T> mClass) {
        super(url, hashMap);
        this.mClass=mClass;
    }


    @Override
    public T preParseData(String data) throws Exception {
        T rootBean = new Gson().fromJson(data,mClass);
        return rootBean;
    }
}
