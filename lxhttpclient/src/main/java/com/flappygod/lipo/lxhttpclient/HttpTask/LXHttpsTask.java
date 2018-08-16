package com.flappygod.lipo.lxhttpclient.HttpTask;

import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;

import java.util.HashMap;

public class LXHttpsTask extends LXHttpsBaseTask<String> {

    /**************
     * 构造器
     *  @param url     地址
     * @param hashMap 请求参数
     */
    public LXHttpsTask(String url, HashMap<String, Object> hashMap) {
        super(url, hashMap);
    }

    @Override
    public String preParseData(String data) throws Exception {
        return data;
    }
}
