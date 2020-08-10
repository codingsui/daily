package cn.syl.java.test;

public class Intert {
    public static void main(String[] args) {
        String str2 = new StringBuffer("天青色").append("等烟雨").toString();
        System.out.println(str2.intern() == str2);
        String str3 = "天青色等烟雨";
        System.out.println(str3 == str2);

        String str1 = new StringBuffer("jav").append("a").toString();
        System.out.println(str1.intern() == str1);
    }
}
