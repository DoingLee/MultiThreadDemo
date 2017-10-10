package com.doing;

import com.sun.tools.jdi.InternalEventHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    /********************************* 线程安全例子 ****************************/

    private ConcurrentCache concurrentCache = new ConcurrentCache();

    public void testThreadSafe() {

        for (int i = 0; i < 2; i++) {
            final int num = i;
            //加读锁线程
            Thread putThread = new Thread(new Runnable() {
                public void run() {
                    System.out.println("放入鸡蛋：" + num);
                    concurrentCache.put("num" + num, "鸡蛋" + num);
                }
            });
            putThread.start();

            //加写锁线程
            for (int j = 0; j < 5; j++) {
                final int num2 = j;
                Thread getThread = new Thread(new Runnable() {
                    public void run() {
                        System.out.println("第" + num2 + "次获取鸡蛋" + num + "：" + concurrentCache.get("num" + num));
                    }
                });
                getThread.start();
            }
        }

    }

    public class ConcurrentCache {
        private HashMap<String, Object> map = new HashMap<String, Object>();

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false); //默认为false - 非公平锁
        private Lock r = readWriteLock.readLock();
        private Lock w = readWriteLock.writeLock();

        public Object get(String key) {
            Object o;

            r.lock();
            try {
                o = map.get(key);

                System.out.println("获取数据时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                //模拟长时间占有读锁
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } finally {
                r.unlock();
            }

            return o;
        }

        public void put(String key, Object value) {
            w.lock();
            try {
                map.put(key, value);

                System.out.println("写入数据时间(秒)：" + Calendar.getInstance().get(Calendar.SECOND));
                //模拟长时间占有写锁
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } finally {
                w.unlock();
            }
        }

        public void clear() {
            w.lock();
            try {
                map.clear();
            } finally {
                w.unlock();
            }
        }

    }

    /********************************* 线程不安全例子 ****************************/

    private Cache cache = new Cache();

    public void testThreadUnSafe() {
        for (int i = 0 ; i < 10; i++) {
            final int num = i;
            Thread putThread = new Thread(new Runnable() {
                public void run() {
                    System.out.println("放入鸡蛋：" + num);
                    cache.put("num" + num, "鸡蛋" + num);
                }
            });
            Thread getThread = new Thread(new Runnable() {
                public void run() {
                    System.out.println("获取鸡蛋：" + cache.get("num" + num));
                }
            });
            putThread.start();
            getThread.start();
        }
    }


    public class Cache {
        private HashMap<String, Object> map = new HashMap<String, Object>();

        public Object get(String key) {
            return map.get(key);
        }

        public void put(String key, Object value) {
            map.put(key, value);
            return;
        }

        public void clear() {
            map.clear();
            return;
        }
    }
}
