package cn.syl.core.utils;

import java.util.UUID;

public class CoreUtils {

    public static String getUUId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
