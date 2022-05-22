package cn.syl.leetcode;

/**
 * 852. 山脉数组的峰顶索引
 */
public class Solution852 {

    public int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) /2;
            if (arr[mid] > arr[mid + 1]) {
                right = mid - 1;
            } else if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            }
        }
        return left;
    }

    public int peakIndexInMountainArray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) /2;
            if (arr[mid] > arr[mid + 1]) {
                right = mid;
            } else if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            }
        }
        return left;
    }
}
