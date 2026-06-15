package design;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2020-10-08.
 */
// 法1： 用双向链表实现. 时间复杂度降低，代码复杂度挺高

    // TODO: 单链表
public class MyLinkedList {
    int size;
    Node head, tail;

    public MyLinkedList(){
        this.size = 0;
        this.head = new Node(-1);
        this.tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
    }

    /** Get the value of the index-th node in the linked list.
     * If the index is invalid, return -1.
     * index 从0开始*/
    public int get(int index) {
        if(index < 0 || index >= size) return -1;
        Node cur;
        if(index + 1 < size - index){
            cur = head;
            for (int i = 0; i <= index; i++) cur = cur.next;

        }else{
            cur = tail;
//            System.out.println(tail.prev.val);
            for (int i = 0; i < size - index; i++) {
//                System.out.println(cur.val);
                cur = cur.prev;
            }
//            System.out.println(cur.val);

        }
//        System.out.println(cur.val);
        return cur.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        size ++;

        Node n = new Node(val);
        n.next = head.next;
        head.next.prev = n;
        head.next = n;
        n.prev = head;

//        System.out.println(head.next.val);
    }
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        size ++;

        Node n = new Node(val);

        tail.prev.next = n;
        n.prev = tail.prev;
        n.next = tail;
        tail.prev = n;
    }
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {

        Node cur, n = new Node(val);
        if(index < size - index){
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
//            System.out.printf("cur: %d\n", cur.val);
            n.next = cur.next;
//            System.out.printf("cur.next: %d\n", cur.next.val);
            cur.next.prev = n;
            cur.next = n;
            n.prev = cur;
//            System.out.printf("n.prev: %d\n", n.prev.val);
//            System.out.printf("n: %d\n", n.val);
//            System.out.printf("n.next: %d\n", n.next.val);
//            System.out.printf("n.next.next: %d\n", n.next.next.val);
        }else{
            cur = tail;
//            System.out.printf("cur: %d\n", cur.val);
//            System.out.printf("i: %d\n", i);
//            System.out.printf("size - index: %d\n", size - index);
            for (int i = 0; i <= size - index; i++) cur = cur.prev;
//            System.out.printf("cur: %d\n", cur.val);

            n.next = cur.next;
            cur.next.prev = n;
            cur.next = n;
            n.prev = cur;
//            System.out.printf("cur.next.prev: %d\n", cur.next.prev.val);
//            System.out.printf("tail.prev: %d\n", tail.prev.val);
//            System.out.printf("next: %d\n", n.next.val);
        }

        size ++;
    }
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size) return;
        Node cur;
        if(index < size - index) {
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
            cur.next = cur.next.next;
            cur.next.prev = cur;
        }else{
            cur = tail;
            for (int i = 1; i < size - index; i++) cur = cur.prev;
//            System.out.printf("cur.val: %d\n", cur.val);
            cur.prev = cur.prev.prev;
            cur.prev.next = cur;
        }

        size -- ;
    }

    class Node{
        int val;
        Node next, prev;
        public Node(int val){
            this.val = val;
            this.next = null;
        }
    }

    public static void main(String[] args){
        MyLinkedList linkedList = new MyLinkedList();

        // 用例1
//        linkedList.addAtHead(1);
//        System.out.println(linkedList.get(0));            //返回1
//
//        linkedList.addAtTail(3);
//        System.out.println(linkedList.get(1));            //返回1
//        System.out.println(linkedList.get(2));            //返回1
//        linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
//        System.out.println(linkedList.get(0));            //返回1
//        System.out.println(linkedList.get(1));            //返回2
//        System.out.println(linkedList.get(2));            //返回3
//        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
////        System.out.println(linkedList.get(1));           //返回3

        // 用例2
//        linkedList.addAtIndex(0, 10);
//        System.out.println(linkedList.get(0));
//
//        linkedList.addAtIndex(0, 20);
//        linkedList.addAtIndex(1, 30);
//        System.out.println(linkedList.get(0));
//        System.out.println(linkedList.get(1));
//        System.out.println(linkedList.get(2));

        //用例3
//        linkedList.addAtHead(2);
//        linkedList.deleteAtIndex(1);
//        linkedList.addAtHead(2);
//        linkedList.addAtHead(7);
//        linkedList.addAtHead(3);
//        linkedList.addAtHead(2);
//        linkedList.addAtHead(5);
//        linkedList.addAtTail(5);
//        linkedList.get(5);
//        linkedList.deleteAtIndex(6);
//        linkedList.deleteAtIndex(4);

        // 用例4
        linkedList.addAtHead(38);
        linkedList.addAtTail(66);
        linkedList.addAtTail(61);
        linkedList.addAtTail(76);
        linkedList.addAtTail(26);
        linkedList.addAtTail(37);
        linkedList.addAtTail(8);
        linkedList.deleteAtIndex(5);
        linkedList.addAtHead(4);
        linkedList.addAtHead(45);
        System.out.println(linkedList.get(0));
        System.out.println(linkedList.get(1));
        System.out.println(linkedList.get(2));
        System.out.println(linkedList.get(3));
        System.out.println(linkedList.get(4));
        System.out.println(linkedList.get(5));
        System.out.println(linkedList.get(6));
        System.out.println(linkedList.get(7));
        System.out.println(linkedList.get(8));
    }
}
