package com.doing;

public class StopThread {

    public void test() throws InterruptedException {

        //方法1：使用interrupt去告知停止线程
        //(1)停止一个正在sleep的线程
        Thread thread1 = new Thread(new SleepRunnable(), "sleepThread");
        thread1.start();
        Thread.sleep(1000);
        thread1.interrupt();
        //(2)停止一个正在wait的线程
        Thread thread2 = new Thread(new WaitRunnable(), "waitThread");
        thread2.start();
        Thread.sleep(1000);
        thread2.interrupt();
        //(3)停止一个正在运行的线程
        Thread thread3 = new Thread(new BusyRunnable(), "busyThread");
        thread3.start();
        Thread.sleep(1000);
        thread3.interrupt();

        //方法2：使用标志位去告知停止线程
        CancelableRunnable cancelRunnable = new CancelableRunnable();
        Thread thread4 = new Thread(cancelRunnable, "cancelableThread");
        thread4.start();
        Thread.sleep(1000);
        cancelRunnable.cancel();
    }

    public class SleepRunnable implements Runnable {
        private boolean on = true; //线程停止标志

        public void run() {
            while (on) {
                try {
                    System.out.println("SleepThread 正在执行");
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    System.out.println("SleepThread interrupt 状态：" + Thread.currentThread().isInterrupted());
                    System.out.println("SleepThread 被告知要停止（中断）了：" + "在这里做线程结束前的处理工作。。。");
                    on = false; //使得停止线程。若不设置on=false，则该线程还会继续执行
                    System.out.println("SleepThread 线程将结束");
                    System.out.println();
                }
            }
        }
    }

    private volatile Object lock = new Object();

    public class WaitRunnable implements Runnable {
        private boolean on = true; //线程停止标志

        public void run() {
            while (on) {
                try {
                    System.out.println("WaitRunnable 正在执行");
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("WaitRunnable interrupt 状态：" + Thread.currentThread().isInterrupted());
                    System.out.println("WaitRunnable 被告知要停止（中断）了：" + "在这里做线程结束前的处理工作。。。");
                    on = false; //使得停止线程。若不设置on=false，则该线程还会继续执行
                    System.out.println("WaitRunnable 线程将结束");
                    System.out.println();
                }
            }
        }
    }


    public class BusyRunnable implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
//                System.out.println("BusyRunnable 正在执行");
            }
            System.out.println("BusyRunnable interrupt 状态：" + Thread.currentThread().isInterrupted());
            System.out.println("BusyRunnable 被告知要停止（中断）了：" + "在这里做线程结束前的处理工作。。。");
            System.out.println("BusyRunnable 线程将结束");
            System.out.println();
        }
    }

    public class CancelableRunnable implements Runnable {

        private volatile boolean on = true; //线程停止标志(由于需要被多线程共用，需要声明为volatile)

        public void run() {
            while (on) {
//                System.out.println("BusyRunnable 正在执行");
            }
            System.out.println("CancelableRunnable 被告知要停止（中断）了：" + "在这里做线程结束前的处理工作。。。");
            System.out.println("CancelableRunnable 线程将结束");
            System.out.println();
        }

        public void cancel() {
            on = false;
        }
    }

}
