package com.zys.concurrent.threadMethod;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.park")
public class Park {

    public static void main(String[] args) {
        test();
    }

    /**
     * LockSupport.park()停止当前线程，不会执行后面的代码
     * 打断了park线程，会继续执行下面的代码
     */
    public static void test(){

        Thread t1 = new Thread(() -> {
            log.debug("park");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
        }, "t1");

        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

}
