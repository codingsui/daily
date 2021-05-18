package cn.syl.java.sort;

import java.util.Arrays;

/**
 * T(n) = O(n) 最差情况：T(n) = O(n2) 平均情况：T(n) = O(n2)
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {5,4,3,2,1};
//        int[] array = {1,2,3,4,5};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));;
    }

    /**
     * 比较相邻的元素。如果第一个比第二个大，就交换它们两个
     * @param array
     */
    public static void bubbleSort(int[] array){
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len-1-i; j++) {
                if (array[j] > array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

}
