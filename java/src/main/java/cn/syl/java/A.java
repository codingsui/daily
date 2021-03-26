package cn.syl.java;

import java.util.ArrayList;
import java.util.List;

public class A {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("ab");
        list.add("ac");
        list.add("ad");
        list.stream().filter(item->{
            if (item.equals("a")){
                return true;
            }
            return false;
        }).forEach(item->{
            System.out.println(item);
        });

    }
}
