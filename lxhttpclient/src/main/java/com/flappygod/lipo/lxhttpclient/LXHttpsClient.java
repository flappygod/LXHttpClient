package com.flappygod.lipo.lxhttpclient;

import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

import android.content.Context;

import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncCallback;
import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncTask;
import com.flappygod.lipo.lxhttpclient.Asynctask.LXAsyncTaskClient;
import com.flappygod.lipo.lxhttpclient.Holder.LXHttpHeaderHolder;
import com.flappygod.lipo.lxhttpclient.HttpTask.Base.LXHttpsBaseTask;
import com.flappygod.lipo.lxhttpclient.HttpTask.LXHttpsTask;

public class LXHttpsClient {
    // 静态单例模式
    private static LXHttpsClient instance;
    // 异步线程客户端
    private LXAsyncTaskClient taskClient;
    // 线程池的默认大小
    public final static int DEFAULT_POOL_SIZE = 3;
    // 主机名称验证
    private HostnameVerifier mHostnameVerifier;
    // 服务器信任manager
    private TrustManager[] trustManagers;
    // 证书
    private KeyManager[] keyManagers;
    // 上下文
    private Context mcontext;


    private boolean hadInitHttps = false;
    //服务器
    // bks地址
    private int serverResource;
    // 密码
    private String serverPassword;


    //客户端双向验证
    // bks地址
    private int clientResource;
    // 密码
    private String clientPassword;


    // 是否开启cookie
    private boolean enableCookie = false;

    // cookie
    private LXHttpHeaderHolder cookieHolder;

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

    /**************
     * 设置keyStore
     *
     * @param mcontext       上下文
     * @param serverResource 资源文件
     * @param serverPassword 密码
     */
    public void setKeyStore(Context mcontext, int serverResource, String serverPassword) {
        this.mcontext = mcontext;
        this.serverResource = serverResource;
        this.serverPassword = serverPassword;
    }


    /**************
     * 设置keyStore
     *
     * @param mcontext       上下文
     * @param serverResource 资源文件
     * @param serverPassword 密码
     * @param clientResource 资源文件
     * @param clientPassword 密码
     */
    public void setKeyStore(Context mcontext, int serverResource, String serverPassword, int clientResource, String clientPassword) {
        this.mcontext = mcontext;
        this.serverResource = serverResource;
        this.serverPassword = serverPassword;
        this.clientResource = clientResource;
        this.clientPassword = clientPassword;
    }


    public HostnameVerifier getmHostnameVerifier() {
        return mHostnameVerifier;
    }

    public void setmHostnameVerifier(HostnameVerifier mHostnameVerifier) {
        this.mHostnameVerifier = mHostnameVerifier;
    }

    public TrustManager[] getTrustManagers() {
        return trustManagers;
    }

    public void setTrustManagers(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

    public KeyManager[] getKeyManagers() {
        return keyManagers;
    }

    public void setKeyManagers(KeyManager[] keyManagers) {
        this.keyManagers = keyManagers;
    }

    /*******************
     * @param poolSize 线程池大小
     */
    public LXHttpsClient(int poolSize) {
        taskClient = new LXAsyncTaskClient(DEFAULT_POOL_SIZE);
    }

    /**************
     * 多线程单例模式
     *
     * @return 单例
     */
    public static LXHttpsClient getInstacne() {
        if (instance == null) {
            synchronized (LXHttpsClient.class) {
                if (instance == null) {
                    instance = new LXHttpsClient(DEFAULT_POOL_SIZE);
                }
            }
        }
        return instance;
    }

    /************
     * 执行get请求
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param callback 请求回调
     */
    public void get(String url,
                    HashMap<String, Object> param,
                    LXAsyncCallback<String> callback) {
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
                    LXAsyncCallback<String> callback,
                    String tag) {
        LXHttpsTask task = new LXHttpsTask(url, param);
        get(task, callback, tag);
    }

    /*********
     * 执行get请求
     *
     * @param task     任务
     * @param callback 回调
     */
    public void get(LXHttpsTask task,
                    LXAsyncCallback<String> callback) {
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
        LXHttpsTask task = new LXHttpsTask(url, param);
        post(task, callback, tag);
    }

    /*************
     * 执行post请求
     *
     * @param task     任务
     * @param callback 回调
     */
    public void post(LXHttpsTask task,
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
    public void postParam(String url,
                          HashMap<String, Object> param,
                          LXAsyncCallback<String> callback) {
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
    public void postParam(String url,
                          HashMap<String, Object> param,
                          LXAsyncCallback<String> callback,
                          String tag) {
        LXHttpsTask task = new LXHttpsTask(url, param);
        postParam(task, callback, tag);
    }


    public <T> void postParam(LXHttpsBaseTask<T> task,
                              LXAsyncCallback<T> callback) {
        postParam(task, callback, null);
    }

    /*************
     * get数据
     *
     * @param task     任务
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void get(LXHttpsBaseTask<T> task,
                        LXAsyncCallback<T> callback,
                        String tag) {
        taskClient.excute(new LXHttpAsyncTask<LXHttpsBaseTask<T>,T>(callback) {
            @Override
            public T run(LXHttpsBaseTask<T> data, String tag) throws Exception {
                LXHttpsBaseTask<T> task = (LXHttpsBaseTask<T>) data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                //假如没有设置https的setDefaultSSLSocketFactory
                if (hadInitHttps == false) {
                    hadInitHttps = true;
                    //解决一个BUG
                    System.setProperty("https.protocols", "TLSv1.1,TLSv1.2");
                    //假如是双向验证
                    if (mcontext != null && serverPassword != null && clientPassword != null) {
                        //假如有账号密码
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword,
                                clientResource,
                                clientPassword);
                    }
                    //假如是单向验证
                    else if (mcontext != null && serverPassword != null) {
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword);
                    }
                    //假如设置了trustManagers
                    else if (trustManagers != null || keyManagers != null) {
                        task.setTrustManager(keyManagers, trustManagers);
                    }
                    //什么都没有就直接默认了
                    else {
                        task.setTrustManagerDefault();
                    }
                }
                if (mHostnameVerifier != null) {
                    task.setmHostnameVerifier(mHostnameVerifier);
                }

                T ret = task.get();
                if (enableCookie) {
                    cookieHolder = task.getHeaderHolder();
                }
                return ret;
            }
        }, task, tag);
    }


    /***********
     * post数据
     *
     * @param task     任务
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void post(LXHttpsBaseTask<T> task,
                         LXAsyncCallback<T> callback,
                         String tag) {
        taskClient.excute(new LXHttpAsyncTask<LXHttpsBaseTask<T>,T>(callback) {
            @Override
            public T run(LXHttpsBaseTask<T> data, String tag) throws Exception {
                LXHttpsBaseTask<T> task = (LXHttpsBaseTask<T>) data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                //假如没有设置https的setDefaultSSLSocketFactory
                if (hadInitHttps == false) {
                    hadInitHttps = true;
                    //假如是双向验证
                    if (mcontext != null && serverPassword != null && clientPassword != null) {
                        //假如有账号密码
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword,
                                clientResource,
                                clientPassword);
                    }
                    //假如是单向验证
                    else if (mcontext != null && serverPassword != null) {
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword);
                    }
                    //假如设置了trustManagers
                    else if (trustManagers != null || keyManagers != null) {
                        task.setTrustManager(keyManagers, trustManagers);
                    }
                    //什么都没有就直接默认了
                    else {
                        task.setTrustManagerDefault();
                    }
                }
                if (mHostnameVerifier != null) {
                    task.setmHostnameVerifier(mHostnameVerifier);
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
     * post键值对数据
     *
     * @param task     任务
     * @param callback 回调
     * @param tag      线程tag
     */
    public <T> void postParam(LXHttpsBaseTask<T> task,
                               LXAsyncCallback<T> callback,
                               String tag) {
        taskClient.excute(new LXHttpAsyncTask<LXHttpsBaseTask<T>,T>(callback) {
            @Override
            public T run(LXHttpsBaseTask<T> data, String tag) throws Exception {
                //传入task
                LXHttpsBaseTask<T> task = (LXHttpsBaseTask<T>) data;
                if (enableCookie && cookieHolder != null) {
                    task.setHeaderHolder(cookieHolder);
                }
                //假如没有设置https的setDefaultSSLSocketFactory
                if (hadInitHttps == false) {
                    hadInitHttps = true;
                    //假如是双向验证
                    if (mcontext != null && serverPassword != null && clientPassword != null) {
                        //假如有账号密码
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword,
                                clientResource,
                                clientPassword);
                    }
                    //假如是单向验证
                    else if (mcontext != null && serverPassword != null) {
                        task.initKeyStore(mcontext,
                                serverResource,
                                serverPassword);
                    }
                    //假如设置了trustManagers
                    else if (trustManagers != null || keyManagers != null) {
                        task.setTrustManager(keyManagers, trustManagers);
                    }
                    //什么都没有就直接默认了
                    else {
                        task.setTrustManagerDefault();
                    }
                }
                if (mHostnameVerifier != null) {
                    task.setmHostnameVerifier(mHostnameVerifier);
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
