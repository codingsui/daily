package cn.syl.leetcode;

public class Solution367 {

    public boolean isPerfectSquare(int num) {
        int left = 1;
        int right = num / 2 + 1;
        while (left <= right) {
            int mid = left + (right - left) /2;
            long s = (long) mid * mid;
            if (s > num) {
                right = mid - 1;
            } else if (s < num) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new Solution367().isPerfectSquare(2147483647));
    }
}
