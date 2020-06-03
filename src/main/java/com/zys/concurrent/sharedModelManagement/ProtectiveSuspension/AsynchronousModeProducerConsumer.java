package com.zys.concurrent.sharedModelManagement.ProtectiveSuspension;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 异步模式之生产者/消费者
 */
public class AsynchronousModeProducerConsumer {

    public static void main(String[] args) {

        MessageQueen mq = new MessageQueen(2);
        for (int i = 0; i <= 2 ;i++){
            int id = i;
            new Thread(()->{
                mq.put(new Message(id,"消息"+id));
            },"生产者"+i).start();
        }


        new Thread(()->{
            while (mq.getQueenNum() != 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = mq.take();
            }
        },"消费者").start();


    }

}

@Slf4j(topic = "c.MessageQueen")
class MessageQueen{
    //队列容器
    private LinkedList<Message> queen = new LinkedList<>();
    //队列容器的最大上限
    private int capCity;

    public MessageQueen (int capCity) {
        this.capCity = capCity;
    }


    public Message take(){
        synchronized (queen){
            while(queen.isEmpty()){
                try {
                    log.debug("队列已空，消费者等待");
                    queen.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queen.removeFirst();
            log.debug("已消费{}",message);
            queen.notifyAll();
            return  message;
        }
    }

    public void put(Message message){
        synchronized (queen){
            while(queen.size() == capCity){
                try {
                    log.debug("队列已满，生产者等待");
                    queen.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queen.add(message);
            log.debug("已生产{}",message);
            queen.notifyAll();
        }

    }

    public int getQueenNum(){
        return queen.size();
    }
}

class Message{
    private int id;
    private String message;

    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
