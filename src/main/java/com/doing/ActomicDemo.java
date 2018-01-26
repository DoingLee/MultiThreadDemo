package com.doing;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author：ldy on 20/01/2018 19:11
 */
public class ActomicDemo {

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void testAtomic() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.getAndIncrement();
                    System.out.println(atomicInteger.get());
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("最后结果：" + atomicInteger.get());
    }
}
