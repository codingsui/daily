package cn.syl.java.sort;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {5,4,3,2,1};
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }
    /**
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，
     * 然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     *
     * 最佳情况：T(n) = O(n2) 最差情况：T(n) = O(n2) 平均情况：T(n) = O(n2)
     */
    public static void selectionSort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            for (int j = i+1; j < len; j++) {
                if (array[minIndex] > array[j]){
                    minIndex = j;
                }
            }
            if (i != minIndex){
                int tmp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = tmp;
            }
        }
    }
}
