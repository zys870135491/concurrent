package com.zys.concurrent.sharedModelManagement.Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 哲学家就餐问题
 * 有五位哲学家，围坐在圆桌旁。
 * 他们只做两件事，思考和吃饭，思考一会吃口饭，吃完饭后接着思考。
 * 吃饭时要用两根筷子吃，桌上共有 5 根筷子，每位哲学家左右手边各有一根筷子。
 * 如果筷子被身边的人拿着，自己就得等待
 */
public class EatLock {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("阿基米德", c1, c2).start();
        new Philosopher("牛顿", c2, c3).start();
        new Philosopher("伽利略", c3, c4).start();
        new Philosopher("麦克", c4, c5).start();
        new Philosopher("杰瑞", c5, c1).start();
    }
    
}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread{
    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name ,Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
       while(true){
           synchronized (left){
               synchronized (right){
                   eat();
               }
           }
       }
    }

    public void eat(){
        log.debug("eating...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Chopstick {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }
}