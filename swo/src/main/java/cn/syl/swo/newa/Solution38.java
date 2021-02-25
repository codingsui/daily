package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 *剑指 Offer 38. 字符串的排列
 * 输入一个字符串，打印出该字符串中字符的所有排列。
 *
 *
 *
 * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
 */
public class Solution38 {

    /**
     * 深度优先搜索+剪枝
     * @param s
     * @return
     */
    private  char[] c ;
    private List<String> res;
    public String[] permutation(String s) {
        if (s == null || s.length() == 0){
            return null;
        }
        c = s.toCharArray();
        res = new ArrayList<>();
        dfs(0);
        return res.toArray(new String[res.size()]);
    }

    private void dfs(int path) {
        if (path == c.length - 1){
            res.add(String.valueOf(c));
            return;
        }
        HashSet<Character> set = new HashSet<>();
        for (int i = path; i < c.length; i++) {
            if (set.contains(c[i])){
                continue;
            }
            set.add(c[i]);
            swap(i,path);
            dfs(path + 1);
            swap(i,path);
        }
    }

    private void swap(int i, int path) {
        char tmp = c[i];
        c[i] = c[path];
        c[path] = tmp;
    }
}
