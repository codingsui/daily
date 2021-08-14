package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.CouponActivity;
import cn.syl.dt.ct.mapper.activity.CouponActivityMapper;
import cn.syl.dt.ct.service.ICouponActivityService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@DS("ct-activity")
public class CouponActivityServiceImpl extends ServiceImpl<CouponActivityMapper, CouponActivity> implements ICouponActivityService {


    @Override
    public CouponActivity queryByDataPackageId(Long dataPackageId) {
        return getOne(Wrappers.<CouponActivity>lambdaQuery().eq(CouponActivity::getDataPackageId,dataPackageId).eq(CouponActivity::getStatus,2));
    }
}
