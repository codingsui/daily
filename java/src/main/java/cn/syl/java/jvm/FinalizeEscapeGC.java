package cn.syl.java.jvm;


public class FinalizeEscapeGC {

    public static FinalizeEscapeGC gc = null;

    public void isAlive(){
        System.out.println("i am alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize executed");
        FinalizeEscapeGC.gc = this;
    }

    public static void main(String[] args) throws InterruptedException {
        gc = new FinalizeEscapeGC();
        //对象第一次拯救自己
        gc = null;
        System.gc();
        Thread.sleep(1500);
        if (gc != null){
            gc.isAlive();
        }else{
            System.out.println("i am dead");
        }
        //下面方法和上面一样 却无法拯救自己
        gc = null;
        System.gc();
        Thread.sleep(1500);
        if (gc != null){
            gc.isAlive();
        }else{
            System.out.println("i am dead");
        }
    }
}
