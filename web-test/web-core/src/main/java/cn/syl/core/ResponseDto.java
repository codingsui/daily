package cn.syl.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseDto implements Serializable {
    private int code;

    private String msg;

    private Object body;

    public ResponseDto() {
    }

    public ResponseDto(String msg, Object body) {
        this.msg = msg;
        this.body = body;
    }

    public ResponseDto(int code, String msg, Object body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static ResponseDto ok(){
        return ResponseDto.ok(null);
    }
    public static ResponseDto ok(Object body){
        return ResponseDto.ok("",body);
    }
    public static ResponseDto ok(String msg,Object body){
        return new ResponseDto(200,msg,body);
    }


}
