package com.doing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadJoinDemoTest {

    @Test
    public void test() throws Exception {
        new ThreadJoinDemo().test();

        Thread.sleep(2000); //等待子线程结束，否则System.exit()会导致所有子线程终止

        System.out.println();
        System.out.println("junit test 结束前：会调用 System.exit()");
    }

}