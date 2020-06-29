package com.zys.concurrent.noLockSix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongAdderTest {

    public static void main(String[] args) {

        demo(() ->new AtomicLong(0),(adder) -> adder.getAndIncrement());

        demo(() ->new LongAdder(), (adder) -> adder.increment());

    }

    /**
     * 函数式编程
     * @param adderSupplier Supplier相当于new对象，通过get()获取到对象(使用泛型，适用范围更广)
     * @param action Consumer消费者，接受参数而不返回值，adder相当于adderSupplier,当成一个参数传递给了后面的函数使用
     * @param <T>
     */
    private static <T> void demo(Supplier<T> adderSupplier , Consumer<T> action){

        long start = System.currentTimeMillis();
        T adder = adderSupplier.get();
        List<Thread> tList = new ArrayList<>();

        for(int i = 0;i<4;i++){
            tList.add(
                    new Thread(()->{
                        for(int j=0;j<500000;j++){
                            action.accept(adder);
                        }
                    },"t"+i)
            );
        }

        tList.forEach(t ->{t.start();});
        tList.forEach(t ->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();

        System.out.println(adder +" cost:"+(end-start));
    }



}
