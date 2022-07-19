package cn.syl.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class Solution744 {

    @Test
    public void test() {
        Assert.assertEquals('j' , new Solution744().nextGreatestLetter(new char[]{'c','f','j'}, 'j'));
    }

    public char nextGreatestLetter(char[] letters, char target) {

        int left = 0;
        int right = letters.length-1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (letters[mid] < target) {
                left = mid + 1;
            } else if (letters[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left == letters.length ? letters[0] :letters[left];
    }
}
