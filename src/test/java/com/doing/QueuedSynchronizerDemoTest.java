package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author：ldy on 25/01/2018 13:28
 */
public class QueuedSynchronizerDemoTest {
    @Test
    public void testTwinsLock() throws Exception {
        QueuedSynchronizerDemo.testTwinsLock();

        Thread.sleep(5000); //等待子线程结束，否则System.exit()会导致所有子线程终止
    }

}