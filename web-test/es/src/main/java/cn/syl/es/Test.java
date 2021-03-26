package cn.syl.es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

    @org.junit.Test
    public  void a() throws UnknownHostException {
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(
                new TransportAddress(InetAddress.getByName("39.106.39.129"),9200));
        GetResponse response = client.prepareGet("test_index","test_type","5").get();
        System.out.println(response.getSourceAsString());

    }
}
