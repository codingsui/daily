package cn.syl.leetcode.dp.string.lis;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 *
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 *
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 *
 * 注意：不允许旋转信封。
 *
 *
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/dynamic-programming-1-plus/5okoej/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class 俄罗斯套娃信封问题 {

    /**
     * 如果将一个信封的宽度和长度揉合成一个变量a，那么就是比较变量a的大小，那么就变成了一个一唯数组找到最大上升子序列的问题了
     * @param envelopes
     * @return
     */
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes.length == 1){
            return 1;
        }
        Comparator<int[]> comparator = (a,b)->{
            if (a[0] > b[0] && a[1] > b[1]){
                return 1;
            }else {
                return 0;
            }
        };
        Arrays.sort(envelopes,(a,b)->a[0]-b[0]);
        int[] dp = new int[envelopes.length];
        int max = 1;
        dp[0] = 1;
        for (int i = 1; i < envelopes.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(envelopes[i],envelopes[j]) == 1){
                    dp[i] = Math.max(dp[i],dp[j] + 1);
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
