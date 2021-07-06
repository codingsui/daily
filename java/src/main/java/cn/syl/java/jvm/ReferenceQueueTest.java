package cn.syl.java.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.WeakHashMap;

public class ReferenceQueueTest {

    public static void main(String[] args) throws InterruptedException {
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        String str = new String("123");
        System.out.println(str);

        new Thread(){
            @Override
            public void run() {
                while (true){
                    Object obj = referenceQueue.poll();

                    if(obj != null){
                        try {
                            System.out.println(obj);
                            System.out.println(obj.toString());
                            Field rereferent = Reference.class
                                    .getDeclaredField("referent");
                            rereferent.setAccessible(true);
                            Object result = rereferent.get(obj);
                            System.out.println("gc will collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }.start();
        Thread.sleep(100);
        PhantomReference<String> a = new PhantomReference<>(str,referenceQueue);
        System.out.println(a);
        System.out.println(a.get());
        str = null;
        System.out.println(a.get());
        Thread.sleep(1000);

        System.gc();
        System.out.println(a);

        Thread.sleep(1000);

    }
}
