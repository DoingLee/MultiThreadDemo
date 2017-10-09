package com.doing;

import java.util.concurrent.*;

public class CallableFutureDemo {

//    ﻿Runnable接口：任务，run()方法无返回值
//    Callable接口：任务，call()方法有返回值
//    Future接口：Callable任务的异步计算返回结果
//    FutureTask：实现Runnable接口、Future接口，Runnable接口的run()方法实际上是调用了Callable()的call()方法。
//                由于实现了Runnable接口，因此可被Thread执行（run）；也可以被ExecutorService执行（execute、submmit）；
//                由于实现了Future接口，因此可以cancel()、isDone()、阻塞get()等

    /**
     * 在线程Thread中执行FutureTask，阻塞主线程地获取线程执行结果
     */
    public void runInThreadBlock() {

        //结合Thread使用
        //（1）创建任务
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            public String call() {
                System.out.println("线程任务1 正在执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("futureTask.cancel()会导致当前线程被中断interrput");
                    System.out.println("在这里做一些线程终止前的清理操作");
                    e.printStackTrace();
                }
                System.out.println("线程退出");
                return "线程任务1返回值";
            }
        });

        //（2）创建线程池
        Thread thread = new Thread(futureTask);
        //（3）执行线程
        thread.run();

        //（4）对异步计算结果futureTask操作：isDone()、cancel()、get()...
        //操作一：获取任务执行情况
//            while (!futureTask.isDone()) {
//                System.out.println("任务是否完成：" + futureTask.isDone());
//            }

        //操作二：取消线程任务：使用interrput()中断线程
        // 1) 如果线程尚未执行，则直接取消成功，返回true
        // 2) 若线程正在执行中，则使用interrput()中断线程，返回true （注意这里的终止线程方式与Thread interrupt()一样，参见StopThread.java）
        // 3) 如果线程任务2已经执行完毕，则取消失败，返回false
//        System.out.println("中断线程任务2：" + futureTask.cancel(true));
//        System.out.println("线程任务2 被中断结果：" + futureTask.isCancelled());

        //操作三：阻塞获取线程执行结果：使用execute/submit的入参futureTask
        try {
            String result = (String) futureTask.get(); //get方法会阻塞直到子线程执行完毕
            System.out.println("线程任务1 返回结果：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在线程池ExecutorService中执行FutureTask，阻塞主线程地获取线程执行结果
     */
    public void runInThreadPoolBlock() {

        //结合线程池ExecutorService使用
        //（1）创建任务
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            public String call() {
                System.out.println("线程任务2 正在执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("futureTask.cancel()会导致当前线程被中断interrput");
                    System.out.println("在这里做一些线程终止前的清理操作");
                    e.printStackTrace();
                }
                System.out.println("线程退出");
                return "线程任务2返回值";
            }
        });

        //（2）创建线程池
        ExecutorService executor = Executors.newCachedThreadPool(); //创建线程池
        //（3）使用线程池执行任务
        executor.execute(futureTask);
//        Future future = executor.submit(futureTask); //﻿与execute一样，返回值future并没有什么用

        //（4）对异步计算结果futureTask操作：isDone()、cancel()、get()...
        //操作一：获取任务执行情况
//            while (!futureTask.isDone()) {
//                System.out.println("任务是否完成：" + futureTask.isDone());
//            }

        //操作二：取消线程任务：使用interrput()中断线程
        // 1) 如果线程尚未执行，则直接取消成功，返回true
        // 2) 若线程正在执行中，则使用interrput()中断线程，返回true （注意这里的终止线程方式与Thread interrupt()一样，参见StopThread.java）
        // 3) 如果线程任务2已经执行完毕，则取消失败，返回false
//        System.out.println("中断线程任务2：" + futureTask.cancel(true));
//        System.out.println("线程任务2 被中断结果：" + futureTask.isCancelled());

        //操作三：阻塞获取线程执行结果：使用execute/submit的入参futureTask
        try {
            String result = (String) futureTask.get(); //get方法会阻塞直到子线程执行完毕
            System.out.println("线程任务2 返回结果：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在线程池中执行FutureTask，不阻塞主线程地获取线程执行结果
     * ﻿（其实这样意义不大，因为子线程本来就知道自己什么时候执行完毕，完全可以在线程return之前做）
     */
    public void runInThreadPoolNonBlock() {

        //结合线程池ExecutorService使用
        //（1）创建任务
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            public String call() {
                System.out.println("线程任务3 正在执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("futureTask.cancel()会导致当前线程被中断interrput");
                    System.out.println("在这里做一些线程终止前的清理操作");
                    e.printStackTrace();
                }
                System.out.println("线程退出");
                return "线程任务3返回值";
            }
        }) {
            @Override
            protected void done() {
                //﻿当isDone()为true时才调用done()，所以在 done()里调用get()就不会阻塞线程（注意：done()是在子线程中执行的）
                super.done();
                try {
                    String result = this.get();
                    System.out.println("线程任务3 返回结果：" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        //（2）创建线程池
        ExecutorService executor = Executors.newCachedThreadPool(); //创建线程池
        //（3）使用线程池执行任务
        executor.execute(futureTask);
//        Future future = executor.submit(futureTask); //﻿与execute一样，返回值future并没有什么用
    }


}
