package cn.syl.java.operate;

public class SingletonTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                A a = Single.INSTANCE.getSingle();
                System.out.println(a);
            }).start();
        }

    }
    private enum Single{
        INSTANCE;
        private A a;
        Single() {
            a = new A();
        }
        public A getSingle(){
            return a;
        }
    }

    private static class A{}
}


