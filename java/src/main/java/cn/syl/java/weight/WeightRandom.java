package cn.syl.java.weight;

import javafx.util.Pair;

import java.util.*;

public class WeightRandom <K,V extends Number>{

    public static void main(String[] args) {
        List<Pair<String,Integer>> list = new ArrayList<>();
        Pair<String,Integer> pair = new Pair<>("A",1);
        Pair<String,Integer> pair1 = new Pair<>("B",2);
        Pair<String,Integer> pair2 = new Pair<>("C",3);
        Pair<String,Integer> pair3 = new Pair<>("D",4);
        list.add(pair);
        list.add(pair1);
        list.add(pair2);
        list.add(pair3);
        WeightRandom<String,Integer> random = new WeightRandom<>(list);
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String k = (String) random.random();
            if (map.containsKey(k)){
                Integer a = map.get(k);
                a++;
                map.put(k,a);
            }else {
                map.put(k,1);
            }
        }
        System.out.println(map);
    }
    private TreeMap<Double, K> weightMap = new TreeMap<Double, K>();

    public WeightRandom(List<Pair<K, V>> list) {
        for (Pair<K, V> pair : list) {
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey().doubleValue();//统一转为double
            this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
        }
    }

    public K random() {
        double randomWeight = this.weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }

}
