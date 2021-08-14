package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.CouponActivity;
import cn.syl.dt.ct.mapper.activity.CouponActivityMapper;
import cn.syl.dt.ct.service.ICouponActivityService;
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

public class CouponActivityServiceImpl  implements ICouponActivityService {

    @Autowired
    private CouponActivityMapper couponActivityMapper;

    @Override
    public CouponActivity queryByDataPackageId(Long dataPackageId) {
        return couponActivityMapper.selectOne(dataPackageId);
    }
}
