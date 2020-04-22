package cn.syl.leetcode.dp;

/**
 * 最长回文子串
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 解题：
 * 1.确定的状态
 * dp[i][j] 代表i到j的字符串是回文，true 否则fasle
 * 2.状态转移方程
 * d[i][j] = d[i+1]d[j-1] & S[i]== S[j]
 * 3.初始条件和边界值
 * d[i][i] = true;
 * 4.计算顺序
 *
 *
 *
 */
public class Solution1 {
    public String longestPalindrome(String s) {

        if (s == null || s.length() == 0){
            return s;
        }
        
        int start=0 ,end = 0;
        char[] array = s.toCharArray();
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int r = 1; r < s.length(); r++) {
            for (int l = 0; l < r; l++) {
                dp[l][r] = (r -l == 1 || dp[l+1][r-1])&& array[l] == array[r];
                System.out.println(l + " " + r + dp[l][r]);
                if (dp[l][r] && r - l > end - start){
                    start = l;
                    end = r;
                }

            }
            System.out.println("---");
        }
        return s.substring(start,end + 1);
    }
}