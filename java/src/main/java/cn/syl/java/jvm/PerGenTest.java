package cn.syl.java.jvm;

import java.util.HashSet;
import java.util.Set;

/**
 * 1.6:-XX:PermSize6M -XX:MaxPermSize6M
 * 1。8-XX:MetaspaceSize=40M -XX:MaxMetaspaceSize=50M
 */
public class PerGenTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int i = 0;
        while (true){
            set.add(String.valueOf(i++).intern());
        }
    }
}
