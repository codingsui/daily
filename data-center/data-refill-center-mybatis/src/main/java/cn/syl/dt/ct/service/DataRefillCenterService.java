package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.RefillRequest;
import cn.syl.dt.ct.entity.RefillResponse;

public interface DataRefillCenterService {
    RefillResponse fill(RefillRequest refillRequest);
}
