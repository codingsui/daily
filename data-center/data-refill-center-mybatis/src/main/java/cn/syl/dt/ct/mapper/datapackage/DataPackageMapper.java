package cn.syl.dt.ct.mapper.datapackage;

import cn.syl.dt.ct.entity.DataPackage;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface DataPackageMapper {
    /**
     * 查询所有的流量套餐
     * @return 流量套餐
     */
    @Select("SELECT * FROM data_package")
    @Results({
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "modified_time", property = "modifiedTime")
    })
    List<DataPackage> queryAll();
}
