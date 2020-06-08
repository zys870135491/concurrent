package com.zys.concurrent.sharedModelManagement.Lock.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.Interruptible")
public class Interruptible {

    static ReentrantLock lock =  new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("启动......");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断");
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();
        Thread.sleep(1000);
        try {
            log.debug("t1 被打断");
            t1.interrupt();
        }finally {
            lock.unlock();
        }

    }
}
