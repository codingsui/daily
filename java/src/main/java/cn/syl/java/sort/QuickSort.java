package cn.syl.java.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] array = {44,3, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
     * 最佳情况：T(n) = O(nlogn) 最差情况：T(n) = O(n2) 平均情况：T(n) = O(nlogn)
     * @param array
     */
    public static void quickSort(int[] array){
        quick(array,0,array.length-1);
    }

    private static void quick(int[] array, int left, int right) {
        if (left >= right){
            return;
        }
        int start = left;
        int end = right;
        int base = array[left];
        while (left < right){
            while (left < right && base < array[right]) {
                right--;
            }
            array[left] = array[right];
            while (left < right && base > array[left]) {
                left++;
            }
            array[right] = array[left];
            array[left] = base;
        }

        if (left - start > 1){
            quick(array,start,left);
        }
        if (end - right > 1){
            quick(array,right + 1,end);

        }

    }



}
