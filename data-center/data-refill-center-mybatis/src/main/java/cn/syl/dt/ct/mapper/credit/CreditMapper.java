package cn.syl.dt.ct.mapper.credit;

import cn.syl.dt.ct.entity.Credit;
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
public interface CreditMapper {
    /**
     * 增加积分
     * @param userAccountId 用户账号id
     */
    @Update("UPDATE credit "
			+ "SET point = point + #{updatedPoint} "
            + "WHERE user_account_id=#{userAccountId}")
    void increment(@Param("userAccountId") Long userAccountId,
                   @Param("updatedPoint") Double updatedPoint);
}
