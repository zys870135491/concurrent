package com.zys.concurrent.DesignPattern.SequentialControl;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步模式之顺序控制
 * waitNotify版本
 * 让先打印1，再打印2
 */
@Slf4j(topic = "c.waitNotifyEdition")
public class waitNotifyEdition {

    static Object lock = new Object();
    static Boolean isFlag = false;
    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                while(!isFlag){
                    try {
                        log.debug("t1等待t2结果");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        },"t1").start();


        new Thread(()->{
            synchronized (lock){
                isFlag =true;
                lock.notifyAll();
                log.debug("2");
            }

        },"t2").start();


    }

}
