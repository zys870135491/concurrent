import com.zys.concurrent.threadMethod.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.balkingTest")
public class balkingTest {

    public static void main(String[] args) {

        TestVolita tv = new TestVolita();
        for(int i = 0 ; i < 100; i++){
            new Thread(()->{
                TestVolita tv1 = new TestVolita();
                tv1.init();
            },"t"+i).start();
        }

    }
}

@Slf4j(topic = "c.TestVolita")
class TestVolita{
    volatile static  boolean inittialized = false;

    public void init(){
        synchronized (TestVolita.class){
            if(inittialized){
                return;
            }
            doInit();
            inittialized = true;
        }
    }

    public void doInit(){
        log.debug("doInit()");
    }
}
