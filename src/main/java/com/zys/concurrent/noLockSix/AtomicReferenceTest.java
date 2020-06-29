package com.zys.concurrent.noLockSix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public static void main(String[] args) {
        AccountReference.demo(new AccountReferenceSafe(new BigDecimal("10000")));
    }

}

class AccountReferenceSafe implements AccountReference{

    // 原子引用泛型里写数据类型，其它的和原子整数一致
    private AtomicReference<BigDecimal> balance;

    public AccountReferenceSafe(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        balance.updateAndGet(value -> value.subtract(amount));
    }
}

interface AccountReference{
    // 获取余额
    BigDecimal getBalance();
    // 取款
    void withdraw(BigDecimal amount);

    /**
     *方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(AccountReference accountReference){
        List<Thread> tl = new ArrayList<>();
        long start = System.currentTimeMillis();
        for(int i = 0; i <1000 ;i++){
            tl.add(new Thread(()->{accountReference.withdraw(new BigDecimal(10));}));
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
        System.out.println(accountReference.getBalance()+" cost:"+(end-start));
    }
}
