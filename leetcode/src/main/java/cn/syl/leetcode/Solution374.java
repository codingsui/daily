package cn.syl.leetcode;


/**
 * 374. 猜数字大小
 */
public class Solution374 {
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int guss = guess(mid);
            if (guss == -1) {
                right = mid - 1;
            } else if (guss == 1) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private int guess(int i) {
        return -1;
    }
}
