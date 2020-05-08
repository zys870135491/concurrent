package com.zys.concurrent.threadMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Join")
public class Join {
    static int  r = 0;
    static int  r2 = 0;
    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    public static void test() throws InterruptedException {
        log.debug("main开始");
        Runnable runnable = () -> {
          log.debug("runnable开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
            log.debug("runnable结束");
        };
        Thread t = new Thread(runnable, "t");
        t.start();
        t.join();
        log.debug("结果为：{}",r);
        log.debug("main结束");
    }


    public static void test2() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.debug("runnable begin....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
            log.debug("runnable end....");
        }, "t");

        Thread t2 = new Thread(() -> {
            log.debug("runnable2 begin....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 20;
            log.debug("runnable2 end....");
        }, "t2");

        long start = System.currentTimeMillis();
        t.start();
        t2.start();

        log.debug("t.join start....");
        t.join();
        log.debug("t.join end....");

        log.debug("t2.join start....");
        t2.join();
        log.debug("t2.join end....");

        long end = System.currentTimeMillis();

        log.debug("r1:{} r2:{} time:{}",r,r2,end-start);
    }

    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("t1 begin....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 20;
            log.debug("t1 end....");
        }, "t1");

        t1.start();
        t1.join(1000);
        log.debug("r:"+r);
    }

}
