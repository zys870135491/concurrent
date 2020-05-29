package com.zys.concurrent.sharedModelManagement.ProtectiveSuspension;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ProtectiveSuspension")
public class ProtectiveSuspensionStep1 {
    /**
     * 同步模式之保护性暂停
     */
    public static void main(String[] args) {
        GuardeObject guro = new GuardeObject();

        new Thread(()->{
            log.debug("等待结果");
            String result = (String)guro.get();
            log.debug("result:"+result);
        },"t1").start();

        new Thread(()->{
            log.debug("执行下载");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String r = "下载完成。。。。。";
            guro.set(r);

        },"t2").start();

    }
}

class GuardeObject {
    private Object response;
    private final Object lock  = new Object();

    public Object get() {
        synchronized (lock){
            while(response ==null){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
