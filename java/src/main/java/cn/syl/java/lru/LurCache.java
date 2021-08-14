package cn.syl.java.limit;

import cn.syl.java.lru.LRUCache;

import java.util.HashMap;
import java.util.Objects;

public class LurCache {

    public static void main(String[] args) {
        LurCache lurCache = new LurCache(3);
        lurCache.set("1",1);
        lurCache.set("2",2);
        lurCache.set("3",3);
        lurCache.set("4",4);
        System.out.println(lurCache.get("1"));;
        System.out.println(lurCache.get("2"));;
    }

    private HashMap<String, LinkNode> cache = new HashMap<>();

    private int count;

    private int capacity;

    private LinkNode<String,Object> head = new LinkNode<>();
    
    private LinkNode<String,Object> tail = new LinkNode<>();
    
    public LurCache(int capacity){
        this.capacity = capacity;
        head.next = tail;
        tail.before = head;
    }
    
    static class LinkNode<K,V>{
        private K k;
        private V v;
        public LinkNode before;
        public LinkNode next;

    }
    
    public Object get(String key){
        LinkNode node = cache.get(key);
        if (Objects.isNull(node)){
            return null;
        }
        moveToTail(node);
        return node.v;
    }

    public void set(String key,Object val){
        LinkNode node = cache.get(val);
        if (node == null){
            LinkNode<String,Object> linkNode = new LinkNode<>();
            linkNode.k = key;
            linkNode.v = val;
            cache.put(key,linkNode);
            addNode(linkNode);
            ++count;
            if (count > capacity){
                this.cache.remove(popHead().k);
                count--;
            }
        }else {
            node.v = val;
            moveToTail(node);
        }
    }

    private void moveToTail(LinkNode<String,Object> p) {
        removeNode(p);
        addNode(p);
    }

    private LinkNode removeNode(LinkNode<String,Object> p){
        p.next.before = p.before;
        p.before.next = p.next;
        p.before = null;
        p.next = null;
        return p;
    }

    private LinkNode popHead(){
        if (head.next == tail){
            return null;
        }
        return removeNode(head.next);
    }

    private void addNode(LinkNode<String,Object> p){
        p.next = tail;
        tail.before.next = p;
        p.before = tail.before;
        tail.before = p;
    }


}
