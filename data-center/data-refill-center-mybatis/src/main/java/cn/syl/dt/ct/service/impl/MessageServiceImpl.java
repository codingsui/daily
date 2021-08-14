package cn.syl.dt.ct.service.impl;


import cn.syl.dt.ct.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

	/**
	 * 发送短信
	 * @param phoneNumber 手机号码
	 * @param message 短信消息
	 */
	public void send(String phoneNumber, String message) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		log.info("给" + phoneNumber + "发送了一条短信：" + message);
	}
	
}
