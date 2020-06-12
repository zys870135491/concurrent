package com.zys.concurrent.DesignPattern.AlternateOutput;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

public class ParkUnParkDP {

    static Thread t1 ;
    static Thread t2 ;
    static Thread t3 ;

    public static void main(String[] args) throws InterruptedException {
        ParkUnParkAO pA = new ParkUnParkAO(5);

         t1 = new Thread(() -> {
            pA.print("a", t2);
        }, "a");

        t2 = new Thread(() -> {
            pA.print("b", t3);
        }, "b");

         t3 = new Thread(() -> {
            pA.print("c", t1);
        }, "c");

         t1.start();
         t2.start();
         t3.start();

         Thread.sleep(1000);
         LockSupport.unpark(t1);

    }

}

@Slf4j(topic = "c.ParkUnParkAO")
class ParkUnParkAO{

    private int loopNum;

    public ParkUnParkAO(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str, Thread t){
        for (int i = 0 ;i < loopNum ; i++){

            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(t);
        }

    }

}
