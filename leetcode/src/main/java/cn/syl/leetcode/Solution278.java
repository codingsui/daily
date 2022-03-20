package cn.syl.leetcode;

import java.util.HashMap;

/**
 *278. 第一个错误的版本
 *
 */
public class Solution278 {

    public int firstBadVersion(int n) {
        int l = 0;
        int r = n;
        while (l < r){
            int mid = l + (r - l) / 2;
            if (isBadVersion(mid)){
                r = mid;
            }else {
                l = mid + 1;
            }
        }
        return l;
    }

    boolean isBadVersion(int version){return false;}
}
