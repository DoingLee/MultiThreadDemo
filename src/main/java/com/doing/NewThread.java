package com.doing;

public class NewThread {

    public void test() {
        //方法1：使用Runnable接口（推荐！）
        Thread thread1 = new Thread(new MyRunnable("第一个"), "thread1");

        //方法2：直接使用Thread：直接覆写run()
        Thread thread2 = new Thread("thread2"){
            @Override
            public void run() {
                // super.run(); //底层其实也是调用Runnable（不为null的话）的run()
                System.out.println("线程名称：" + getName());
            }
        };

        //执行线程:start的底层是native函数调用操作系统的线程接口启动线程，启动的线程会调用该Thread对象的run()
        thread1.start();
        thread2.start();

    }

    public class MyRunnable implements Runnable {
        private String tag;

        //可以通过构造函数传递一些引用/信息进来
        public MyRunnable(String tag) {
            this.tag = tag;
        }

        //Runnable的run()会被Thread的run()执行
        public void run() {
            System.out.println(tag + " ====== 执行！");

            //可以通过Thread提供的静态工具方法获得Runnable所在线程：Thread.currentThread()
            System.out.println("线程名称：" + Thread.currentThread().getName());
        }
    }


}
