package cn.syl.java.jvm;

import org.junit.Test;

public class StringTest {

    @Test
    public void a() {
        String a = "Hello world";
        String b = "Hello " + "world";
        System.out.println(a == b);
    }

    @Test
    public void b() {
        String a = "Hello ";
        String b = "world";
        String c = "Hello world";
        String d = a + b;
        System.out.println(d == c);
        System.out.println(d.equals(c));
    }

    @Test
    public void c() {
        String a = new String("hello world");
        String a1 = a.intern();
        String b = "hello world";
        System.out.println(a == b);
        System.out.println(a1 == b);
        System.out.println(a1 == a);
    }
    @Test
    public void d() {
        String str1 = new String("SEU")+ new String("Calvin");
        System.out.println(str1.intern() == str1);
        System.out.println(str1 == "SEUCalvin");
    }

    public static void a1(String[] args){
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
    }

    @Test
    public void e(){
        String s = new String("1");
        String s1 = s.intern();
        String s2 = "1";
        System.out.println(s == s1);
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
    }

    @Test
    public void f(){
        String s3 = new String("1") + new String("1");
        String intern = s3.intern();
        String s4 = "11";
        System.out.println(s3==intern);
        System.out.println(s4==intern);
        System.out.println(s4==s3);
        System.out.println(System.getProperty("java.version"));
    }
    @Test
    public void g(){
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
        String s3 = new String("1") + new String("1");
        String intern = s3.intern();
        String s4 = "11";
        System.out.println(s3==intern);
        System.out.println(s4==intern);
        System.out.println(s4==s3);

    }
}
