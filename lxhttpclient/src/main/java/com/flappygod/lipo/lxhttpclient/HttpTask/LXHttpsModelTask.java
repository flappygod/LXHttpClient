package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LXHttpsModelTask<M> extends LXHttpsBaseTask<M> {


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
    public  M  preParseData(String data) throws Exception{
        Type  jsonType = new TypeToken<M>() {}.getType();
        M rootBean = new Gson().fromJson(data, jsonType);
        return rootBean;
    }
}
