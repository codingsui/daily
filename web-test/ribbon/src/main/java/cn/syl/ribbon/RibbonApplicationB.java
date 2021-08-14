package cn.syl.ribbon;
//
//import cn.syl.RibbonAConf;
//import cn.syl.RibbonBConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@SpringBootApplication
//@RibbonClients(
//        value = {
//                @RibbonClient(name = "ribbonA",configuration = RibbonAConf.class),
//                @RibbonClient(name = "ribbonB",configuration = RibbonBConf.class )
//        }
//)
public class RibbonApplicationB {


    public static void main(String[] args) {
        SpringApplication.run(RibbonApplicationB.class,args);
    }
}
