package cn.syl.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20M -Xmx20M
 */
public class HeapOomTest {
    static class OOMObject{}
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }

    }
}
