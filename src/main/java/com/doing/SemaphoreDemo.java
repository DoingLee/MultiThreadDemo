package com.doing;

import java.util.concurrent.Semaphore;

/**
 * @author：ldy on 26/01/2018 10:36
 */
public class SemaphoreDemo {
    static Semaphore semaphore = new Semaphore(3);

    public static void testSemaphore() {
        for (int i = 0; i < 12; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire(); //获取许可证
                        System.out.println("线程" + num + "开始工作");
                        Thread.sleep(500); //模拟工作
                        semaphore.release(); //释放许可证
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
