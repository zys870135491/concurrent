package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "c.WaitNotifyTest")
public class WaitNotifyTest {

     static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
                synchronized (obj){
                    log.debug("执行。。。。");
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("执行其他代码");
                }
        },"t1").start();

        new Thread(()->{
            synchronized (obj){
                log.debug("执行。。。。");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("执行其他代码");
            }
        },"t2").start();

        Thread.sleep(2000);
        synchronized (obj){
            log.debug("唤醒其它线程");
            obj.notifyAll();
        }

    }

}
