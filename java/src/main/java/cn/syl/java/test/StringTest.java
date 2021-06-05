package cn.syl.java.test;

public class StringTest {
    public static void main(String[] args) {
        String a1 = new String("abcdefg");
        String a2 = a1.intern();
        String a = "abcdefg";
        System.out.println(a == a1);//false
        System.out.println(a == a2);//true
        System.out.println(a1 == a2);//false

        String b = new String("天天");
        String b1 = b.intern();
        System.out.println(b == b1);//false
    }
}
