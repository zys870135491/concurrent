package com.zys.concurrent.sharedModelManagement.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitNotifyStep3")
public class waitNotifyStep3 {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasToken = false;

    /**
     *notify 只能随机唤醒一个 WaitSet 中的线程，这时如果有其它线程也在等待，那么就可能唤醒不了正确的线 程，称之为【虚假唤醒】
     * 解决方法，改为 notifyAll
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
                room.notify();
            }
        },"送外卖的").start();

    }

}
