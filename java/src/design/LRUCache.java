package design;
import java.util.*;
/**
 * Created by Wayne.A.Z on 2020-10-08.
 */
// TODO: 参考LFUCache，用Linkedlist接口实现
class LRUCache {
    Map <Integer, Node> map;
    DoubleList cache;
    int cap;
    public LRUCache(int capacity) {
        map = new HashMap();
        cache = new DoubleList();
        this.cap = capacity;
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        int val = map.get(key).val;
        put(key, val); // 用put 方法提前
        return val;
    }

    public void put(int key, int value) {
        Node x = new Node(key, value);
        if(map.containsKey(key)){
            cache.remove(map.get(key));
            cache.addFirst(x);
            map.put(key, x);// 更新映射
        }else{
            if(cap == cache.size()){
                Node last = cache.removeLast();
                map.remove(last.key);//
                System.out.println(last.key);
            }
            cache.addFirst(x);
            map.put(key, x); //
        }
    }

    class Node{
        int key;
        int val;
        Node prev;
        Node next;
        public Node(int k, int v){
            this.key = k;
            this.val = v;
        }
        public Node(){}
    }
    class DoubleList{
        int size;
        Node head;
        Node tail;
        public DoubleList(){
            this.size = 0;
            // 伪头结点 尾节点
            this.head = new Node();
            this.tail = new Node();
            head.next = tail;
            tail.prev = head;
        }
        public void addFirst(Node x){
            x.prev = head;
            x.next = head.next;
            head.next.prev = x;
            head.next = x;
            this.size ++;
        }
        public void remove(Node x){
            x.prev.next = x.next;
            x.next.prev = x.prev;
            this.size --;
        }
        public Node removeLast(){
            Node res = tail.prev;
            remove(res);
            return res;
        }
        public int size(){
            return this.size;
        }
    }
}


/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */