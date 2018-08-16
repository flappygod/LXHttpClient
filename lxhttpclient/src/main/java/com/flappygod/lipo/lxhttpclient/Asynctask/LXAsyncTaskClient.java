package com.flappygod.lipo.lxhttpclient.Asynctask;

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

    /*******************
     * 执行某个异步线程
     *
     * @param task 异步任务
     * @param tag  线程tag
     */
    public void excute(LXAsyncTask task, String tag) {
        excute(task, null, tag);
    }

    /*******************
     * 执行某个异步线程
     *
     * @param task     异步任务
     * @param inObject 传入的参数
     * @param tag      线程tag
     */
    public void excute(LXAsyncTask task, Object inObject, String tag) {
        /*********
         * 创建一个线程
         */
        LXAsyncTaskThread thread = new LXAsyncTaskThread(inObject, tag, task);
        // 执行线程
        threadpool.execute(thread);
    }


    /*********
     * 执行某个异步任务
     *
     * @param task 异步任务
     */
    public void excute(LXAsyncTask task) {
        excute(task, null);
    }

    /**************************
     * 禁止当前的所有线程执行回调操作
     */
    public void cancleAllTask() {
        List<Thread> threads = threadpool.getAllThread();
        for (int s = threads.size() - 1; s >= 0; s--) {
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                // 判断这个线程是不是正要或者即将对这个image进行操作
                ((LXAsyncTaskThread) thread).setCallBackEnable(false);
                // 从当前的任务中移除这个线程
                threadpool.remove(thread);
            }
        }
    }

    /***********************
     * 撤销某个任务
     *
     * @param task 某个任务
     */
    public void cancleTask(LXAsyncTask task) {
        List<Thread> threads = threadpool.getAllThread();
        for (int s = threads.size() - 1; s >= 0; s--) {
            Thread thread = threads.get(s);
            // 下载的线程
            if (thread instanceof LXAsyncTaskThread) {
                // 判断是否移除成功
                boolean isRemoved = ((LXAsyncTaskThread) thread)
                        .cancleTask(task);
                if (isRemoved) {
                    // 从当前的线程池中移除这个任务
                    threadpool.remove(thread);
                    break;
                }
            }
        }
    }

    /*********************
     * 获取当前正在进行的任务
     *
     * @return
     */
    public List<LXAsyncTask> getAllTask() {
        List<LXAsyncTask> tasks = new ArrayList<LXAsyncTask>();
        List<Thread> threads = threadpool.getAllThread();
        for (int s = 0; s < threads.size(); s++) {
            if (threads.get(s) instanceof LXAsyncTaskThread) {
                LXAsyncTaskThread men = (LXAsyncTaskThread) threads.get(s);
                tasks.add(men.getTask());
            }
        }
        return tasks;
    }

}
