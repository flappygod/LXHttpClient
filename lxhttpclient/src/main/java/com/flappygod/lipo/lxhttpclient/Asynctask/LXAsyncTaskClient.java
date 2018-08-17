package com.flappygod.lipo.lxhttpclient.Asynctask;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.flappygod.lipo.lxhttpclient.Threadpool.ExcutePoolExecutor;

/***************
 * 异步线程执行
 *
 * @author lijunlin
 */
public class LXAsyncTaskClient {

    // 线程池
    private ExcutePoolExecutor threadpool;

    /************
     * 构造器创建的时候设置线程池的大小
     *
     * @param poolSize 线程池大小
     */
    public LXAsyncTaskClient(int poolSize) {
        // 创建一个大小为3的线程池
        this.threadpool = new ExcutePoolExecutor(poolSize);
    }

    /*********
     * 执行某个异步任务
     *
     * @param task 异步任务
     */
    public void excute(LXAsyncTask task) {
        excute(task, null);
    }

    /*******************
     * 执行某个异步线程
     *
     * @param task 异步任务
     * @param taskTag  线程taskTag
     */
    public void excute(LXAsyncTask task, String taskTag) {
        excute(task, null, taskTag);
    }

    /*******************
     * 执行某个异步线程
     *
     * @param task     异步任务
     * @param taskInput 传入的参数
     * @param taskTag      线程taskTag
     */
    public void excute(LXAsyncTask task, Object taskInput, String taskTag) {
        excute(task, taskInput, taskTag, null);
    }

    /*******************
     *
     * 执行某个异步线程
     *
     * 执行某个异步线程
     *
     * @param task       异步任务
     * @param taskInput  传入的参数
     * @param context   线程的归属
     * @param taskTag        线程taskTag
     */
    public void excute(LXAsyncTask task, Object taskInput, String taskTag, Context context) {
        /*********
         * 创建一个线程
         */
        LXAsyncTaskThread thread = new LXAsyncTaskThread(context, taskInput, taskTag, task);
        // 执行线程
        threadpool.execute(thread);
    }


    /**************************
     * 禁止当前的所有线程执行回调操作
     */
    public void cancleAllTask() {
        //线程池中取出所有的线程
        List<Thread> threads = threadpool.getAllThread();

        //反向
        for (int s = threads.size() - 1; s >= 0; s--) {
            //获取
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                // 不再执行回调了
                ((LXAsyncTaskThread) thread).setCallBackEnable(false);
                // 从当前的任务中移除这个线程
                threadpool.remove(thread);
            }
        }
    }


    /**************************
     * 通过线程的taskTag关闭线程
     * @param taskTag
     */
    public void cancleTaskBytaskTag(String taskTag) {
        //线程池中取出所有的线程
        List<Thread> threads = threadpool.getAllThread();
        //反向
        for (int s = threads.size() - 1; s >= 0; s--) {
            //获取
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                String men = ((LXAsyncTaskThread) thread).getTaskTag();
                if (men != null && men.equals(taskTag)) {
                    // 不再执行回调了
                    ((LXAsyncTaskThread) thread).setCallBackEnable(false);
                    // 从当前的任务中移除这个线程
                    threadpool.remove(thread);
                }
            }
        }
    }


    /********************
     * 通过上下文关闭
     * @param context 上下文，这里我们使用了hashCode
     */
    public void cancleTask(Context context) {
        //获取所有线程
        List<Thread> threads = threadpool.getAllThread();
        //进行遍历
        for (int s = threads.size() - 1; s >= 0; s--) {
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                Context men = ((LXAsyncTaskThread) thread).getTaskContext();
                if (men == context) {
                    // 不再执行回调了
                    ((LXAsyncTaskThread) thread).setCallBackEnable(false);
                    // 从当前的任务中移除这个线程
                    threadpool.remove(thread);
                }
            }
        }
    }


    /***********************
     * 撤销某个任务
     *
     * @param task 某个任务
     */
    public void cancleTask(LXAsyncTask task) {
        // 获取所有线程
        List<Thread> threads = threadpool.getAllThread();
        //进行遍历
        for (int s = threads.size() - 1; s >= 0; s--) {
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                // 判断是否移除成功
                boolean isRemoved = ((LXAsyncTaskThread) thread).cancleTask(task);
                if (isRemoved) {
                    // 从当前的线程池中移除这个任务
                    threadpool.remove(thread);
                    break;
                }
            }
        }
    }


}
