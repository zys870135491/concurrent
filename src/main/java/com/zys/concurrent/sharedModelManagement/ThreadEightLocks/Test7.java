package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * 结果: 1s后 a b  或者 b 1s后 a
 * 因为锁的是同一个对象(类只有一个,锁的同一个类固锁的同一对象)，打印的结果会根据哪个线程先运行先打印哪个
 */
public class Test7 {

    public static void main(String[] args) {
        Number7 num = new Number7();
        Number7 num1 = new Number7();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num1.b();},"t2").start();
    }

}

@Slf4j(topic = "c.number")
class Number7{
    public static synchronized  void a(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("a");
    }
    public static synchronized  void b(){
        log.debug("b");
    }
}


