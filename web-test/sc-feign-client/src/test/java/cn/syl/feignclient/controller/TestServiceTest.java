package cn.syl.feignclient.controller;

import cn.syl.feignclient.FeignClientAppltcation;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignClientAppltcation.class)
public class TestServiceTest{

    @Autowired
    private TestController testService;

    @Test
    public void testGet() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(testService.get(1));
        }

    }
}