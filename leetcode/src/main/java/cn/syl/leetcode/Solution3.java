package cn.syl.leetcode;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 *
 */
public class Solution3 {

    /**
     * dp[i][j]=dp[i+1][j-1] & s[i] ==
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        HashMap<Character,Integer> map = new HashMap<>();
        int n = s.length();
        int res = 0;
        for (int start = 0,end = 0; end < n; end++) {
            if (map.containsKey(s.charAt(end))){
                start = Math.max(map.get(s.charAt(end)) + 1,start);
            }
            res = Math.max(res,end-start+1);
            map.put(s.charAt(end),end);
        }
        return res;
    }

    public static void main(String[] args) {
        String a ="abba";
        System.out.println(new Solution3().lengthOfLongestSubstring(a));
    }
}
