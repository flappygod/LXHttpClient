package com.flappygod.lipo.lxhttpclient.Asynctask;

import android.os.Message;

public class LXAsyncTaskThread extends Thread {

	// 用于回调
	private LXAsyncTaskHandler handler;
	// 任务
	private LXAsyncTask task;
	// 线程tag
	private String   tag;
	//传入的数据
	private Object   inObject;

	/************
	 * @param tag
	 *            线程tag
	 * @param task
	 *            任务
	 */
	public LXAsyncTaskThread(Object inObject,String tag, LXAsyncTask task) {
		super();
		this.handler = new LXAsyncTaskHandler(tag, task);
		this.task = task;
		this.tag=tag;
		this.inObject=inObject;
	}

	public LXAsyncTaskHandler getHandler() {
		return handler;
	}

	public void setHandler(LXAsyncTaskHandler handler) {
		this.handler = handler;
	}

	public LXAsyncTask getTask() {
		return task;
	}

	public void setTask(LXAsyncTask task) {
		this.task = task;
	}

	/****************
	 * 设置线程执行完成后回调是否响应
	 * 
	 * @param callBackEnable
	 *            是否响应
	 */
	public void setCallBackEnable(boolean callBackEnable) {
		handler.setCallBackEnable(callBackEnable);
	}

	/**********
	 * 取消正在执行的任务
	 * 
	 * @param task
	 * @return
	 */
	public boolean cancleTask(LXAsyncTask task) {
		if (handler.getTask() == task) {
			handler.setCallBackEnable(false);
			return true;
		}
		return false;
	}

	public void run() {
		try {
			Object object = task.run(inObject,tag);
			// 发送执行成功的消息
			Message msg = handler.obtainMessage(LXAsyncTaskHandler.SUCCESS_MSG,object);
			//发送消息
			handler.sendMessage(msg);
		} catch (Exception e) {
			// 发送执行错误的消息
			Message msg = handler.obtainMessage(LXAsyncTaskHandler.FAILURE_MSG,e);
			//错误
			handler.sendMessage(msg);
		}
	}

}
