package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * 结果: 1s后 a b  或者 b 1s后a
 * 因为锁的是同一个对象，如果先运行t1线程则会是1s后 a b ,反之b 1s后a
 */
public class Test2 {

    public static void main(String[] args) {
        Number1 num = new Number1();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num.b();},"t2").start();
    }

}

@Slf4j(topic = "c.number")
class Number1{
    public synchronized  void a(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("a");
    }
    public synchronized  void b(){
        log.debug("b");
    }
}

