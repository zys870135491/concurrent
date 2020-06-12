package com.zys.concurrent.DesignPattern.SequentialControl;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步模式之顺序控制
 * ReenTrantLock版本
 * 让先打印1，再打印2
 */
@Slf4j(topic = "c.ReentrantLockEdition")
public class ReentrantLockEdition {

    static ReentrantLock lock = new ReentrantLock();
    static Boolean isFlag = false;
    static  Condition waitRoom = lock.newCondition();

    public static void main(String[] args) {
        new Thread(()->{

            lock.lock();
            try {
                while(!isFlag){
                    try {
                        waitRoom.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }finally {
                lock.unlock();
            }


        },"t1").start();

        new Thread(()->{
            lock.lock();
            log.debug("2");
            try {
                isFlag =true;
                waitRoom.signal();
            }finally {
                lock.unlock();
            }
        },"t2").start();

    }


}
