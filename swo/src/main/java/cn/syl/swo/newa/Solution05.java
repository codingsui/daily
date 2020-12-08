package cn.syl.swo.newa;

/**
 * 面试题05. 替换空格
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 */
public class Solution05 {
    public String replaceSpace(String s) {
        if (s == null || "".equals(s)){
            return s;
        }
        return s.replaceAll(" ","%20");
    }

    public  String replaceSpace2(String s) {
        if (s == null || "".equals(s)){
            return s;
        }
        char[] chars = s.toCharArray();
        char[] newc = new char[chars.length * 3];
        int j = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' '){
                newc[j++] = '%';
                newc[j++] = '2';
                newc[j++] = '0';
            }else {
                newc[j++] = chars[i];
            }
        }
        return new String(newc,0,j);
    }
}
