package com.zys.concurrent.sharedModelManagement;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j(topic = "c.sellTicketTest")
public class SellTicketTest {

    public static void main(String[] args) {
        TicketWindow tw = new TicketWindow(2000);

        List<Thread> threadList = new ArrayList<>();
        List<Integer> amountList = new Vector<>();
        for(int i=0;i<4000;i++){
            Thread t = new Thread(()->{
                // 这里进行了读写操作，是线程不安全的，添加synchronized关键字
                int amount = tw.sellTicket(getRandom());
                try {
                    Thread.sleep(getRandom());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 这是在线程里，所以考虑线程安全问题，使用了Vector是线程安全的
                amountList.add(amount);
            });
            //threadList并不在线程里，是在main的线程里所以不需要考虑线程安全问题
            threadList.add(t);
            t.start();
        }

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.debug("剩余票数："+tw.getTicketCount());
        log.debug("卖出去了："+amountList.stream().mapToInt(c->c).sum());

    }


    static Random random = new Random();
    public static int getRandom(){
        return random.nextInt(5) + 1;
    }
}



class TicketWindow{
    private int ticketCount;

    public TicketWindow(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketCount(){
        return  ticketCount;
    }

    public synchronized int sellTicket(int amount){
        if(ticketCount >= amount){
            ticketCount -= amount;
            return amount;
        }else{
            return 0;
        }
    }
}
