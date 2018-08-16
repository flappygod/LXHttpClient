package com.flappygod.lipo.lxhttpclient;

import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncCallback;
import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncTask;

public abstract class LXHttpAsyncTask implements LXAsyncTask {
	// 回调
	private LXAsyncCallback callback;

	// 构造器
	public LXHttpAsyncTask(LXAsyncCallback callback) {
		this.callback = callback;
	}

	/*********
	 * 获取当前任务的回调
	 * 
	 * @return
	 */
	public LXAsyncCallback getCallback() {
		return callback;
	}

	/*************
	 * 设置当前任务的回调
	 * 
	 * @param callback
	 *            回调
	 */
	public void setCallback(LXAsyncCallback callback) {
		this.callback = callback;
	}

	@Override
	public void failure(Exception e, String tag) {
		if (callback != null) {
			callback.failure(e, tag);
		}
	}

	@Override
	public void success(Object data, String tag) {
		if (callback != null) {
			callback.success(data, tag);
		}
	}
}
