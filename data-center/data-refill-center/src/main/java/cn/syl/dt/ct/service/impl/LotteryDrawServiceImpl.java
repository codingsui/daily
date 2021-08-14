package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.LotteryDraw;
import cn.syl.dt.ct.mapper.lottery.LotteryDrawMapper;
import cn.syl.dt.ct.service.ILotteryDrawService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@DS("ct-lottery")
public class LotteryDrawServiceImpl extends ServiceImpl<LotteryDrawMapper, LotteryDraw> implements ILotteryDrawService {

    @Override
    public void increment(Long userAccountId) {
        UpdateWrapper<LotteryDraw> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("lottery_draw_count = lottery_draw_count + 1 where user_account_id = " + userAccountId);
        update(updateWrapper);
    }
}
