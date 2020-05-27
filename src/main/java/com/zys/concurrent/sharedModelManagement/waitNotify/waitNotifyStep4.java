package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitNotifyStep4")
public class waitNotifyStep4 {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasToken = false;

    /**
     * 用 notifyAll 仅解决某个线程的唤醒问题，但使用 if + wait 判断仅有一次机会，一旦条件不成立，就没有重新 判断的机会了
     * 解决方法，用 while + wait，当条件不成立，再次 wait
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
                }else{
                    log.debug("干不了活");
                }
            }

        },"小南").start();

        new Thread(() ->{
            synchronized (room){
                log.debug("外卖到了吗 [{}]",hasToken);
                if(!hasToken){
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

    }

}
