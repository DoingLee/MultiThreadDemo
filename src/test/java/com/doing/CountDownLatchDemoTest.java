package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author：ldy on 26/01/2018 09:47
 */
public class CountDownLatchDemoTest {

    @Test
    public void testCountDownLatch() throws Exception {
        for (int i = 0; i < 6; i++) {
            CountDownLatchDemo.testCountDownLatch();
            Thread.sleep(2000);
            System.out.println();
        }
    }

}