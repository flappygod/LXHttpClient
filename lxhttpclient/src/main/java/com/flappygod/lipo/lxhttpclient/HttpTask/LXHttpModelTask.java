package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpBaseTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LXHttpModelTask<M>  extends LXHttpBaseTask<M> {

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
    public M preParseData(String data) throws Exception {
        Type jsonType = new TypeToken<M>() {}.getType();
        M rootBean = new Gson().fromJson(data, jsonType);
        return rootBean;
    }
}
