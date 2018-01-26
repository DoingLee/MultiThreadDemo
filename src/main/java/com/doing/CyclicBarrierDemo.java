package com.doing;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author：ldy on 26/01/2018 10:10
 */
public class CyclicBarrierDemo {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            System.out.println("合并工作");
        }
    });

    /**
     * 输出：
     * 线程1 开始工作
     * 线程2 开始工作
     * 线程3 开始工作
     * 线程3 进入await
     * 线程2 进入await
     * 线程1 进入await
     * 合并工作
     * 线程1 从await唤醒
     * 线程3 从await唤醒
     * 线程2 从await唤醒
     */
    public static void testCyclicBarrier() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1 开始工作");
                    Thread.sleep(1000); //模拟工作
                    System.out.println("线程1 进入await");
                    cyclicBarrier.await();
                    System.out.println("线程1 从await唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2 开始工作");
                    Thread.sleep(600); //模拟工作
                    System.out.println("线程2 进入await");
                    cyclicBarrier.await();
                    System.out.println("线程2 从await唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程3 开始工作");
                    Thread.sleep(200); //模拟工作
                    System.out.println("线程3 进入await");
                    cyclicBarrier.await();
                    System.out.println("线程3 从await唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();


    }
}
