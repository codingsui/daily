package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.LotteryDraw;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface ILotteryDrawService{

    /**
     * 增加一次抽奖次数
     * @param userAccountId 用户账号id
     */
    void increment(Long userAccountId);
}
