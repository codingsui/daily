package cn.syl.dt.ct.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class RefillRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userAccountId;

    private Long businessAccountId;

    private String businessName;

    private Double payAmount;

    private Integer payType;

    private String phoneNumber;

    private Long data;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private Coupon coupon;

    @TableField(exist = false)
    private DataPackage dataPackage;

}
