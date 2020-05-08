package com.zys.concurrent.threadMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.SleepAndYield")
public class SleepAndYield {

    public static void main(String[] args) throws InterruptedException {
        SleepAndYield.sleepInterrupt();
    }

    /**
     * 以使用  interrupt 方法打断正在睡眠的线程
     * @throws InterruptedException
     */
    public static void sleepInterrupt() throws InterruptedException {
        Runnable runnable = () -> {
            log.debug("sleepStart...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("running...");
        };

        Thread t = new Thread(runnable, "t");
        t.start();
        Thread.sleep(1000);
        log.debug("interrupt...");
        t.interrupt();
    }

}

