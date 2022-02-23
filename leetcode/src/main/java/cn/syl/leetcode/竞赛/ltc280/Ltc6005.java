package cn.syl.leetcode.竞赛.ltc287;

import java.util.*;

public class Ltc6005 {
    public int minimumOperations(int[] nums) {
        if (nums.length == 1){
            return 0;
        }
        if ( nums.length == 2){
            if (nums[0] == nums[1]){
                return 1;
            }else {
                return 0;
            }
        }
        int c1,c2;
        if (nums.length % 2 == 0){
            c1 = c2 = nums.length / 2;
        }else {
            c1 = nums.length / 2 + 1;
            c2 = nums.length - c1;
        }
        Map<Integer,Integer> map1 = new HashMap<>();//从0开始
        Map<Integer,Integer> map2 = new HashMap<>();//从1开始
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (i % 2 == 0){
                if (map1.get(num) == null){
                    map1.put(num,1);
                }else {
                    map1.put(num, map1.get(num) + 1);
                }
            }else {
                if (map2.get(num) == null){
                    map2.put(num,1);
                }else {
                    map2.put(num, map2.get(num) + 1);
                }
            }
        }
        int res = 0;
        List<Map.Entry<Integer, Integer>> sort1 = getSort(map1);
        List<Map.Entry<Integer, Integer>> sort2 = getSort(map2);
        if (sort1.get(0).getKey() == sort2.get(0).getKey()) {
            if (sort1.get(0).getValue() > sort2.get(0).getValue()) {
                res = c1 - sort1.get(0).getValue() + c2;
            }else {
                res = c2 - sort2.get(0).getValue() + c1;
            }
        }else {
            if (sort1.get(0).getValue() > sort2.get(0).getValue()) {
                res = c1 - sort1.get(0).getValue() + c2-sort2.get(0).getValue() ;
            }else {
                res = c2 - sort2.get(0).getValue() + c1-sort1.get(0).getValue()  ;
            }
        }
        return res;
    }

    public List<Map.Entry<Integer, Integer>> getSort(Map<Integer,Integer> map) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

    public static void main(String[] args) {

//        System.out.println(new Ltc6005().minimumOperations(new int[]{48,38,42,18,13,1,97,88,82,48,54,16,78,59,52,30,40,77,59,87,71,28}));
        System.out.println(new Ltc6005().minimumOperations(new int[]{1,2,2,2,2}));
    }
}
