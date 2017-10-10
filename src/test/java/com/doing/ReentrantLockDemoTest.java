package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReentrantLockDemoTest {
    @Test
    public void test1() throws Exception {
        new ReentrantLockDemo().test();

        Thread.sleep(5000);

    }

}