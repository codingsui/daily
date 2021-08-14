package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.Credit;
import cn.syl.dt.ct.mapper.credit.CreditMapper;
import cn.syl.dt.ct.service.ICreditService;
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

public class CreditServiceImpl  implements ICreditService {

    @Autowired
    private CreditMapper creditMapper;

    @Override
    public void increment(Long userAccountId, Double updatedPoint) {
        creditMapper.increment(userAccountId,updatedPoint);
    }
}
