package cn.syl.leetcode.erfen;

import org.junit.Test;

public class Solution167 {

    @Test
    public void test() {

    }

    public int[] twoSum(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i+1, j+1};
                }
            }
        }
        return null;
    }

    public int[] twoSum2(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int left = i;
            int right = numbers.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < target -numbers[i]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return null;
    }

    public int[] twoSum3(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int left = i;
            int right = numbers.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < target -numbers[i]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return null;
    }

    public int[] twoSum4(int[] numbers, int target) {
        for (int l = 0,r = numbers.length - 1; l < r;) {
            if (numbers[l] + numbers[r] == target) {
                return new int[]{l+1, r+1};
            }else if (numbers[l] + numbers[r] < target) {
                l++;
            } else {
                r--;
            }
        }
        return null;
    }
}
