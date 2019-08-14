package com.flappygo.lilin.lxhttpclient.Asynctask;

import android.content.Context;
import android.os.Message;

import java.lang.ref.WeakReference;

public class LXAsyncTaskThread extends Thread {

    // 用于回调
    private LXAsyncTaskHandler handler;
    // 任务
    private LXAsyncTask task;
    // 线程所归属的上下文
    private WeakReference<Context> taskContext;
    // 线程tag
    private String taskTag;
    //传入的数据
    private Object taskInput;

    /************
     * @param tag
     *            线程tag
     * @param task
     *            任务
     */
    public LXAsyncTaskThread(Context taskContext, Object inObject, String tag, LXAsyncTask task) {
        super();
        this.handler = new LXAsyncTaskHandler(tag, task);
        this.taskContext = new WeakReference<Context>(taskContext);
        this.task = task;
        this.taskTag = tag;
        this.taskInput = inObject;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public Object getTaskInput() {
        return taskInput;
    }

    public Context getTaskContext() {
        if (taskContext != null) {
            return taskContext.get();
        }
        return null;
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
            Object object = task.run(taskInput, taskTag);
            // 发送执行成功的消息
            Message msg = handler.obtainMessage(LXAsyncTaskHandler.SUCCESS_MSG, object);
            //发送消息
            handler.sendMessage(msg);
        } catch (Exception e) {
            // 发送执行错误的消息
            Message msg = handler.obtainMessage(LXAsyncTaskHandler.FAILURE_MSG, e);
            //错误
            handler.sendMessage(msg);
        }
    }

}
