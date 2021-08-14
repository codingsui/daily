package cn.syl.java.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = {44,3, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        // 只需要修改成对应的方法名就可以了
        mergeSort(array);

        System.out.println(Arrays.toString(array));
    }


    /**
     * 最佳情况：T(n) = O(n) 最差情况：T(n) = O(nlogn) 平均情况：T(n) = O(nlogn)
     *
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）
     * 的一个非常典型的应用。归并排序是一种稳定的排序方法。将已有序的子序列合并，得到完全有序的序列；
     * 即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
     *
     * @param array
     */
    public static void mergeSort(int[] array) {
        if (array == null || array.length == 0){
            return;
        }
        int[] tmp = new int[array.length];
        merge(array,tmp,0,array.length-1);
    }


    private static void merge(int[] array, int[] tmp, int left, int right) {
        if (left < right){
            int mid = (left + right) /2;
            merge(array,tmp,left,mid);
            merge(array,tmp,mid+1,right);
            sort(array,tmp,left,mid,right);
        }
    }

    private static void sort(int[] array, int[] tmp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i<=mid && j <= right){
            if (array[i]<array[j]){
                tmp[index++] = array[i++];
            }else {
                tmp[index++] = array[j++];
            }
        }
        while (i<=mid){
            tmp[index++] = array[i++];
        }
        while (j<=right){
            tmp[index++] = array[j++];
        }
        index = 0;
        while (left <= right){
           array[left++] = tmp[index++];
        }
    }
}
