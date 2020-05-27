package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitNotifyStep2")
public class waitNotifyStep2 {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasToken = false;

    /**
     *解决了其它干活的线程阻塞的问题
     * 但如果有其它线程也在等待条件呢？
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
             synchronized (room) {
             hasCigarette = true;
             log.debug("烟到了");
             room.notify();
            }
        },"送烟的").start();

    }

}
