package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Solution57B {

    public static void main(String[] args) {

    }
    public int[][] findContinuousSequence(int target) {
        List<int[]> list = new ArrayList<>();
        int i = 1;
        int j = 2;
        int s = 3;
        while (i < j){
            if (s > target){
                s-=i;
                i++;
            }else if (s < target){
                j++;
                s+=j;
            }else {
                int[] arry = new int[j-i+1];
                int a = 0;
                for (int k = i; k <= j; k++) {
                    arry[a++] = k;
                }
                list.add(arry);
                s-=i;
                i++;
            }
        }
        return list.toArray(new int[0][]);
    }

}
