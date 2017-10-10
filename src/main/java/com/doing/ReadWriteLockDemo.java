package com.doing;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    /********************************* 读写锁例子（写-读阻塞、写-写阻塞、读-写阻塞、读-读不阻塞） ****************************/

    private ConcurrentCache concurrentCache = new ConcurrentCache();

    public void testReadWriteLock() {

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

//    /********************************* 线程安全例子 ****************************/
//
//    private SafeCache safeCache = new SafeCache();
//
//    public void testSafeCache() {
//        for (int i = 0 ; i < 10; i++) {
//            final int num = i;
//            Thread putThread = new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("放入鸡蛋：" + num);
//                    safeCache.put("num" + num, "鸡蛋" + num);
//                }
//            });
//            Thread getThread = new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("获取鸡蛋：" + safeCache.get("num" + num));
//                }
//            });
//            putThread.start();
//            getThread.start();
//        }
//    }
//
//
//    public class SafeCache {
//        private HashMap<String, Object> map = new HashMap<String, Object>();
//
//        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false); //默认为false - 非公平锁
//        private Lock r = readWriteLock.readLock();
//        private Lock w = readWriteLock.writeLock();
//
//        public Object get(String key) {
//            Object o;
//
//            r.lock();
//            try {
//                o = map.get(key);
//            } finally {
//                r.unlock();
//            }
//
//            return o;
//        }
//
//        public void put(String key, Object value) {
//            w.lock();
//            try {
//                map.put(key, value);
//            } finally {
//                w.unlock();
//            }
//        }
//
//        public void clear() {
//            w.lock();
//            try {
//                map.clear();
//            } finally {
//                w.unlock();
//            }
//        }
//    }

//    /********************************* 线程不安全例子 ****************************/
//
//    private UnSafeCache unSafeCache = new UnSafeCache();
//
//    public void testUnSafeCache() {
//        for (int i = 0 ; i < 10; i++) {
//            final int num = i;
//            Thread putThread = new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("放入鸡蛋：" + num);
//                    unSafeCache.put("num" + num, "鸡蛋" + num);
//                }
//            });
//            Thread getThread = new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("获取鸡蛋：" + unSafeCache.get("num" + num));
//                }
//            });
//            putThread.start();
//            getThread.start();
//        }
//    }
//
//
//    public class UnSafeCache {
//        private HashMap<String, Object> map = new HashMap<String, Object>();
//
//        public Object get(String key) {
//            return map.get(key);
//        }
//
//        public void put(String key, Object value) {
//            map.put(key, value);
//            return;
//        }
//
//        public void clear() {
//            map.clear();
//            return;
//        }
//    }
}
