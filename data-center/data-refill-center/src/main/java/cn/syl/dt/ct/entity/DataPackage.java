package cn.syl.dt.ct.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    private Double price;

    private Long data;

    private Integer type;

    private String comment;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private PromotionActivity promotionActivity;

    @TableField(exist = false)
    private CouponActivity couponActivity;

}
