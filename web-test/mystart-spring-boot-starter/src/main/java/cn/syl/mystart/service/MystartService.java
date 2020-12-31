package cn.syl.mystart.service;


public class MystartService {

    private String pre;

    private String sub;



    public MystartService(String pre, String sub) {
        this.pre = pre;
        this.sub = sub;
    }


    public String mystart(String word){
        return pre + word + sub;
    }
}
