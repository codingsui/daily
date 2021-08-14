package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.LotteryDraw;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface ILotteryDrawService extends IService<LotteryDraw> {

    /**
     * 增加一次抽奖次数
     * @param userAccountId 用户账号id
     */
    void increment(Long userAccountId);
}
