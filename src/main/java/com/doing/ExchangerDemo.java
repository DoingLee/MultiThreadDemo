package com.doing;

import java.util.concurrent.Exchanger;

/**
 * @author：ldy on 26/01/2018 10:58
 */
public class ExchangerDemo {

    static Exchanger<String> exchanger = new Exchanger<>();

    /**
     * 输出：
     * thread1交换到的信息是： thread2的信息
     * thread2交换到的信息是： thread1的信息
     */
    public static void testExchanger() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String a = "thread1的信息";
                try {
                    String b = exchanger.exchange(a); //阻塞在同步点等待对方
                    System.out.println("thread1交换到的信息是： " + b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String b = "thread2的信息";
                try {
                    Thread.sleep(500); //模拟工作，延迟到达同步点
                    String a = exchanger.exchange(b);
                    System.out.println("thread2交换到的信息是： " + a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
