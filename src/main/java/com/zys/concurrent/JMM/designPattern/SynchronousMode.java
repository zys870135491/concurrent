package com.zys.concurrent.JMM.designPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步模式之Balking
 * Balking （犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，
 * 那么本线程就无需再做 了，直接结束返回
 */
public class SynchronousMode {

    public static void main(String[] args) throws InterruptedException {
        SynchronousModeService sms = new SynchronousModeService();
        sms.start();
        sms.start();
        sms.start();
        Thread.sleep(3000);
        sms.stop();
    }

}

@Slf4j(topic = "c.SynchronousModeService")
class SynchronousModeService{

    private Thread moniterThread;

    private volatile Boolean flag = false;

    private  Boolean starting = false;


    public void start() throws InterruptedException {
        // 保证starting的原子性
        synchronized (this){
            if(starting){
                return;
            }
            starting = true;
        }
        moniterThread = new Thread(()->{
                while (true) {
                    if (flag) {
                        log.debug("处理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                        log.debug("日志监控执行");
                    } catch (InterruptedException e) {
                    }
                }
            },"moniterThread");
        moniterThread.start();

    }

    public void stop(){
        flag = true;
        moniterThread.interrupt();
    }

}
