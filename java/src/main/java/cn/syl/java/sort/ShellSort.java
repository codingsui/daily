package cn.syl.java.sort;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        // 只需要修改成对应的方法名就可以了
        shellSort(array);

        System.out.println(Arrays.toString(array));
    }

    /**
     * 希尔排序是把记录按下表的一定增量分组，对每组使用直接插入排序算法排序；
     * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
     *
     * 最佳情况：T(n) = O(nlog2 n) 最坏情况：T(n) = O(nlog2 n) 平均情况：T(n) =O(nlog2n)
     * @param array
     */
    public static void shellSort(int[] array) {
        int len = array.length;
        int gap = len / 2;
        while (gap > 0){
            for (int i = gap; i < len; i+=gap) {
                int min = array[i];
                int j = i;
                for (; j >0 && min < array[j-1]; j-=gap) {
                    array[j] = array[j-1];
                }
                array[j] = min;
            }
            gap = gap/2;
        }
    }

}
