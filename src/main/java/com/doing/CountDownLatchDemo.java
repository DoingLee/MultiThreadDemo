package com.doing;

import java.util.concurrent.CountDownLatch;

/**
 * @author：ldy on 25/01/2018 21:02
 */
public class CountDownLatchDemo {

    public static void testCountDownLatch() throws InterruptedException {

        //countDownLatch的count只能设置使用一次
        CountDownLatch countDownLatch = new CountDownLatch(4);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1开始countDown");
                countDownLatch.countDown();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2开始countDown");
                countDownLatch.countDown();
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程3开始countDown");
                countDownLatch.countDown();
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程4开始countDown");
                countDownLatch.countDown();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        //countDownLatch.await会阻塞当前线程，等待countDown到0才会从阻塞唤醒
        countDownLatch.await(); //这行去掉可以看出效果
        System.out.println("等待所有线程countDown完毕");
    }
}
