package cn.syl.swo.newa;

/**
 * 剑指 Offer 11. 旋转数组的最小数字
 *
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，
 * 输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 *
 * 示例 1：
 *
 * 输入：[3,4,5,1,2]
 * 输出：1
 * 示例 2：
 *
 * 输入：[2,2,2,0,1]
 * 输出：0
 *
 *
 */
public class Solution11 {

    /**
     * 普通的遍历循环
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        if (numbers == null || numbers.length == 0){
            return -1;
        }
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (min > numbers[i]){
                min = numbers[i];
                break;
            }
        }
        return min;
    }

    /**
     * 二分法
     * @param numbers
     * @return
     */
    public int minArray2(int[] numbers) {
        if (numbers == null || numbers.length == 0){
            return -1;
        }
        int left = 0;
        int right = numbers.length - 1;
        int mid = 0;
        while (left < right){
            mid = (left + right) / 2;
            if (numbers[mid] < numbers[right]){
                right = mid;
            }else if (numbers[mid] > numbers[right]){
                left = mid + 1;
            }else {
                right--;
            }
        }

        return numbers[right];
    }

    public static void main(String[] args) {
        Solution11 s = new Solution11();
        int[] a = {3,4,5,1};
        System.out.println(s.minArray2(a));
    }
}
