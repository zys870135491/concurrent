package com.zys.concurrent.threadMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.interrupt")
public class Interrupt {

    /*public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true){
                // 打断 sleep，wait，join 的线程会清空isInterrupted状态
                // 如果线程没有sleep，wait，join，可以通过isInterrupted状态来优雅的结束此线程
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted){
                    log.debug("打断状态"+interrupted);
                    break;
                }
            }
        },"t1");

        t.start();
        log.debug("interrupt start");
        t.interrupt();
    }*/


    public static void main(String[] args) throws InterruptedException {

        /**
         * tpt线程在执行监控日志操作，点击某个按钮之后终止掉日志操作
         */
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        tpt.stop();
    }

}

@Slf4j(topic = "c.twoPhaseTermination")
class TwoPhaseTermination{
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread thread = Thread.currentThread();
                if(thread.isInterrupted()){
                    log.debug("料理后事逻辑");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控逻辑");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 进行打断的时候出现sleep会抛异常，这时候需要重新设置打断标记
                    thread.interrupt();
                }
            }
        },"t1");

        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }

}
