package com.zys.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.RunnableTest")
public class RunnableTest {

    public static void main(String[] args) {
        Runnable r = () -> { log.debug("Runnable running");};
        Thread t = new Thread(r,"runnable");
        t.start();
    }

}
