package com.zys.concurrent.sharedModelManagement;


import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.SynchronizedTest")
public class SynchronizedTest {

   /* static  int count = 0;
    static final Object lock = new Object();

    *//**
     * 虽然是加了5000，同时减了5000，但是结果并不是0,因为在做自增或者自减得时候还没来得及放入到主内存中，读取到的是脏数据
     * synchronized，来解决上述问题，即俗称的【对象锁】，它采用互斥的方式让同一 时刻至多只有一个线程能持有【对象锁】，
     * 其它线程再想获取这个【对象锁】时就会阻塞住。这样就能保证拥有锁 的线程可以安全的执行临界区内的代码，不用担心线程上下文切换
     *//*
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count:{},{}",count,1);
    }*/

    /**
     * 面向对象改进
     * 把需要保护的共享变量放入一个类Room中
     */
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                room.add();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                room.increament();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count:{}",room.getCount());
    }

}


class Room{
    private int count = 0;

    public void increament(){
        synchronized (this){
            count--;
        }
    }

    public void add(){
        synchronized (this){
            count++;
        }
    }

    public int getCount(){
        synchronized (this){
            return count;
        }
    }
}