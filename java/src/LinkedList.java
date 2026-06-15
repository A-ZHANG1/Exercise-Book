import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2019-07-08.
 * 程序员代码面试指南，链表相关问题
 *
 * */
public class LinkedList {
    Node head;

    public class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public LinkedList insertLast(LinkedList list, int data)
    {
        Node new_node = new Node(data);
        new_node.next = null;

        if (list.head == null) {
            list.head = new_node;
        }
        else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
        }
        return list;
    }

    public static void printList(LinkedList list)
    {
        Node currNode = list.head;

        System.out.print("LinkedList: ");

        while (currNode != null) {
            System.out.print(currNode.value + " ");
            currNode = currNode.next;
        }
    }

    //p51
    public Node JosephusKill(Node head, int m){
        if(head == null || head.next == head || m < 1){
            return head;
        }
        Node last = head;
        while(last.next != head){
            last = last.next;
        }
        int count = 0;
        while(head != last){
            if(++count == m){
                head = last.next;
            }else{
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    //p56
    public Boolean isPalindrome(Node head){
        Stack<Node> s = new Stack<>();
        Node cur = head;
        while(cur != null){
            s.push(cur);
            cur = cur.next;
        }
        while(head.next != head){
            if(s.pop().value != head.value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //p57 右半区折过去和左半区对比。关键是找到中间位置
    public Boolean isPalindrome2(Node head){
        Stack<Node> s = new Stack<>();
        Node fast = head;
        Node slow = head.next;
        while(fast.next.next != null ){
            slow = slow.next;
            fast = fast.next.next;
        }
        while(slow != null){
            s.push(slow);
            slow = slow.next;
        }
        while(!s.isEmpty()){
            if(s.pop().value != head.value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

}

