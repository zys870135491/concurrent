package com.zys.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.futureTask")
public class FutureTaskTest {
    public static void main(String[] args) throws Exception{
        // 创建任务对象
        FutureTask<Integer> task = new FutureTask<>(()->{
            log.debug("future running");
            Thread.sleep(200000);
            return 100;
        });

        new Thread(task, "f").start();
        //主线程堵塞，同步等待task执行完毕返回的结果
        //log.debug 第二个参数的值会填充到第一个参数里的{}
        log.debug("返回结果：{}",task.get());
    }
}
