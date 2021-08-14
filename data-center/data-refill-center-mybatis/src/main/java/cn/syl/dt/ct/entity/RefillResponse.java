package cn.syl.dt.ct.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
public class RefillResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Date createTime;

    private Date updateTime;


}
