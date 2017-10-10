package com.doing;

import org.junit.Test;

public class ReadWriteLockDemoTest {
    @Test
    public void testThreadSave() throws Exception {
        new ReadWriteLockDemo().testThreadSafe();

        Thread.sleep(15000);
    }

//    @Test
//    public void testThreadUnSafe() throws Exception {
//        new ReadWriteLockDemo().testThreadUnSafe();
//
//        Thread.sleep(3000);
//    }

}