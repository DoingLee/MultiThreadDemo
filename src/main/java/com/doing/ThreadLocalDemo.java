package com.doing;

public class ThreadLocalDemo {

    MultiThreadObject multiThreadObject = new MultiThreadObject();

    public void test() {
        Thread thread1 = new Thread(new Runnable1(), "thread1");
        Thread thread2 = new Thread(new Runnable2(), "thread2");

        thread1.start();
        thread2.start();
    }

    public class MultiThreadObject{

        ThreadLocal<String> threadLocal = new ThreadLocal<String>();

        public String getLocalStr() {
            return threadLocal.get();
        }

        public void setLocalStr(String localStr) {
            threadLocal.set(localStr);
        }
    }

    public class Runnable1 implements Runnable {
        public void run() {
            multiThreadObject.setLocalStr("线程1 localStr");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程1读取localStr：" + multiThreadObject.getLocalStr());
        }
    }

    public class Runnable2 implements Runnable {
        public void run() {
            multiThreadObject.setLocalStr("线程2 localStr");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程2读取localStr：" + multiThreadObject.getLocalStr());
        }
    }

}
