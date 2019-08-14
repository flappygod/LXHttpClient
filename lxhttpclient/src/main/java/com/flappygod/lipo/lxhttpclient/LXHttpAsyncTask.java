package com.flappygo.lilin.lxhttpclient;

import com.flappygo.lilin.lxhttpclient.Asynctask.LXAsyncCallback;
import com.flappygo.lilin.lxhttpclient.Asynctask.LXAsyncTask;


/****************
 * 异步任务实现
 * @param <T>  输入
 * @param <M>  输出
 */
public abstract class LXHttpAsyncTask<T,M> implements LXAsyncTask<T,M> {
	// 回调
	private LXAsyncCallback<M> callback;

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
	public void success(M data, String tag) {
		if (callback != null) {
			callback.success(data, tag);
		}
	}
}
