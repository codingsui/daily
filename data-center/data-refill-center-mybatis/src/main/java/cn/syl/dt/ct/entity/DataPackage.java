package cn.syl.dt.ct.entity;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class DataPackage implements Serializable {

    private static final long serialVersionUID = 1L;


    protected Long id;

    private Double price;

    private Long data;

    private Integer type;

    private String comment;

    private Date createTime;

    private Date updateTime;


    private PromotionActivity promotionActivity;


    private CouponActivity couponActivity;

}
