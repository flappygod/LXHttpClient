package com.flappygod.lipo.lxhttpclient.Interface;

/**
 * Created by Administrator on 2017/12/17.
 */

public interface LXHttpTaskPreParseInterface {

    /*************
     * 继承    LXHttpTask     时实现此方法将字符串数据转换为实体对象数据
     * @param data          数据
     * @return               返回数据
     * @throws Exception     错误
     */
    public Object preParseData(Object data) throws  Exception;

}
