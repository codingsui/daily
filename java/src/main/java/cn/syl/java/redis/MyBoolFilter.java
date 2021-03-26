package cn.syl.java.redis;

import java.util.BitSet;
import java.util.Objects;

public class MyBoolFilter {

    private static final int DEFAULT_SIZE = 2 << 24;

    private BitSet bitSet = new BitSet(DEFAULT_SIZE);

    private static final int[] SEEDS = new int[]{3,13,46,71,91,134};

    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    public MyBoolFilter() {
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE,SEEDS[i]);
        }
    }

    public static void main(String[] args) {
        String a = "tom";
        String b = "jack";
        MyBoolFilter myBoolFilter = new MyBoolFilter();
        System.out.println(myBoolFilter.contains(a));
        System.out.println(myBoolFilter.contains(b));
        System.out.println(myBoolFilter.contains("tom"));
        myBoolFilter.add(a);
        myBoolFilter.add(b);
        System.out.println(myBoolFilter.contains(a));
        System.out.println(myBoolFilter.contains(b));
        System.out.println(myBoolFilter.contains("tom"));
    }


    private void add(Object value){
        for (SimpleHash item : func) {
            bitSet.set(item.hash(value),true);
        }
    }

    public boolean contains(Object value){
        boolean ret = true;
        for (SimpleHash item : func){
            ret = ret && bitSet.get(item.hash(value));
        }
        return ret;
    }

    private static class SimpleHash{

        private int cap;

        private int sed;

        public SimpleHash(int cap, int sed) {
            this.cap = cap;
            this.sed = sed;
        }



        public int hash(Object value) {
            int h;
            return value == null ? 0 : sed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16));
        }
    }
}
