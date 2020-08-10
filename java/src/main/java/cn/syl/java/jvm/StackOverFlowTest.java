package cn.syl.java.jvm;

/**
 * -Xss128K 32位系统
 * -Xss160K Mac系统
 */
public class StackOverFlowTest {
    private int stackDeep = 0;
    public void stackLeep(){
        stackDeep ++;
        stackLeep();
    }
    public static void main(String[] args) {
        StackOverFlowTest t = new StackOverFlowTest();
        try {
            t.stackLeep();
        }catch (Throwable e){
            System.out.println("Stack deep" + t.stackDeep);
            throw e;
        }
    }
}
