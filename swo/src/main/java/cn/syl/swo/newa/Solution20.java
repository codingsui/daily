package cn.syl.swo.newa;

/**
 *
 *剑指 Offer 20. 表示数值的字符串
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、
 * "3.1416"、"-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
 *
 */
public class Solution20 {

    public boolean isNumber(String s) {
        if (s == null || s.length() == 0 || s.trim().length() == 0){
            return false;
        }
        char[] chars = s.trim().toCharArray();
        boolean isError = false;
        boolean isNum = false;
        boolean isPoint = false;
        boolean isE = false;
        int fu = -1;
        int e = -1;
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (isError){
                return false;
            }else if (current >= '0' && current <= '9'){
                isNum = true;
                continue;
            }else if (current == '.'){
                if (isPoint|| !isNum){
                    isError = true;
                    continue;
                }
                isPoint = true;
            }else if (current == 'e' || current == 'E'){
                if (isE || !isNum){
                    isError = true;
                    continue;
                }
                isNum = false;
                isE = true;
                e = i;
            }else if (current == '+' || current == '-'){
                if (!(isE && e == (i-1))){
                    return false;
                }
            }
        }
        if (!isNum || isError){
            return false;
        }
        return true;
    }

    public boolean a(String s){
        if (s == null || s.length() == 0) return false;
        //去掉首位空格
        s = s.trim();
        boolean numFlag = false;
        boolean dotFlag = false;
        boolean eFlag = false;
        for (int i = 0; i < s.length(); i++) {
            //判定为数字，则标记numFlag
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numFlag = true;
                //判定为.  需要没出现过.并且没出现过e
            } else if (s.charAt(i) == '.' && !dotFlag && !eFlag) {
                dotFlag = true;
                //判定为e，需要没出现过e，并且出过数字了
            } else if ((s.charAt(i) == 'e' || s.charAt(i) == 'E') && !eFlag && numFlag) {
                eFlag = true;
                numFlag = false;//为了避免123e这种请求，出现e之后就标志为false
                //判定为+-符号，只能出现在第一位或者紧接e后面
            } else if ((s.charAt(i) == '+' || s.charAt(i) == '-') && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {

                //其他情况，都是非法的
            } else {
                return false;
            }
        }
        return numFlag;
    }
    public static void main(String[] args) {
        System.out.println(new Solution20().isNumber(".1"));
    }
}
