package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution19 {

    public boolean isMatch(String s, String p) {
        if (s == null || p == null){
            return false;
        }
        char[] chars = s.toCharArray();
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == p.charAt(k) || p.charAt(k) == '.'){
                k++;
            }else if (p.charAt(k) == '*'){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }
}
