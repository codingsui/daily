package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.DataPackage;
import cn.syl.dt.ct.mapper.datapackage.DataPackageMapper;
import cn.syl.dt.ct.service.ICouponActivityService;
import cn.syl.dt.ct.service.IDataPackageService;
import cn.syl.dt.ct.service.IPromotionActivityService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
@Service
@Slf4j
@DS("ct-package")
public class DataPackageServiceImpl extends ServiceImpl<DataPackageMapper, DataPackage> implements IDataPackageService {

    @Autowired
    private ICouponActivityService couponActivityService;

    @Autowired
    private IPromotionActivityService promotionActivityService;

    @Override
    public List<DataPackage> queryAll() {
        log.info("12312");
        List<DataPackage> list = list();
        if (CollectionUtils.isEmpty(list)){
            return Lists.emptyList();
        }
        list.stream().forEach(item->{
            item.setCouponActivity(couponActivityService.queryByDataPackageId(item.getId()));
            item.setPromotionActivity(promotionActivityService.queryByDataPackageId(item.getId()));
        });
        return list;
    }

}
