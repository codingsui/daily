package cn.syl.java.operate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("htmk","html","aaa");
        Filter htmlFilter = new HtmlFilter();
        htmlFilter.filter(list).stream().forEach(System.out::println);
    }
}

interface Filter<T>{
    List<T> filter(List<T> list);
}

class HtmlFilter implements Filter<String>{

    @Override
    public List<String> filter(List<String> list) {
        if (list == null || list.size() == 0){
            return null;
        }else {
            return list.stream().filter(item->!item.contains("html")).collect(Collectors.toList());
        }
    }
}