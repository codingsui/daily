package cn.syl.rabbitmq.service;

import cn.syl.rabbitmq.RabbitMqStartAppltcation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitMqStartAppltcation.class)
public class TestServiceTest{

    @Autowired
    private TestService testService;

    @Test
    public void testSendTest() {
        testService.sendTest();
    }

    @Test
    public void testSendFanout() {
        testService.sendFanout();
    }

    @Test
    public void testSendTopic() {
        testService.sendTopic();
    }

    @Test
    public void testconfirmQueue() {
        String msg = "2";
        testService.confirmQueue(msg);
        try {
            TimeUnit.SECONDS.sleep(235);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testmoreQueue() {
        testService.moreQueue();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}