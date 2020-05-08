package com.zys.concurrent.threadMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.StartAndRun")
public class StartAndRun {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for(int i =0 ;i<100000000;i++){
            }
          log.debug("running...");
        };

        Thread t = new Thread(runnable, "t");
        t.start();
        log.debug("do other thing...");
    }

}
