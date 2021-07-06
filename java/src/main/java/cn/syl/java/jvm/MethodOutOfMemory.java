package cn.syl.java.jvm;

import java.util.ArrayList;
import java.util.List;

public class MethodOutOfMemory {
    public static void main(String[] args) {

        String a = new String("hello") + new String("world");
    }
}
