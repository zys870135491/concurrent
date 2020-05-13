package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * 结果:b 1s后 a
 * 因为b锁的是类，a锁的是this，锁的不是同一个对象，a要先休息一秒，会把时间片让给b,所以只有一个结果
 */
public class Test5 {

    public static void main(String[] args) {
        Number5 num = new Number5();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num.b();},"t2").start();
    }

}

@Slf4j(topic = "c.number")
class Number5{
    public static synchronized  void a(){
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
