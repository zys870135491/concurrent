package com.zys.concurrent.JMM;

import com.zys.concurrent.Util.Sleep;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.test")
public class test {

    public static void main(String[] args) {
        for(int i =0;i<=300;i++){
            new Thread(()->{
                Singleton instance = Singleton.getInstance();
                if(instance ==null){
                    System.out.println("获取为空");
                }
            },"t"+i).start();
        }
    }

}

final class Singleton {
    private Singleton() { }
    private static volatile Singleton INSTANCE = null;

    public static Singleton getInstance() {

        if(INSTANCE ==null){
            synchronized (Singleton.class){
                if (INSTANCE == null) {
                    System.out.println("新建");
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;

    }

}

