package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution58 {

    public String reverseWords(String s) {
        String[] res = s.trim().split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = res.length-1; i >= 0; i--) {
            if (res[i].equals("")){
                continue;
            }
            sb.append(res[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
