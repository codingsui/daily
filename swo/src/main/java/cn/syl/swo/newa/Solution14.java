package cn.syl.swo.newa;

/**
 *
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。请问 k[0]*k[1]*...*k[m-1]
 * 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 *
 *
 */
public class Solution14 {


    public int cuttingRope(int n) {
        if (n < 3){
            return n -1;
        }
        int[] dp = new int[n+1];
        dp[0]=dp[1]=0;
        for (int i = 2; i <= n;i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(Math.max(j*(i-j),j*dp[i-j]),dp[i]) ;
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(new Solution14().cuttingRope(10));
    }
}
