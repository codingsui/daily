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
public class LotteryDraw implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userAccountId;

    private Integer lotteryDrawCount;

    private Date createTime;

    private Date updateTime;


}
