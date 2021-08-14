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
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userAccountId;

    private Double couponAmount;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;


}
