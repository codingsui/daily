package cn.syl.leetcode.竞赛.ltc280;

public class One {


    public int countOperations(int num1, int num2) {

        return dg(num1, num2, 0);
    }

    private int dg(int num1, int num2, int c) {
        if (num1 == 0 || num2 == 0){
            return c;
        }
        if (num1 == num2){
            return c + 1;
        }
        if (num1 < num2){
            return dg(num1, num2 - num1, c + 1);
        }else {
            return dg(num1 - num2, num2, c + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new One().countOperations(2,3));
    }
}
