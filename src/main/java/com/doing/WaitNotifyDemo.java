package com.doing;

public class WaitNotifyDemo {

    volatile boolean flag = true;
    Object lock = new Object();

    public void test() throws InterruptedException {
        Thread thread1 = new Thread(new First(), "FirstThread");
        Thread thread2 = new Thread(new Second(), "SecondThread");
        Thread thread3 = new Thread(new Third(), "ThirdThread");

        thread1.start();
        Thread.sleep(500); //等待前面的线程先获取锁，以保证获取锁的顺序
        thread2.start();
        Thread.sleep(500); //等待前面的线程先获取锁，以保证获取锁的顺序
        thread3.start();
    }

    public class First implements Runnable {
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println("FirstThread 获得对象锁后，释放对象锁，进入等待队列");
                        lock.wait();
                        System.out.println("FirstThread 获得对象锁");
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("FirstThread 释放对象锁，进行其他不需要对象锁的工作");
        }
    }


    public class Second implements Runnable {
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println("SecondThread 获得对象锁后，释放对象锁，进入等待队列");
                        lock.wait();
                        System.out.println("SecondThread 获得对象锁");
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("SecondThread 释放对象锁，进行其他不需要对象锁的工作");
        }
    }

    public class Third implements Runnable {
        public void run() {
            synchronized (lock) {
                System.out.println("ThirdThread 获得对象锁");
                flag = false;
                lock.notifyAll();
//                lock.notify();
                System.out.println("ThirdThread notify使得对象锁等待队列的线程出队列，进入锁阻塞等待队列");
                try {
                    System.out.println("ThirdThread 继续占有对象锁，进行工作。。。");
                    Thread.sleep(1000); //模拟当前线程长时间占有对象锁，被notify的线程在对象锁阻塞队列中需等待该线程结束后才能获得对象锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThirdThread 释放对象锁，进行其他不需要对象锁的工作");
        }
    }

}
