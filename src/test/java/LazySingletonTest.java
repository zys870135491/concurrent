import lombok.extern.slf4j.Slf4j;

@Slf4j(topic ="c.LazySingletonTest")
public class LazySingletonTest {

    public static void main(String[] args) {
        for(int i = 0 ; i < 200; i++){
            int j = i;
            new Thread(()->{
                LazySingleton instance = LazySingleton.getInstance();
                //synchronized (instance){
                    int ticket = instance.getTicket();
                    if(ticket >0){
                        ticket--;
                        instance.setTicket(ticket);
                    }

                   log.debug("剩余票数："+instance.getTicket());
                //}
            },"t"+i).start();
        }
    }

}


class LazySingleton{

    private int ticket =20;

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    private LazySingleton(){
    }


    private static volatile LazySingleton INSTANCE = null;

    public static LazySingleton getInstance(){
        if(INSTANCE  == null){
            synchronized (LazySingleton.class){
                if(INSTANCE ==null){
                    System.out.println("新建LazySingleton");
                    INSTANCE = new LazySingleton();
                }
                return INSTANCE;
            }
        }
          return INSTANCE;
    }
}