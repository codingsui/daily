package cn.syl.dt.ct.service;

public interface ThirdPartyService {
    /**
     * 充值流量
     * @param phoneNumber 手机号
     * @param data 流量
     */
    void refillData(String phoneNumber, Long data);
}
