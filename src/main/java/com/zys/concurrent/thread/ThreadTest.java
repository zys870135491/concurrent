package com.zys.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadTest")
public class ThreadTest {
    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t");
        t.start();
        log.debug("main running");
    }
}
