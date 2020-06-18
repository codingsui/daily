package cn.syl.swo;

import java.util.HashSet;
import java.util.Set;

/**
 * 面试题50
 *
 * 思路1：
 * 滑动窗口，记录窗口的大小，从中获取最大的
 *
 */
public class Solution48 {

    public int lengthOfLongestSubstring(String s) {

        if (s == null || s.length() ==0){
            return 0;
        }
        Set<Character> set = new HashSet<>();
        int max = 0;

        for (int l = 0,r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            if (set.contains(c)){

            }else{

            }
        }
        return -1;
    }
}
