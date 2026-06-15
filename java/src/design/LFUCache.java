package design;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Wayne.A.Z on 2020-10-05.
 * 看题解，可以用哪些数据结构，分析复杂度
 *
 * TODO: 用例没过。花花说太南不常考
 */
// 460
public class LFUCache {
    Map<Integer, LinkedList<Node>> frequency; // linkedlist的实现就是双向链表 https://www.runoob.com/java/java-linkedlist.html
    Map<Integer, Node> cache;
    int capacity;
    int minFreq;

    public LFUCache(int capacity) {
        this.frequency = new HashMap<>();
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(cache.containsKey(key)){
            Node n = cache.get(key);
            freqInc(n);
            return n.val;
        }
        return -1;
    }
    public void put(int key, int value) {
        if(cache.containsKey(key)){
            Node n = cache.get(key);
            n.val = value;
            freqInc(n);
        }else{
            System.out.printf("%d, %d, %b, %d\n", key, cache.size(), cache.size() == capacity, minFreq);
            if(cache.size() == capacity){
//                System.out.println(frequency.get(minFreq).stream().map(Object::toString).collect(Collectors.joining(", ")));
                System.out.println(frequency.keySet().toString());
                System.out.println(frequency.get(minFreq).toString());
                Node deadNode = frequency.get(minFreq).getLast();
                System.out.println(deadNode.val);
                frequency.get(minFreq).remove(deadNode);
                if(frequency.get(minFreq).size() == 0) minFreq ++;

                cache.remove(deadNode.key);
            }
            Node n = new Node(key, value, 1);
            cache.put(key, n);
            addNewNode(n);
            minFreq = 1;
        }
    }

    public void freqInc(Node n){
        frequency.get(n.freq).remove(n);

        LinkedList<Node> list = frequency.getOrDefault(n.freq + 1, new LinkedList<Node>());
        list.addFirst(new Node(n.key, n.val, n.freq + 1));
        frequency.remove(n.freq + 1);
        frequency.put(n.freq + 1, list);
        if(n.freq == minFreq && list.size() == 0) minFreq = n.freq + 1;
        cache.put(n.key, n);
    }

    public void addNewNode(Node n){
        LinkedList<Node> list = frequency.getOrDefault(1, new LinkedList<>());
        list.add(n);
        frequency.put(1, list);
        minFreq = 1;
    }

    class Node{
        int key;
        int val;
        int freq;

        Node(int key, int val ,int freq){
            this.key = key;
            this.val = val;
            this.freq = freq;
        }

        @Override
        public String toString(){
            return "{" + key + ", " + val + ", " + freq + "}";
        }

    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);

//        System.out.println(cache.cache.keySet());

        int res1 = cache.get(1);
//        System.out.println(res1);

        cache.put(3, 3);
//        System.out.println(cache.cache.keySet());

        int res2 = cache.get(2);
//        System.out.println(res2);

        int res3 = cache.get(3);
//        System.out.println(res3);

        cache.put(4, 4);
//        System.out.println(cache.cache.keySet());

        int res4 = cache.get(1); // -1
        System.out.println(res4);

        int res5 = cache.get(3);
        System.out.println(res5);

        int res6 = cache.get(4);
        System.out.println(res6);
    }
}

