package com.zys.concurrent.DesignPattern.ProtectiveSuspension;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ProtectiveSuspension")
public class ProtectiveSuspensionStep2 {
    /**
     * 同步模式之保护性暂停.带超时版
     */
    public static void main(String[] args) {
        GuardeObjectTimeOut guro = new GuardeObjectTimeOut();

        new Thread(()->{
            log.debug("等待结果");
            String result = (String)guro.get(5000);
            log.debug("result:"+result);
        },"t1").start();

        new Thread(()->{
            log.debug("执行下载");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String r = "下载完成。。。。。";
            guro.set(null);

        },"t2").start();

    }
}

@Slf4j(topic = "c.GuardeObjectTimeOut")
class GuardeObjectTimeOut {
    private Object response;
    private final Object lock  = new Object();

    public Object get(long timeOut) {

        // 开始时间
        long begin = System.currentTimeMillis();
        // 经历时间
        long timePassed = 0;
        synchronized (lock){
            while(response ==null){
                long waitTime = timeOut -timePassed;
                // 超过timeOut直接跳出

                log.debug("waitTime:"+waitTime);
                if(waitTime <=0){
                    log.debug("break");
                    break;
                }
                try {
                    lock.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timePassed = System.currentTimeMillis()- begin;
            }
            return response;
        }
    }

    public void set(Object  response){
        synchronized (lock){
            this.response = response;
            lock.notifyAll();
        }
    }

}
