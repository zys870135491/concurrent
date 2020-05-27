package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "c.WaitNotify")
public class WaitNotify {

    final static Object OBJECT = new Object();
    public static void main(String[] args) throws InterruptedException {

        new Thread(() ->{
            synchronized (OBJECT) {
                log.debug("执行......");
                try {
                    //进入到waitSet里等待，直到有其它线程拿到了OBJECT锁同时调用了notify方法
                    // 才会唤醒waitSet中的线程，去到EntryList中和其它线程一起竞争
                    OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码......");
            }
        },"t1").start();

        new Thread(() ->{
            synchronized (OBJECT) {
                log.debug("执行......");
                try {
                    OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码......");
            }
        },"t2").start();

        Thread.sleep(2000);
        log.debug("唤醒其它线程......");
        synchronized (OBJECT){
            // 唤醒OBJECT里waitSet中所有wait状态的线程
            OBJECT.notifyAll();
        }



    }
    
}


