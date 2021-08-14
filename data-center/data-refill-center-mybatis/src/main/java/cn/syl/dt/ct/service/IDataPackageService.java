package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.DataPackage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface IDataPackageService extends IService<DataPackage> {

    /**
     * 查询所有的流量套餐
     * @return 流量套餐
     */
    List<DataPackage> queryAll();
}
