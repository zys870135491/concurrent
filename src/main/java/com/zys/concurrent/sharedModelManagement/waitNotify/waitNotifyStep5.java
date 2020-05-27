package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitNotifyStep5")
public class waitNotifyStep5 {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasToken = false;

    /*
    这是notify的正确使用姿势，使用while去做判断
    synchronized (lock){
        while(条件不成立){
            lock.wait();
        }
        //干活
    }

    //另一个线程
    synchronized (lock){
        lock.notifyAll();
    }*/
    public static void main(String[] args) throws InterruptedException {

        new Thread(() ->{
            synchronized (room){
                log.debug("有烟吗 [{}]",hasCigarette);
                while(!hasCigarette){
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
                }else{
                    log.debug("干不了活");
                }
            }

        },"小南").start();

        new Thread(() ->{
            synchronized (room){
                log.debug("外卖到了吗 [{}]",hasToken);
                while(!hasToken){
                    log.debug("外卖没到再等等");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                log.debug("外卖到了吗 [{}]",hasToken);
                if(hasToken){
                    log.debug("开始干活");
                }else{
                    log.debug("干不了活");
                }
            }

        },"小北").start();



        Thread.sleep(1000);

        new Thread(()->{
            synchronized (room) {
                hasToken = true;
                log.debug("外卖到了");
                room.notifyAll();
            }
        },"送外卖的").start();

        Thread.sleep(7000);

        new Thread(()->{
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了");
                room.notifyAll();
            }
        },"送烟的").start();

    }


}
