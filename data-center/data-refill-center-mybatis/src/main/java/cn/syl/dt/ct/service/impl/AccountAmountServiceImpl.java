package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.AccountAmount;
import cn.syl.dt.ct.mapper.finance.AccountAmountMapper;
import cn.syl.dt.ct.service.IAccountAmountService;
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

public class AccountAmountServiceImpl  implements IAccountAmountService {

    @Autowired
    private AccountAmountMapper accountAmountMapper;

    @Override
    public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
        accountAmountMapper.updateAmount(fromUserAccountId,-amount);
        accountAmountMapper.updateAmount(toUserAccountId,amount);
    }
}
