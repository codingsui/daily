package cn.syl.java.redis.zset;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShopSearchDemo implements ShopSearch{

    public static void main(String[] args) {
        ShopSearch shopSearch = new ShopSearchDemo();
        List<String> key1 = new ArrayList<>();
        key1.add("家具");
        key1.add("实用");
        key1.add("低价");
        key1.add("木材");
        shopSearch.addKeyWords(1,key1);

        List<String> key2 = new ArrayList<>();
        key2.add("家具");
        key2.add("木材");
        shopSearch.addKeyWords(2,key2);


        List<String> key3 = new ArrayList<>();
        key3.add("浴室");
        key3.add("低价");
        shopSearch.addKeyWords(4,key3);

        List<String> k1 = new ArrayList<>();
        k1.add("家具");
        System.out.println(shopSearch.searchProduct(k1));
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    @Override
    public long addKeyWords(long productId, List<String> keyWords) {
        for (String item : keyWords){
            jedis.sadd(String.format("keywords:%s",item), String.valueOf(productId));
        }
        return 1;
    }

    @Override
    public Set<String> searchProduct(List<String> keyWords) {
        List<String> k = new ArrayList<>();
        keyWords.stream().forEach(item->k.add(String.format("keywords:%s",item)));
        String[] z = k.toArray(new String[k.size()]);
        return jedis.sinter(z);
    }
}

interface ShopSearch{


    /**
     * 给商品添加关键词
     * @param productId
     * @param keyWords
     * @return
     */
    long addKeyWords(long productId, List<String> keyWords);

    /**
     * 根据关键词搜索商品
     * @param keyWords
     * @return
     */
    Set<String> searchProduct(List<String> keyWords);



}
