package cn.syl.java;

import java.util.Arrays;
import java.util.Comparator;

public class A {

    public static void main(String[] args) {
        Integer[] a = {5,4,2,3,1,11,22};
        Arrays.sort(a, Comparator.comparing(i -> i.equals(3)));
        System.out.println(Arrays.toString(a));

    }
}
