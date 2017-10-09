package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

public class CallableFutureDemoTest {

    @Test
    public void test() throws Exception {
        new CallableFutureDemo().runInThreadBlock();
//        new CallableFutureDemo().runInThreadPoolBlock();
//        new CallableFutureDemo().runInThreadPoolNonBlock();

        Thread.sleep(2000);
    }

}