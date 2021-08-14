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
public class PromotionActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dataPackageId;

    private Double discountPrice;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;


}
