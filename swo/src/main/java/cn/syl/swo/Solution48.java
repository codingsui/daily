package cn.syl.swo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 剑指 Offer 48. 最长不含重复字符的子字符串
 *
 * 思请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class Solution48 {

    /**
     * 滑动窗口
     * head tail 指针移动
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        Set<Character> set = new HashSet<>();
        int max = 0;
        int head = 0,tail = 0;
        while (tail < s.length()) {
            char c = s.charAt(tail++);
            while (set.contains(c)){
                set.remove(s.charAt(head++));
            }
            set.add(c);
            max = Math.max(max,tail - head);
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        Map<Character,Integer> dic = new HashMap<>();
        int max = 0;
        int left = -1;
        for (int right = 0; right < s.length(); right++) {
            if (dic.containsKey(s.charAt(right))){
                left = Math.max(left,dic.get(right));
            }
            dic.put(s.charAt(right),right);
            max = Math.max(max,right-left);
        }
        return max;
    }
}
