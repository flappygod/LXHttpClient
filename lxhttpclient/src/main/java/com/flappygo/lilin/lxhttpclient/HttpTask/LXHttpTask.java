package com.flappygo.lilin.lxhttpclient.HttpTask;

import com.flappygo.lilin.lxhttpclient.HttpTask.Base.LXHttpBaseTask;

import java.util.HashMap;

/******************
 * LXHttpTask 请求任务
 */
public class LXHttpTask extends LXHttpBaseTask<String> {

    /**************
     * 构造器
     *  @param url
     *            地址
     * @param hashMap
     */
    public LXHttpTask(String url, HashMap<String, Object> hashMap) {
        super(url, hashMap);
    }


    @Override
    public String preParseData(String data) throws Exception {
        return data;
    }
}
