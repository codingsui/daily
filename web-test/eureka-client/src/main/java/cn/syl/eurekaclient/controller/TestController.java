package cn.syl.eurekaclient.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @GetMapping("/discovery")
    public List<String> discoveryClient(){
        List<String> servers = discoveryClient.getServices();
        for (String server:servers) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(server);
            for (ServiceInstance item:serviceInstances) {
                String serviceId = item.getServiceId();
                URI uri = item.getUri();
                String host = item.getHost();
                int port = item.getPort();
                System.out.println(serviceId +":" + uri);
                System.out.println(host+":"+port);
            }
        }
        return servers;
    }

    @GetMapping("/get/{msg}")
    public String get(@PathVariable("msg") String msg) throws InterruptedException {

        Thread.sleep(10000);
        return msg;
    }

    @GetMapping("/change")
    public String change(@RequestParam("status") String status){
        if (status.equals("1")){
            applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        }else {
            applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        }
        return "ok";
    }

}
