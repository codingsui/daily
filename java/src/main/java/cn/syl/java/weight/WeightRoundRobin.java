package cn.syl.java.weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightRoundRobin {

    /**上次选择的服务器*/
    private int currentIndex = -1;
    /**当前调度的权值*/
    private int currentWeight = 0;
    /**最大权重*/
    private int maxWeight;
    /**权重的最大公约数*/
    private int gcdWeight;
    /**服务器数*/
    private int serverCount;
    private List<Server> servers = new ArrayList<Server>();

    /*
     * 得到两值的最大公约数
     */
    public int greaterCommonDivisor(int a, int b){
        if(a % b == 0){
            return b;
        }else{
            return greaterCommonDivisor(b,a % b);
        }
    }
    /*
     * 得到list中所有权重的最大公约数，实际上是两两取最大公约数d，然后得到的d
     * 与下一个权重取最大公约数，直至遍历完
     */
    public int greatestCommonDivisor(List<Server> servers){
        int divisor = 0;
        for(int index = 0, len = servers.size(); index < len - 1; index++){
            if(index ==0){
                divisor = greaterCommonDivisor(
                        servers.get(index).getWeight(), servers.get(index + 1).getWeight());
            }else{
                divisor = greaterCommonDivisor(divisor, servers.get(index).getWeight());
            }
        }
        return divisor;
    }

    /*
     * 得到list中的最大的权重
     */
    public int greatestWeight(List<Server> servers){
        int weight = 0;
        for(Server server : servers){
            if(weight < server.getWeight()){
                weight = server.getWeight();
            }
        }
        return weight;
    }

    /**
     *  算法流程：
     *  假设有一组服务器 S = {S0, S1, …, Sn-1}
     *  有相应的权重，变量currentIndex表示上次选择的服务器
     *  权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
     *  通过权重的不断递减 寻找 适合的服务器返回
     */
    public Server getServer(){
        while(true){
            currentIndex = (currentIndex + 1) % serverCount;
            if(currentIndex == 0){
                currentWeight = currentWeight - gcdWeight;
                if(currentWeight <= 0){
                    currentWeight = maxWeight;
                    if(currentWeight == 0){
                        return null;
                    }
                }
            }
            if(servers.get(currentIndex).getWeight() >= currentWeight){
                return servers.get(currentIndex);
            }
        }
    }

    public void init(){
        servers.add(new Server("192.168.191.1", 5));
        servers.add(new Server("192.168.191.2", 1));
        servers.add(new Server("192.168.191.4", 1));
        servers.add(new Server("192.168.191.8", 1));


        maxWeight = greatestWeight(servers);
        gcdWeight = greatestCommonDivisor(servers);
        serverCount = servers.size();
    }

    public static void main(String args[]){
        WeightRoundRobin weightRoundRobin = new WeightRoundRobin();
        weightRoundRobin.init();

        for(int i = 0; i < 15; i++){
            Server server = weightRoundRobin.getServer();
            System.out.println("server " + server.getIp() + " weight=" + server.getWeight());
        }
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Server k =weightRoundRobin.getServer();
            if (map.containsKey(k.getIp())){
                Integer a = map.get(k.getIp());
                a++;
                map.put(k.getIp(),a);
            }else {
                map.put(k.getIp(),Integer.valueOf(1));
            }
        }
        System.out.println(map);
    }

}

class Server{
    private String ip;
    private int weight;

    public Server(String ip, int weight) {
        this.ip = ip;
        this.weight = weight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}