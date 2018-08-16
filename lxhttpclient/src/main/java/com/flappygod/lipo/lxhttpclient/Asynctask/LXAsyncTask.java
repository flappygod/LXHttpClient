package com.flappygod.lipo.lxhttpclient.Asynctask;

/**************
 * 异步回调
 * 
 * @author lijunlin
 */
public interface LXAsyncTask extends LXAsyncCallback {

	/******************
	 * 线程执行
	 * @param data    传入数据
	 * @param tag     tag
	 * @return
	 * @throws Exception  错误
     */
	Object run(Object data,String tag) throws Exception;


}
