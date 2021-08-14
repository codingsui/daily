package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.Credit;
import cn.syl.dt.ct.mapper.credit.CreditMapper;
import cn.syl.dt.ct.service.ICreditService;
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
@DS("ct-credit")
public class CreditServiceImpl extends ServiceImpl<CreditMapper, Credit> implements ICreditService {

    @Override
    public void increment(Long userAccountId, Double updatedPoint) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setSql("point = point + " + updatedPoint + " where user_account_id=+" + userAccountId);
        update(updateWrapper);
    }
}
