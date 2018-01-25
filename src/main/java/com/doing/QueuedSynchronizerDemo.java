package com.doing;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author：ldy on 24/01/2018 16:20
 *
 * 自定义同步器﻿AbstractQueuedSynchronizer
 */
public class QueuedSynchronizerDemo {

    /**
     * 输出：
     thread1 获取锁成功！
     thread2 获取锁成功！
     thread1 运行1000毫秒结束
     thread1 释放锁成功！
     thread3 获取锁成功！
     thread3 运行500毫秒结束
     thread3 释放锁成功！
     thread4 获取锁成功！
     thread2 运行2000毫秒结束
     thread2 释放锁成功！
     thread4 运行1000毫秒结束
     thread4 释放锁成功！
     */
    public static void testTwinsLock() {
        final TwinsLock twinsLock = new TwinsLock();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 运行1000毫秒结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                twinsLock.unlock();
            }
        },"thread1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " 运行2000毫秒结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                twinsLock.unlock();
            }
        },"thread2");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + " 运行500毫秒结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                twinsLock.unlock();
            }
        },"thread3");
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 运行1000毫秒结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                twinsLock.unlock();
            }
        },"thread4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static class TwinsLock implements Lock {

        private final Sync sync = new Sync(2);

        @Override
        public void lock() {
            sync.acquireShared(1); //尝试获取锁，获取不到会阻塞（内部通过死循环不断尝试获取锁）
            System.out.println(Thread.currentThread().getName() + " 获取锁成功！");
        }

        @Override
        public void unlock() {
            sync.releaseShared(1);
            System.out.println(Thread.currentThread().getName() + " 释放锁成功！");
        }

        //其他接口方法略

        @Override
        public void lockInterruptibly() throws InterruptedException {
            //todo
        }

        @Override
        public boolean tryLock() {
            //todo
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            //todo
            return false;
        }

        @Override
        public Condition newCondition() {
            //todo
            return null;
        }

        //自定义同步器，静态内部类
        private static final class Sync extends AbstractQueuedSynchronizer {
            public Sync(int count) {
                if (count <= 0) {
                    throw new IllegalArgumentException("count must large than zero.");
                }
                setState(count);
            }

            /**
             * 供AbstractQueuedSynchronizer的acquireShared调用
             *
             * @param reduceCount
             * @return
             * 大于0：AbstractQueuedSynchronizer获取共享锁成功；
             * 小于0：AbstractQueuedSynchronizer会在死循环中调用本方法不断尝试获取共享锁
             */
            @Override
            public int tryAcquireShared(int reduceCount) {
                for (; ; ) {
                    int current = getState();
                    int newCount = current - reduceCount;
                    if (newCount < 0 || compareAndSetState(current, newCount)) {
                        return newCount;
                    }
                }
            }

            /**
             * 供AbstractQueuedSynchronizer的releaseShared调用
             *
             * @param returnCount
             * @return
             */
            @Override
            public boolean tryReleaseShared(int returnCount) {
                for (; ; ) {
                    int current = getState();
                    int newCount = current + returnCount;
                    if (compareAndSetState(current, newCount)) {
                        return true;
                    }
                }
            }
        }
    }
}
