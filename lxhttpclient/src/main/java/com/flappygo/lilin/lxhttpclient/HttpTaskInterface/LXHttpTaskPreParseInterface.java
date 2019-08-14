package com.flappygo.lilin.lxhttpclient.HttpTaskInterface;

/**
 * Created by Administrator on 2017/12/17.
 */

public interface LXHttpTaskPreParseInterface<T>{

    /*************
     * 继承    LXHttpTask     实现此方法将
     * @param data          数据
     * @return               返回数据
     * @throws Exception     错误
     */
    public T preParseData(String data) throws  Exception;

}
