package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.PromotionActivity;
import cn.syl.dt.ct.mapper.activity.PromotionActivityMapper;
import cn.syl.dt.ct.service.IPromotionActivityService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@DS("ct-activity")
public class PromotionActivityServiceImpl extends ServiceImpl<PromotionActivityMapper, PromotionActivity> implements IPromotionActivityService {


    /**
     * 优惠活动mapper组件
     */
    @Autowired
    private PromotionActivityMapper promotionActivityMapper;

    /**
     * 查询流量套餐绑定的状态处于"进行中"的优惠活动
     * @return 优惠活动
     */
    public PromotionActivity queryByDataPackageId(Long dataPackageId) {
        return promotionActivityMapper.selectOne(Wrappers.<PromotionActivity>lambdaQuery().eq(PromotionActivity::getDataPackageId, dataPackageId).eq(PromotionActivity::getStatus, 2));
    }
}
