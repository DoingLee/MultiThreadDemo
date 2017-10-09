package com.doing;

public class ThreadJoinDemo {

    public void test() throws InterruptedException {
        Thread childThread = new Thread(new Child(), "childThread");
        childThread.start();

        childThread.join();
        System.out.println("父线程后执行");
    }

    public class Child implements Runnable {
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("子线程优先执行");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
