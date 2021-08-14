package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.Coupon;
import cn.syl.dt.ct.mapper.coupon.CouponMapper;
import cn.syl.dt.ct.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
@Service

public class CouponServiceImpl  implements ICouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public Coupon queryByUserAccountId(Long userAccountId) {
        return couponMapper.queryByUserAccountId(userAccountId);
    }

    @Override
    public void markCouponUsed(Long id) {
        couponMapper.updateStatus(id,2);
    }

    @Override
    public void insert(Coupon coupon) {
        couponMapper.insert(coupon);
    }


}
