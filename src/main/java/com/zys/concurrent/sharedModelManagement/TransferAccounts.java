package com.zys.concurrent.sharedModelManagement;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "c.transferAccounts")
public class TransferAccounts {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t = new Thread(() -> {
            for(int i =0 ;i<1000;i++){
                // 这里有共享资源，所以线程不安全
                a.transgfer(b,getRandom());
            }
        });
        Thread t1 = new Thread(() -> {
            for(int i =0 ;i<1000;i++){
                b.transgfer(a,getRandom());
            }
        });
        t1.start();
        t.start();
        t1.join();
        t.join();

        log.debug("剩余的钱："+(a.getMoney()+b.getMoney()));

    }

    static Random random = new Random();
    public static int getRandom(){
        return random.nextInt(100);
    }
}


class Account{
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void  transgfer(Account target,int amount){
        // 这里有两个共享资源，①this.getMoney ②target.getMoney,所以需要锁住类
        synchronized (Account.class){
            if(this.money>=amount){
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney()+amount);
            }
        }
    }

}
