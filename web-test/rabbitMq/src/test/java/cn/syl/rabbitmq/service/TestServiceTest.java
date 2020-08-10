package cn.syl.rabbitmq.service;

import cn.syl.rabbitmq.RabbitMqStartAppltcation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitMqStartAppltcation.class)
public class TestServiceTest{

    @Autowired
    private TestService testService;

    @Test
    public void testSendTest() {
        testService.sendTest();
    }
}