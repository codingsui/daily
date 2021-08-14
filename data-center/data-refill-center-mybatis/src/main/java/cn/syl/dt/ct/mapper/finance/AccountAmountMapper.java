package cn.syl.dt.ct.mapper.finance;

import cn.syl.dt.ct.entity.AccountAmount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface AccountAmountMapper {


    @Update("UPDATE account_amount "
            + "SET accont_mount=accont_mount + #{updatedAmount} "
            + "WHERE user_account_id=#{userAccountId}")
    int updateAmount(@Param("userAccountId") Long userAccountId,
                 @Param("updatedAmount") Double updatedAmount);
}
