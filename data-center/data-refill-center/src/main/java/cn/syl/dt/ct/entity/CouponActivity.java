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
public class CouponActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long dataPackageId;

    private Double couponAmount;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
