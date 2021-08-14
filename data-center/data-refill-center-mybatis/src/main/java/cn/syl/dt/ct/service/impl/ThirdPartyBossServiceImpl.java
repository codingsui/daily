package cn.syl.dt.ct.service.impl;


import cn.syl.dt.ct.service.ThirdPartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThirdPartyBossServiceImpl implements ThirdPartyService {

	/**
	 * 充值流量
	 * @param phoneNumber 手机号
	 * @param data 流量
	 */
	public void refillData(String phoneNumber, Long data) {
		try {
			Thread.sleep(500); 
			log.info("已经完成为" + phoneNumber + "充值" + data + "MB流量");
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
}
