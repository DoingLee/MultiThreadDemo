package com.doing;

import java.util.Calendar;
import java.util.concurrent.*;

public class ThreadPoolDemo {

    /**
     * corePoolSize固定，等待队列为无界队列LinkedBlockingQueue：
     * 线程池中当忙碌线程数量大于corePoolSize，之后的任务进入等待队列
     */
    public void fixedThreadPoolTest() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        //前5个线程同时执行1秒完毕，后五个线程同时开始
        for (int i = 0; i < 10; i++) {
            final int num = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("线程" + num + "在执行");
                    System.out.println("当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * corePoolSize=1, 等待队列为无界队列LinkedBlockingQueue：
     * 线程池中只有1个线程，之后的任务进入等待队列
     */
    public void singleThreadPoolTest() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        //5个线程串行执行
        for (int i = 0; i < 5; i++) {
            final int num = i;
            singleThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("线程" + num + "在执行");
                    System.out.println("当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * corePoolSize=0, maximumPoolSize=Integer.MAX_VALUE, 等待队列为仅有一个元素的阻塞队列SynchronousQueue：
     * 线程池不断从SynchronousQueue获取任务执行（线程池中的线程数根据任务数动态调整，至多可以有Integer.MAX_VALUE个线程同时执行）
     */
    public void cacheThreadPoolTest() {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        //10个线程同时执行
        for (int i = 0; i < 10; i++) {
            final int num = i;
            cacheThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("线程" + num + "在执行");
                    System.out.println("当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * corePoolSize固定，等待队列为无界队列DelayQueue：
     * 线程池中的线程从DelayQueue获取已经到期的ScheduledFutureTask执行
     */
    public void scheduledThreadPoolTest() {
        System.out.println("当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        //五个线程同时1秒后执行
        for (int i = 0 ; i < 5; i ++) {
            scheduledThreadPool.schedule(new Runnable() {
                public void run() {
                    System.out.println("延迟1秒执行 - 当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                }
            }, 1, TimeUnit.SECONDS);
        }

        //每间隔两秒执行一次任务
//        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                System.out.println("每隔两秒执行一次任务 - 当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
//                try {
//                    //Thread.sleep(3000); //注意：若线程执行时间(3秒) > 设置的间隔时间(2秒)，则以线程执行完成为准顺序执行
//                    Thread.sleep(500); // 若线程执行时间(0.5秒) < 设置的间隔时间(2秒)，则按时间间隔定时执行
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 2, TimeUnit.SECONDS);

        //任务之间每间隔两秒执行一次（前一次任务执行结束后间隔2秒再执行下一次任务）
//        scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
//            public void run() {
//                System.out.println("两次任务之间隔2秒执行 - 线程开始 - 当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("两次任务之间隔2秒执行 - 线程结束 - 当前时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
//            }
//        }, 0, 2, TimeUnit.SECONDS);
    }

}
