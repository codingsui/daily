package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class Solution62 {
    public static void main(String[] args) {
        System.out.println(new Solution62().lastRemaining(5,3));
    }

    public int lastRemaining(int n, int m) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int idx = 0;
        while (n > 1){
            idx = (idx + m - 1) % n ;
            list.remove(idx);
            n--;
        }
        return list.get(0);
    }
}
