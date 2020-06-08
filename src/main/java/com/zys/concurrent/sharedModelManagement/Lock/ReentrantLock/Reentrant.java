package com.zys.concurrent.sharedModelManagement.Lock.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 相对于 synchronized 它具备如下特点
 * 可中断
 * 可以设置超时时间
 * 可以设置为公平锁
 * 支持多个条件变量
 * 这里是可重入特点，synchronized也具备
 */

@Slf4j(topic = "c.Reentrant")
public class Reentrant {

    static  ReentrantLock lock =  new ReentrantLock();

    public static void main(String[] args) {
        m1();
    }

    public static  void m1(){
        lock.lock();
        try {
            m2();
            log.debug("m1");
        } finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try {
            m3();
            log.debug("m2");
        } finally {
            lock.unlock();
        }
    }

    public static void m3(){
        lock.lock();
        try {
            log.debug("m3");
        } finally {
            lock.unlock();
        }
    }

}
