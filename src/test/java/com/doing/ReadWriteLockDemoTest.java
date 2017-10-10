package com.doing;

import org.junit.Test;

public class ReadWriteLockDemoTest {
    @Test
    public void testReadWriteLock() throws Exception {
        new ReadWriteLockDemo().testReadWriteLock();

        Thread.sleep(15000);
    }

//    @Test
//    public void testSafeCache() throws Exception {
//        new ReadWriteLockDemo().testSafeCache();
//
//        Thread.sleep(3000);
//    }
//
//    @Test
//    public void testUnSafeCache() throws Exception {
//        new ReadWriteLockDemo().testUnSafeCache();
//
//        Thread.sleep(3000);
//    }
}