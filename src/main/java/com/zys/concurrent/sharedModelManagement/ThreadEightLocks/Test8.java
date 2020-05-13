package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * 结果:  b 1s后 a
 * 因为锁的不是同一个对象，a要先休息一秒，会把时间片让给b,所以只有一个结果
 */
public class Test8 {

    public static void main(String[] args) {
        Number8 num = new Number8();
        Number8 num1 = new Number8();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num1.b();},"t2").start();
    }

}

@Slf4j(topic = "c.number")
class Number8{
    public static synchronized  void a(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("a");
    }
    public  synchronized  void b(){
        log.debug("b");
    }
}
