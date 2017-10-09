package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolDemoTest {
    @Test
    public void fixedThreadPoolTest() throws Exception {
        new ThreadPoolDemo().fixedThreadPoolTest();
        Thread.sleep(2000);
    }

    @Test
    public void singleThreadPoolTest() throws Exception {
        new ThreadPoolDemo().singleThreadPoolTest();
        Thread.sleep(5000);
    }

    @Test
    public void cacheThreadPoolTest() throws Exception {
        new ThreadPoolDemo().cacheThreadPoolTest();
        Thread.sleep(1000);
    }

    @Test
    public void scheduledThreadPoolTest() throws Exception {
        new ThreadPoolDemo().scheduledThreadPoolTest();
//        Thread.sleep(1000);
        Thread.sleep(10000);
    }

}