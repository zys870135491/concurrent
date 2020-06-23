public class HungrySingletonTest {
    public static void main(String[] args) {
        for(int i = 0 ; i < 100; i++){
            new Thread(()->{
                HungrySingleton instance = HungrySingleton.getInstance();
                System.out.println(System.identityHashCode(instance));
            },"t"+i).start();
        }
    }
}


class HungrySingleton{
    private HungrySingleton(){};
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    public static HungrySingleton getInstance(){
        System.out.println("新建LazySingleton");
        return INSTANCE;
    }

}
