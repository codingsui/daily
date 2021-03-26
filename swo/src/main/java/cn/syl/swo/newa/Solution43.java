package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution43 {
    public int countDigitOne(int n) {
        int low = 0;
        int high = n /10;
        int cur = n % 10;
        int res = 0;
        int dit = 1;
        while (high != 0 || cur != 0){
            if (cur == 0){
                res += high * dit;
            }else if (cur == 1){
                res += high * dit + low + 1;
            }else{
                res += high * dit + dit;
            }
            low += dit * cur;
            cur = high % 10;
            high /= 10;
            dit *= 10;
        }
        return res;
    }
        /**
         * 自己写的方法
         * @param n
         * @return
         */
    public int countDigitOne2(int n) {
        int pre = 0;
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = pre + count(i);
            pre = res;
        }
        return res;
    }

    private int count(int i){
        int res = 0;
        while (i != 0){
            res += i % 10 == 1 ? 1 : 0;
            i = i / 10;
        }
        return res;
    }

}
