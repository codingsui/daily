package cn.syl.leetcode.竞赛.ltc287;

public class Ltc2180 {

    public int countEven(int num) {
        int c = 0;
        for (int i = 1; i <= num; i++) {
            int a = i, sum = 0;
            while (a > 0){
                sum += a % 10;
                a /= 10;
            }
            if (sum % 2 == 0){
                c++;
            }
        }
        return c;
    }
}
