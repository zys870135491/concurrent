package com.zys.concurrent.threadMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.daemon")
public class Daemon {

    /**
     * 默认情况下，Java 进程需要等待所有线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，
     * 只要其它非守 护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束
     */
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("开始....");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束....");
        }, "daemon");

        // 设置为守护线程
        t1.setDaemon(true);
        t1.start();
        log.debug("main结束");

    }

}
