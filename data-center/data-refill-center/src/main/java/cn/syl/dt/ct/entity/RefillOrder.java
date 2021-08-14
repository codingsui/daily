package cn.syl.dt.ct.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RefillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private Long userAccountId;

    private Long businessAccountId;

    private String businessName;

    private Double amout;

    private String title;

    private String type;

    private Integer status;

    private Integer payType;

    private String refillComment;

    private String refillPhoneNumber;

    private Long refillData;

    private Double credit;

    private Date createTime;

    private Date updateTime;


}
