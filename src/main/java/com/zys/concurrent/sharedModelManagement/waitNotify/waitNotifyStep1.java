package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitNotifyStep1")
public class waitNotifyStep1 {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasToken = false;

    /**
     * 其它干活的线程，都要一直阻塞，效率太低
     * 小南线程必须睡足 2s 后才能醒来，就算烟提前送到，也无法立刻醒来
     * 加了 synchronized (room) 后，就好比小南在里面反锁了门睡觉，烟根本没法送进门，main 没加 synchronized 就好像 main 线程是翻窗户进来的
     * 解决方法，使用 wait - notify 机制

     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        new Thread(() ->{
            synchronized (room){
                log.debug("有烟吗 [{}]",hasCigarette);
                if(!hasCigarette){
                    log.debug("没有烟再等等");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟吗 [{}]",hasCigarette);
                if(hasCigarette){
                    log.debug("开始干活");
                }
            }

        },"小南").start();


        for(int i = 0 ;i<5;i++){
            new Thread(() ->{
                synchronized (room) {
                    log.debug("开始干活");
                }
            },"其他人").start();
        }

        Thread.sleep(1000);

        new Thread(()->{
           // synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了");
            //}
        },"送烟的").start();

    }

}
