package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadLocalDemoTest {
    @Test
    public void test() throws Exception {
        new ThreadLocalDemo().test();

        Thread.sleep(3000);
    }

}