package com.zys.concurrent.sharedModelManagement.ProtectiveSuspension;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 保护性暂停扩展-----解耦和等待生产
 */
@Slf4j(topic = "c.protectiveSuspensionStep3")
public class ProtectiveSuspensionStep3 {


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Thread.sleep(1000);
        for (Integer id : MailBoxs.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }

}

@Slf4j(topic = "c.people")
class People extends Thread{
    @Override
    public void run() {

        GuardeObjectDecoup go = MailBoxs.createGuardeObjectDecoup();
        log.debug("开始收信 id:{}", go.getId());
        Object mail = go.get(2000);
        log.debug("收到信 id:{}, 内容:{}", go.getId(), mail);
    }
}

@Slf4j(topic = "c.postman")
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardeObjectDecoup go = MailBoxs.getGuardeObjectDecoup(id);
        log.debug("送信id:{},内容:{}",id,mail);
        go.set(mail);
    }
}

/**
 * 邮箱
 */
@Slf4j(topic = "c.mailBoxs")
class MailBoxs{
    // hashtable保证线程安全
    private static Map<Integer, GuardeObjectDecoup> boxes = new Hashtable<>();

    private static int id;

    public static synchronized int generateId(){
        return  id++;
    }

    public static GuardeObjectDecoup getGuardeObjectDecoup(int id){
        return  boxes.remove(id);
    }

    public static  GuardeObjectDecoup createGuardeObjectDecoup(){
        GuardeObjectDecoup go = new GuardeObjectDecoup(generateId());
        boxes.put(go.getId(),go);
        return go;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }


}

class GuardeObjectDecoup{

    private Object resposen;
    // 邮件的唯一标识
    private int id;

    public GuardeObjectDecoup(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object get(long timeOut){
        long start = System.currentTimeMillis();
        long timePassed =0;
        synchronized (this){
            while(resposen == null){
                // 这一轮循环应该等待的时间
                long waitTime = timeOut - timePassed;
                if(timeOut - timePassed <= 0){
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待了多久
                timePassed = System.currentTimeMillis() -start;
            }
        }

        return resposen;
    }

    //产生结果
    public void set(Object resposen){
        synchronized (this){
            this.resposen = resposen;
            this.notifyAll();
        }
    }


}
