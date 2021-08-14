package cn.syl.hystrix.service;

import cn.syl.hystrix.HystrixApplication;
import cn.syl.hystrix.commond.ProductCommand;
import cn.syl.hystrix.commond.ProductHystrixCollapser;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HystrixApplication.class)
public class ProductServiceTest{

    private RestTemplate restTemplate;

    @Before
    public void init(){
        restTemplate = new RestTemplate();
    }


    @Test
    public void testGetOne() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        for (int i = 0; i < 100; i++) {
            ProductCommand p = new ProductCommand((long) (i % 10));
            System.out.println(p.execute());
        }
        context.shutdown();

    }


    @Test
    public void testCollapes() {

        for (int i = 0; i < 100; i++) {
            ProductHystrixCollapser p = new ProductHystrixCollapser((long) (i % 10));
            p.execute();
        }

    }
}