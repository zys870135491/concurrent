package com.zys.concurrent.sharedModelManagement.Lock.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.LockTimeout")
public class LockTimeout {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                if(!lock.tryLock(3, TimeUnit.SECONDS)){
                    log.debug("获取失败");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                log.debug("获取锁");
            }finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();
        try {
            Thread.sleep(2000);
        }finally {
            lock.unlock();
        }

    }

}
