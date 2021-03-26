package cn.syl.java.sort;


/**
 * 快排核心思路，找到一个基数，然后将数组中大于基数的放在基数右边，小于基数的放基数左边
 * 之后用分治思想 处理基数左边的和基数右边的
 */
public class QuickTest {

    public static void main(String[] args) {
        int[] a = {5,11,100,11,11,3,11,22};
        QuickTest.sort(a);
        for (int item:a) {
            System.out.println(item);
        }
    }



    private static void sort(int[] a) {
        if (a == null || a.length == 0){
            return;
        }
        quickSort3(a,0,a.length-1);
    }

    /**
     * 建议使用
     * @param a
     * @param l
     * @param r
     */
    private static void quickSort3(int[] a, int l, int r) {
        if (l >= r){
            return;
        }
        int low = l,high = r;
        int tmp = a[l];
        while (l < r){
            while (l < r && a[r] >= tmp){
                r--;
            }
            a[l] = a[r];
            while (l < r && a[l] <= tmp){
                l++;
            }
            a[r] = a[l];
            a[l] = tmp;
        }

        quickSort3(a,low,l-1);
        quickSort3(a,r+1,high);
    }

    /**
     * 逻辑有点绕
     * @param strs
     * @param l
     * @param r
     */
    static void quickSort2(int[] strs, int l, int r) {
        if(l >= r) return;
        int i = l, j = r;
        int tmp = strs[i];
        while(i < j) {
            while(strs[j] > strs[i] && i < j) j--;
            while(strs[i] < strs[j] && i < j) i++;
            tmp = strs[i];
            strs[i] = strs[j];
            strs[j] = tmp;
        }
        strs[i] = strs[l];
        strs[l] = tmp;
        quickSort2(strs, l, i - 1);
        quickSort2(strs, i + 1, r);
    }



        static void quickSort(int[] a,int l,int r){
        int low = l,high = r,index = l;
        int base = a[l];
        while (l < r){
            while (l < r && a[r] > base){
                r--;
            }
            a[l] = a[r];
            index = r;
            while (l < r && a[l] < base){
                l++;
            }
            a[r] = a[l];
            index = l;
            a[index] = base;
        }
        if (l - low > 1){
            quickSort(a,low,l-1);
        }
        if (high - r > 1){
            quickSort(a,r+1,high);
        }
    }
}
