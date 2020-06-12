package com.zys.concurrent.DesignPattern.AlternateOutput;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替输出ReentrantLock版本
 */
public class ReentrantLockDP {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockAO ro = new ReentrantLockAO(5);
        Condition a = ro.newCondition();
        Condition b = ro.newCondition();
        Condition c = ro.newCondition();

        new Thread(()->{
            ro.print("a",a,b);
        },"a").start();
        new Thread(()->{
            ro.print("b",b,c);
        },"b").start();
        new Thread(()->{
            ro.print("c",c,a);
        },"c").start();

        Thread.sleep(1000);
        ro.lock();
        try {
            a.signal();
        }finally {
            ro.unlock();
        }
    }
}

/**
 * 顺序输出
 *    a
 *    b
 *    c
 */
class ReentrantLockAO extends ReentrantLock{
    private int loopNum;

    public ReentrantLockAO(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str, Condition con, Condition nextCon){

        for(int i = 0; i<loopNum; i++){
            lock();
            try {
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(str);
                nextCon.signal();

            }finally {
                unlock();
            }
        }

    }
}
