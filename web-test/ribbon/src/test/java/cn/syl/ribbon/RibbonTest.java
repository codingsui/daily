package cn.syl.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.client.http.RestClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RibbonTest {

    @Test
    public void test() throws Exception {
        ConfigurationManager.getConfigInstance().setProperty("ribbon-test.ribbon.listOfServers","localhost:8081,localhost:8082");
        RestClient restClient = (RestClient) ClientFactory.getNamedClient("ribbon-test");
        HttpRequest httpRequest = HttpRequest.newBuilder().uri("/ribbon/hello/word").build();
        for (int i = 0; i < 10; i++) {
            HttpResponse response = restClient.executeWithLoadBalancer(httpRequest);
            String result = response.getEntity(String.class);
            System.out.println(result);
        }
    }

    @Test
    public void test2(){
        BaseLoadBalancer iLoadBalancer = new BaseLoadBalancer();
        iLoadBalancer.setRule(new MyRule());
        List<Server> list = new ArrayList<>(2);
        list.add(new Server("localhost",8081));
        list.add(new Server("localhost",8082));
        iLoadBalancer.addServers(list);
        for (int i = 0; i < 10; i++) {
            Server server = iLoadBalancer.chooseServer(null);
            System.out.println(server);
        }
    }

    static class MyRule implements IRule{
        private ILoadBalancer loadBalancer;
        @Override
        public Server choose(Object o) {
            List<Server> list = loadBalancer.getAllServers();
            return list.get(0);
        }

        @Override
        public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
            loadBalancer = iLoadBalancer;
        }

        @Override
        public ILoadBalancer getLoadBalancer() {
            return loadBalancer;
        }
    }
}
