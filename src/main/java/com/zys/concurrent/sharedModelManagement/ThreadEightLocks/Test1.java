package com.zys.concurrent.sharedModelManagement.ThreadEightLocks;

import lombok.extern.slf4j.Slf4j;

/**
 * 结果: a b  或者 b a
 * 因为锁的是同一个对象，打印的结果会根据哪个线程先运行先打印哪个
 */
public class Test1 {

    public static void main(String[] args) {
        Number num = new Number();
        new Thread(() ->{num.a();},"t1").start();
        new Thread(() ->{num.b();},"t2").start();
    }

}

@Slf4j(topic = "c.number")
class Number{
    public synchronized  void a(){
        log.debug("1");
    }
    public synchronized  void b(){
        log.debug("2");
    }
}