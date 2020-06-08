package com.zys.concurrent.sharedModelManagement.Lock.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.SolveEatLock")
public class SolveEatLock {


    public static void main(String[] args) {
        ChopsticksReentrantLock c1 = new ChopsticksReentrantLock("1");
        ChopsticksReentrantLock c2 = new ChopsticksReentrantLock("2");
        ChopsticksReentrantLock c3 = new ChopsticksReentrantLock("3");
        ChopsticksReentrantLock c4 = new ChopsticksReentrantLock("4");
        ChopsticksReentrantLock c5 = new ChopsticksReentrantLock("5");

        new PhilosopherReentrantLock("阿基米德", c1, c2).start();
        new PhilosopherReentrantLock("牛顿", c2, c3).start();
        new PhilosopherReentrantLock("伽利略", c3, c4).start();
        new PhilosopherReentrantLock("麦克", c4, c5).start();
        new PhilosopherReentrantLock("杰瑞", c5, c1).start();
    }
}

@Slf4j(topic = "c.Philosopher")
class PhilosopherReentrantLock extends  Thread{
    private ChopsticksReentrantLock left;
    private ChopsticksReentrantLock right;

    public PhilosopherReentrantLock( String name, ChopsticksReentrantLock left, ChopsticksReentrantLock right) {
        super( name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
       while (true){
           if(left.tryLock()){
               try {
                   if( right.tryLock()){
                       try {
                           eat();
                       }finally {
                           right.unlock();
                       }
                   }
               }finally {
                   left.unlock();
               }
           }

       }
    }

    public void eat(){
        log.debug("eating....");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ChopsticksReentrantLock extends ReentrantLock{
    private String name;

    public ChopsticksReentrantLock(String name) {
        this.name = name;
    }
}