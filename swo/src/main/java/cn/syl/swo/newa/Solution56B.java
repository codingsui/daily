package cn.syl.swo.newa;

/**
 *
 */
public class Solution56B {

    public int singleNumber(int[] nums) {
        int[] res = new int[32];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[j] += nums[i] & 1;
                nums[i] >>>= 1;
            }
        }
        int r = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < res.length; i++) {
            r <<= 1;
            r |= res[31-i] % 3;
        }
        return r;
    }

    public static void main(String[] args) {
        int[] a = {3,4,3,3};
        System.out.println(new Solution56B().singleNumber(a) );
        String b = "1001";
        int r = 0;
        for (int i = 0; i < b.length(); i++) {
            r <<= 1;
            r|=Integer.parseInt(String.valueOf(b.charAt((b.length() -1) - i))) ;
        }
        System.out.println(r);
    }

}
