package cn.syl.java.current.aqs;

public class InheritableThreadLocalDemo {
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("mainThread");
        System.out.println("value:"+threadLocal.get());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String value = threadLocal.get();
                System.out.println("value:"+value);
            }
        });
        thread.start();
    }

}
