package com.flappygod.lipo.lxhttpclient.Asynctask;

/**************
 * 异步回调执行接口
 * @param <M>  输入
 * @param <T>  输出
 */
public interface  LXAsyncTask<M,T> extends LXAsyncCallback<T> {



	/******************
	 * 线程执行
	 * @param data    传入数据
	 * @param tag     tag
	 * @return
	 * @throws Exception  错误
     */
	 T run(M data,String tag) throws Exception;


}
