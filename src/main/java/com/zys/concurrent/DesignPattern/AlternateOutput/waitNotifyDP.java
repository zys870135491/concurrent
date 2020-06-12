package com.zys.concurrent.DesignPattern.AlternateOutput;


/**
 * 交替输出waitNotify版本
 */
public class waitNotifyDP {

    public static void main(String[] args) {
        waitNotifyAO wo = new waitNotifyAO(1,5);

        new Thread(()->{
            wo.print("a",1,2);
        },"a").start();

        new Thread(()->{
            wo.print("b",2,3);
        },"b").start();

        new Thread(()->{
            wo.print("c",3,1);
        },"c").start();
    }

}


/**
 * 顺序输出        等待标记     下一个输出标记
 *    a               1               2
 *    b               2               3
 *    c               3               1
 */
class waitNotifyAO{

    // 等待标记
    private int flag;
    // 循环次数
    private int loopNum;

    public waitNotifyAO(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str , int waitFlag , int nextFlag){
           for(int i =0 ;i<loopNum;i++){
               synchronized (this){
                   while(waitFlag != this.flag){
                       try {
                           this.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   System.out.print(str);
                   this.flag = nextFlag;
                   this.notifyAll();
               }
           }
    }

}


