package com.flappygod.lipo.lxhttpclient.Asynctask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LXAsyncTaskHandler extends Handler {

	// 成功的消息
	public static int SUCCESS_MSG = 1;
	// 失败的消息
	public static int FAILURE_MSG = 0;

	// 任务
	private LXAsyncTask task;
	// 线程tag
	private String tag;
	// 是否执行回调
	private boolean callBackEnable = true;

	/*******
	 * 判断当前handler是否响应
	 * 
	 * @return
	 */
	public boolean isCallBackEnable() {
		return callBackEnable;
	}

	/****************
	 * 设置当前handler是否响应
	 * 
	 * @param callBackEnable
	 *            是否响应
	 */
	public void setCallBackEnable(boolean callBackEnable) {
		this.callBackEnable = callBackEnable;
	}

	/*********
	 * 构建handler
	 * 
	 * @param task
	 *            任务
	 */
	public LXAsyncTaskHandler(String tag, LXAsyncTask task) {
		super(Looper.getMainLooper());
		this.task = task;
		this.tag = tag;
	}

	/***********
	 * 获取当前的task
	 * 
	 * @return
	 */
	public LXAsyncTask getTask() {
		return task;
	}

	/*************
	 * 发送过来的消息
	 */
	public void handleMessage(Message message) {
		if (!callBackEnable) {
			return;
		}
		if (message.what == SUCCESS_MSG) {
			task.success(message.obj, tag);
		} else if (message.what == FAILURE_MSG) {
			task.failure((Exception) message.obj, tag);
		}
	}

}
