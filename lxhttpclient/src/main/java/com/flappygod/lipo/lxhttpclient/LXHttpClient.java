package com.flappygod.lipo.lxhttpclient;

import java.util.HashMap;
import java.util.List;

import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncCallback;
import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncTask;
import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncTaskClient;
import com.flappygod.lipo.lxhttpclient.Holder.LXHttpHeaderHolder;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpBaseTask;
import com.flappygod.lipo.lxhttpclient.HttpTask.LXHttpTask;
import com.flappygod.lipo.lxhttpclient.HttpTask.LXHttpsModelTask;

public class LXHttpClient {

    // 静态单例模式
    private static LXHttpClient instance;
    // 异步线程客户端
    private LXAsyncTaskClient taskClient;
    // 线程池的默认大小
    public final static int DEFAULT_POOL_SIZE = 3;
    // 是否开启cookie
    private boolean enableCookie = false;
    // cookie
    private LXHttpHeaderHolder cookieHolder;

    /*******************
     * @param poolSize 线程池大小
     */
    public LXHttpClient(int poolSize) {
        taskClient = new LXAsyncTaskClient(DEFAULT_POOL_SIZE);
    }

    /**************
     * 多线程单例模式
     *
     * @return 单例
     */
    public static LXHttpClient getInstacne() {
        if (instance == null) {
            synchronized (LXHttpClient.class) {
                if (instance == null) {
                    instance = new LXHttpClient(DEFAULT_POOL_SIZE);
                }
            }
        }
        return instance;
    }

    public boolean isEnableCookie() {
        return enableCookie;
    }

    /**********
     * 开启cookie
     *
     * @param enableCookie
     */
    public void setEnableCookie(boolean enableCookie) {
        this.enableCookie = enableCookie;
        if (cookieHolder == null) {
            cookieHolder = new LXHttpHeaderHolder(null);
        }
    }

    /***********
     * 获取当前的cookieholder
     *
     * @return
     */
    public LXHttpHeaderHolder getCookieHolder() {
        return cookieHolder;
    }

    /************
     * 设置cookieHolder
     *
     * @param holder
     */
    public void setCookieHolder(LXHttpHeaderHolder holder) {
        this.cookieHolder = holder;
    }

    /************
     * 执行get请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     */
    public void get(final String url,
                    final HashMap<String, Object> param,
                    final LXAsyncCallback<String> callback) {
        get(url, param, callback, null);
    }

    /************
     * 执行get请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     * @param tag      线程tag
     */
    public void get(String url,
                    HashMap<String, Object> param,
                    final LXAsyncCallback<String> callback,
                    String tag) {
        LXHttpTask task = new LXHttpTask(url, param);
        get(task, callback, tag);
    }

    /*************
     * 执行get请求
     *
     * @param task     任务
     * @param callback 回调
     */
    public void get(LXHttpTask task,
                    final LXAsyncCallback<String> callback) {
        get(task, callback, null);
    }


    /************
     * 执行post请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     */
    public void post(String url,
                     HashMap<String, Object> param,
                     LXAsyncCallback<String> callback) {
        post(url, param, callback, null);
    }


    /************
     * 执行post请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     * @param tag      线程tag
     */
    public void post(String url,
                     HashMap<String, Object> param,
                     LXAsyncCallback<String> callback,
                     String tag) {
        LXHttpTask task = new LXHttpTask(url, param);
        post(task, callback, tag);
    }

    /*************
     * 执行post请求
     *
     * @param task     任务
     * @param callback 回调
     */
    public void post(LXHttpTask task,
                     LXAsyncCallback<String> callback) {
        post(task, callback, null);
    }


    /************************
     * 执行post请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     */
    public void postParam(final String url,
                          final HashMap<String, Object> param,
                          final LXAsyncCallback<String> callback) {
        postParam(url, param, callback, null);
    }


    /************************
     * 执行post请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     * @param tag      线程tag
     */
    public void postParam(final String url,
                          final HashMap<String, Object> param,
                          final LXAsyncCallback<String> callback,
                          String tag) {
        //传入数据
        LXHttpTask task = new LXHttpTask(url, param);
        //线程池执行
        postParam(task, callback, tag);
    }


    public <T> void postParam(LXHttpBaseTask<T> task,
                          final LXAsyncCallback<T> callback) {
        //线程池执行
        postParam(task, callback, null);
    }


    /*********************
     * 执行get请求
     *
     * @param intask   task
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void get(LXHttpBaseTask<T> intask,
                          LXAsyncCallback<T> callback,
                      String tag) {
        taskClient.excute(new LXHttpAsyncTask(callback) {
            @Override
            public Object run(Object data, String tag) throws Exception {
                LXHttpBaseTask<T> task = (LXHttpBaseTask<T>) data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                T ret = task.get();
                if (enableCookie) {
                    cookieHolder = task.getHeaderHolder();
                }
                return ret;
            }
        }, intask, tag);
    }

    /**************
     * 执行post请求
     *
     * @param task     任务
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void post(LXHttpBaseTask<T> task,
                     LXAsyncCallback<T>  callback,
                     String tag) {
        taskClient.excute(new LXHttpAsyncTask<LXHttpBaseTask<T>,T>(callback) {
            @Override
            public T run(LXHttpBaseTask<T> data, String tag) throws Exception {
                LXHttpBaseTask<T> task = data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                T ret = task.postAsJson();
                if (enableCookie) {
                    cookieHolder = task.getHeaderHolder();
                }
                return ret;
            }
        }, task, tag);
    }

    /***********
     * 执行post请求
     *
     * @param task     任务
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void postParam(LXHttpBaseTask<T> task,
                          final LXAsyncCallback<T> callback,
                          String tag) {
        //线程池执行
        taskClient.excute(new LXHttpAsyncTask<LXHttpBaseTask<T>,T>(callback) {
            @Override
            public T run(LXHttpBaseTask<T> data, String tag) throws Exception {
                LXHttpBaseTask<T> task =  data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                T ret = task.postAsParam();
                if (enableCookie) {
                    cookieHolder = task.getHeaderHolder();
                }
                return ret;
            }
        }, task, tag);
    }

    /**************
     * 取消所有的请求
     */
    public void removeAllRequest() {
        taskClient.cancleAllTask();
    }

    /**************
     * 移除回调
     *
     * @param callback 回调
     */
    public void removeCallBack(LXAsyncCallback callback) {
        // 获取所有的任务
        List<LXAsyncTask> tasks = taskClient.getAllTask();
        // 遍历
        for (int s = 0; s < tasks.size(); s++) {
            // 如果任务属于LXHttpAsyncTask
            if (tasks.get(s) instanceof LXHttpAsyncTask) {
                // 装换
                LXHttpAsyncTask mem = (LXHttpAsyncTask) tasks.get(s);
                // 如果当前任务执行的是这个callback
                if (mem.getCallback() == callback) {
                    // 就取消当前的这个任务
                    taskClient.cancleTask(mem);
                }
            }
        }
    }

}
