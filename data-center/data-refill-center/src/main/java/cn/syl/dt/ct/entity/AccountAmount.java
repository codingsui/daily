package cn.syl.dt.ct.entity;

import java.util.Date;
import java.io.Serializable;
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
public class AccountAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userAccountId;

    private Double accontMount;

    private Date updateTime;

    private Date createTime;


}
