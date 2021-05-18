package cn.syl.java.sort;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        // 只需要修改成对应的方法名就可以了
        insertionSort(array);

        System.out.println(Arrays.toString(array));
    }

    /**
     * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     * 最佳情况：T(n) = O(n) 最坏情况：T(n) = O(n2) 平均情况：T(n) = O(n2)
     * @param array
     */
    public static void insertionSort(int[] array) {
        int len = array.length;
        for (int i = 1; i < len; i++) {
            int min = array[i];
            int j=i;
            for (; j > 0 && min < array[j-1]; j--) {
                array[j] = array[j-1];
            }
            array[j] = min;
        }
    }
}
