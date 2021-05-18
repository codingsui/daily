package cn.syl.swo.newa;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 剑指 Offer 50. 第一个只出现一次的字符
 * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
 *
 */
public class Solution50 {

    public static void main(String[] args) {
        System.out.println(new Solution50().firstUniqChar(""));
    }




    public char firstUniqChar(String s) {
        char tes = ' ';
        if (s == null || s.length() == 0){
            return tes;
        }
        Map<Character,Integer> treeMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            if (treeMap.containsKey(a)){
                int z = treeMap.get(a)+1;
                treeMap.put(a,z);
            }else {
                treeMap.put(a,1);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            if (treeMap.get(s.charAt(i)) == 1){
                return s.charAt(i);
            }
        }
        return tes;
    }
}
