package cn.syl.swo.newa;

import org.junit.Assert;

/**
 *
 *
 */
public class Solution64 {

    public int sumNums(int n) {
        boolean x = n > 1 && (n+= sumNums(n - 1))> 0;
        return n;
    }

    public static void main(String[] args) {
        Solution64 s = new Solution64();
//        System.out.println(s.sumNums(1));
        System.out.println(s.sumNums(2));
        System.out.println(s.sumNums(3));
        System.out.println(s.sumNums(4));

    }
}
