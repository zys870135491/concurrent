package com.zys.concurrent.JMM.designPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * volatile实现两阶段优雅退出
 * 在一个线程 T1 中如何“优雅”终止线程 T2？这里的【优雅】指的是给 T2 一个料理后事的机会。
 */
@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        volatileTPT vpt = new volatileTPT();
        vpt.start();

        Thread.sleep(3000);
        log.debug("停止监听");
        vpt.stop();
    }

}

@Slf4j(topic = "c.volatileTPT")
class volatileTPT{

    private Thread moniterThread;

    // 添加了volatile保证了可见性(每次都拿的最新的数据，但不保证原子性)
    private volatile Boolean flag =false;

    public void start(){
        moniterThread = new Thread(()->{
            while (true){
                if(flag){
                    log.debug("料理后事");
                    break;
                }

                try {
                    Thread.sleep(1000);
                    log.debug("执行监控日志");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        },"t1");
        moniterThread.start();
    }

    public void stop(){
        flag = true;
        moniterThread.interrupt();
    }

}
