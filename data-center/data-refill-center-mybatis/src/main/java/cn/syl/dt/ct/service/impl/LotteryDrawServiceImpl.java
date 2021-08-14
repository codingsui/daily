package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.LotteryDraw;
import cn.syl.dt.ct.mapper.lottery.LotteryDrawMapper;
import cn.syl.dt.ct.service.ILotteryDrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Service

public class LotteryDrawServiceImpl  implements ILotteryDrawService {

    @Autowired
    private LotteryDrawMapper lotteryDrawMapper;

    @Override
    public void increment(Long userAccountId) {
        lotteryDrawMapper.increment(userAccountId);
    }
}
