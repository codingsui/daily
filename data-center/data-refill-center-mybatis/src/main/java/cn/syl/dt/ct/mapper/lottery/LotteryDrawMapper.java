package cn.syl.dt.ct.mapper.lottery;

import cn.syl.dt.ct.entity.LotteryDraw;
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
public interface LotteryDrawMapper {
    /**
     * 增加一次抽奖次数
     * @param userAccountId 用户账号id
     */
    @Update("UPDATE lottery_draw "
            + "SET lottery_draw_count = lottery_draw_count + 1 "
            + "WHERE user_account_id=#{userAccountId}")
    void increment(@Param("userAccountId") Long userAccountId);
}
