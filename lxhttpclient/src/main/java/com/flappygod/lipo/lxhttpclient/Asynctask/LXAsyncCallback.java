package com.flappygod.lipo.lxhttpclient.Asynctask;

public interface LXAsyncCallback {

	/*************
	 * 执行异常
	 * 
	 * @param tag
	 *            线程tag
	 * @param e
	 *            错误
	 */
	void failure(Exception e, String tag);

	/****************
	 * 成功
	 * 
	 * @param tag
	 *            线程tag
	 * @param data
	 *            线程需要传递的数据
	 */
	void success(Object data, String tag);

}
