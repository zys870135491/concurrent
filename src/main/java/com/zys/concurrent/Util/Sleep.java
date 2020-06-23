package com.zys.concurrent.Util;

public class Sleep {

    public static void meSleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
