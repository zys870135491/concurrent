package com.zys.concurrent.noLockSix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    public static void main(String[] args) {
        Account.demo(new AccountSafe(10000));
        Account.demo(new AccountCAS(10000));
    }
}

// 无锁解决（CAS）
class AccountCAS implements  Account{
    private AtomicInteger balance;

    public AccountCAS(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    /* compareAndSet 正是做这个检查，在 set 前，先比较 prev 与当前值
       - 不一致了，next 作废，返回 false 表示失败,比如，别的线程已经做了减法，当前值已经被减成了 990
         那么本线程的这次 990 就作废了，进入 while 下次循环重试
       - 一致，以 next 设置为新值，返回 true 表示成功
     */
    @Override
    public void withdraw(int amount) {
        // 需要不断尝试，直到成功为止
       /* while (true){
            // 比如拿到了旧值 1000
            int prev = balance.get();
            // 在这个基础上 1000-10 = 990
            int next = prev - amount;
            if(balance.compareAndSet(prev,next)){
                break;
    }
}*/
        //balance.addAndGet(-amount);
        // 函数式编程，底层还是compareAndSet
        balance.updateAndGet(value -> value -10);
    }
}


// 加锁解决
class AccountSafe implements Account{

    private Integer balance;

    public AccountSafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(int amount) {
        balance -= amount;
    }
}

interface Account{
    // 获取余额
    Integer getBalance();
    // 取款
    void withdraw(int amount);

    /**
     *方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(Account account){
        List<Thread> tl = new ArrayList<>();
        long start = System.currentTimeMillis();
        for(int i = 0; i <1000 ;i++){
            tl.add(new Thread(()->{account.withdraw(10);}));
        }

        tl.forEach(Thread::start);
        for (Thread t : tl) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(account.getBalance()+" cost:"+(end-start));
    }
}