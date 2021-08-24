package cn.syl.java;

import java.util.Arrays;

public class Test1 {
    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        System.out.println(Arrays.toString(test1.search(new int[]{1,3,5,7},12)));;

    }


    public int[] search(int[] array,int target){
        if (array == null || array.length == 0){
            return new int[]{};
        }
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                if (array[i] + array[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{};
    }

    public int[] search2(int[] array,int target){
        if (array == null || array.length == 0){
            return new int[]{};
        }
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                if (array[i] + array[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{};
    }

}
