package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.AccountAmount;
import cn.syl.dt.ct.mapper.finance.AccountAmountMapper;
import cn.syl.dt.ct.service.IAccountAmountService;
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
@DS("ct-finance")
public class AccountAmountServiceImpl extends ServiceImpl<AccountAmountMapper, AccountAmount> implements IAccountAmountService {
    @DS("ct-finance")
    @Override
    public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
        UpdateWrapper<AccountAmount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("accont_mount=accont_mount - " + amount + " where user_account_id =" + fromUserAccountId);
        update(updateWrapper);
        UpdateWrapper<AccountAmount> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.setSql("accont_mount=accont_mount + " + amount + " where user_account_id =" + toUserAccountId);
        update(updateWrapper1);
    }
}
