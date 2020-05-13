package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * c 1s后 a b 或 b c 1s后 a 或 cb 1s后 a
 * 因为t1,t2锁的是同一个对象，t3没有进行加锁，每次c都会优先输出，a,b会根据t1还是t2先运行决定
 */
public class Test3 {

    public static void main(String[] args) {
        Number2 num = new Number2();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num.b();},"t2").start();
        new Thread(() ->{num.c();},"t3").start();

    }

}

@Slf4j(topic = "c.number")
class Number2{
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

    public  void c(){
        log.debug("c");
    }
}
