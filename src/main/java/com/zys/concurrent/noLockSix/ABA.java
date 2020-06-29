package com.zys.concurrent.noLockSix;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题 普通的AtomicReference 数据A->B B->A A->C是可以修改成功的，只对比的是值是否一致
 * AtomicStampedReference对比的是版本号是否一致，一旦被其它线程修改过，就修改失败
 */
@Slf4j(topic = "c.ABA")
public class ABA {

    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);

    public static void main(String[] args) throws InterruptedException {

        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("版本号：{}",stamp);
        other();
        Thread.sleep(1000);
        log.debug("从A-C,{}",ref.compareAndSet(prev,"C",stamp,stamp+1));

    }


    private static void other(){
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("版本号：{}",stamp);
            log.debug("从A-B,{}",ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();

        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("版本号：{}",stamp);
            log.debug("从B-A,{}",ref.compareAndSet(ref.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }
}

