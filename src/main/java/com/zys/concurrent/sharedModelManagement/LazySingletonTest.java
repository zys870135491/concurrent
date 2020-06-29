package com.zys.concurrent.sharedModelManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.LazySingletonTest")
public class LazySingletonTest {

    public static void main(String[] args) {

        for (int i = 0; i < 30; i++){
            new Thread(()->{
                LazySingleton instance = LazySingleton.getInstance();
                synchronized (instance){
                    int ticket = instance.getTicket();
                    if(ticket >0){
                        ticket--;
                        instance.setTicket(ticket);
                        log.debug("剩余：{}",ticket);
                        return;
                    }
                    log.debug("余票不足");
                }
            },"t"+i).start();
        }

    }

}


@Slf4j(topic = "c.LazySingleton")
class LazySingleton {

    private int ticket = 20;

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    private LazySingleton(){};

    private static volatile LazySingleton INSTANCE = null;

    public static LazySingleton getInstance(){

        if(INSTANCE == null){
            synchronized (LazySingleton.class){
                if(INSTANCE == null){
                   /* String tn = Thread.currentThread().getName();
                    log.debug("新建:"+tn);*/
                    INSTANCE = new LazySingleton();
                }
                return  INSTANCE;
            }
        }
        return INSTANCE;


    }

}
