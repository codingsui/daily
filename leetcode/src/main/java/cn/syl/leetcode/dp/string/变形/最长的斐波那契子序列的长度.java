package cn.syl.leetcode.dp.string.变形;

import java.util.HashMap;
import java.util.Map;

public class 最长的斐波那契子序列的长度 {
    /**
     * 暴力
     * @param A
     * @return
     */
    public int lenLongestFibSubseq(int[] A) {
        if (A.length < 3){
            return 0;
        }
        //A[i] + A[j]= A[k]
        int max = 2;
        for (int i = 0; i < A.length; i++) {
            for (int j = i+1; j < A.length; j++) {
                int cur = 2;
                int cu_i = i;
                int cu_j = j;
                for (int k = j+1; k < A.length; k++) {
                    if (A[cu_i] + A[cu_j] == A[k]){
                        cur++;
                        cu_i=cu_j;
                        cu_j=k;
                    }
                }
                max = Math.max(max,cur);
            }
        }
        return max > 2 ? max : 0;
    }

    /**
     * 暴力优化
     * @param A
     * @return
     */
    public int lenLongestFibSubseq2(int[] A) {
        if (A.length < 3){
            return 0;
        }
        //A[i] + A[j]= A[k]
        Map<Integer,Integer> index = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            index.put(A[i],i);
        }
        int max = 2;
        for (int i = 0; i < A.length; i++) {
            for (int j = i+1; j < A.length; j++) {
                int cur = 2;
                int cu_i = i;
                int cu_j = j;
                while (index.containsKey(A[cu_i] + A[cu_j])){
                    int cu = index.get(A[cu_i] + A[cu_j]);
                    cu_i = cu_j;
                    cu_j = cu;
                    cur++;
                }
                max = Math.max(max,cur);
            }
        }
        return max > 2 ? max : 0;
    }
    public static void main(String[] args) {
        System.out.println(new 最长的斐波那契子序列的长度().lenLongestFibSubseq(new int[]{1,2,3,4,5,6,7,8}));
    }
}
