package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution60 {
    public double[] dicesProbability2(int n) {
        double[] dp = new double[6];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = (double) 1 / 6;
        }
        /*
            由于
            dp[i][j] += dp[1][k] * dp[i-1][j-k];
            方程可以发现，
            dp[i-1][j] 其实只与 dp[i][j+(1~6)]有关
            就可以由dp[i-1]推出dp[i]了
         */
        for (int i = 2; i <= n; i++) {
            double[] tmp = new double[5 * i + 1];
            for (int j = 0; j < dp.length; j++) {
                for (int k = 0; k < 6; k++) {
                    tmp[j+k] += dp[j] / 6.0;
                }
            }
            dp = tmp;
        }
        return dp;
    }

    public double[] dicesProbability(int n) {
        //动态规划 定义状态转移方程，dp[i][j] 表示 i个骰子掷出和为j的概率
        double[][] dp = new double[n + 1][6*n + 1];
        //初始化1个骰子情况
        for (int i = 1; i < 7; i++) {
            dp[1][i] = (double) 1 / 6;
        }
        for (int i = 2; i <= n; i++) {
            //当前骰子可以掷出的最大和
            int maxValue = 6 * i;
            for (int j = i; j <= maxValue; j++) {
                for (int k = 1; k <= 6; k++) {
                    /*
                      状态转移方程推导
                      i个骰子，掷出和为j的可能=（1个骰子，掷出1～6的可能）* （有i-1个骰子，掷出和为j-（1～6）的可能）
                                          dp[i][j] += dp[1][k] * dp[i-1][j-k];
                     */
                    if (j - k < 0){
                        continue;
                    }
                    dp[i][j] += dp[1][k] * dp[i-1][j-k];
                }
            }
        }
        double[] res = new double[5*n+1];
        int value = n;
        for (int i = 0; i < res.length; i++) {
            res[i] = dp[n][value++];
        }
        return res;
    }
}





