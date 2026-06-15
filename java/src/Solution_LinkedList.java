import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2020-06-26.
 */


public class Solution_LinkedList {
    public static class ListNode { // 静态类 才能在下面的额静态方法中被访问
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }
    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }
    public static void prettyPrintLinkedList(ListNode node) {
        while (node != null && node.next != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }

        if (node != null) {
            System.out.println(node.val);
        } else {
            System.out.println("Empty LinkedList");
        }
    }
    // 100163
//    public ListNode removeDuplicateNodes(ListNode head) {
//        ListNode cur = head.next;
//        ListNode res = new ListNode(head.val);
//        ListNode resHead = res;
//
//        while(cur != null){
//            ListNode before = head;
//
//            while(before.next != cur){
//                before = before.next;
//                if(before.val == cur.val) {
//                    break;
//                }
//            }
//            System.out.println(cur.val);
//            res.next = cur;
//            res = res.next;
//            cur = cur.next;
//        }
//        return resHead;
//    }

    public ListNode removeDuplicateNodes(ListNode head) {
        Set<Integer> set = new HashSet<>(); // 去重
        ListNode cur = head;
        while(cur != null){
            set.add(cur.val);
            if(cur.next != null && set.contains(cur.next.val)){
                cur.next = cur.next.next;
            }else{
                cur = cur.next;
            }
        }
        return head;
    }

    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while(fast != slow && fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = head;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // 148

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head; // TODO: head.next == null
        ListNode fast = head.next, slow = head; // TODO: fast不能是head
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while(left != null && right != null){
            if(left.val < right.val){
                cur.next = left;
                left = left.next;
            }else{
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        cur.next = left != null ? left : right;
        return dummyHead.next;
    }
    // 147
//    public ListNode insertionSortList(ListNode head) {
//        ListNode prev = head, cur = head, dummy = new ListNode(Integer.MIN_VALUE);
//        dummy.next = head;
//        while(cur != null){
//            if(prev.val <= cur.val) {
//                prev = cur;
//                cur = cur.next;
//            }else{
//                ListNode p = dummy;
//                while(p != prev && p.next.val < cur.val){
//                    p = p.next;
//                }
//                // prev始终指向已排序链表末尾
//                prev.next = cur.next;
//                // cur 插入到p和p.next之间
//                cur.next = p.next;
//                p.next = cur;
//
//                cur = prev.next;
//            }
//        }
//        return dummy.next;
//    }

    public ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        ListNode tail = head, prev = dummy ,cur = head.next;
        while(cur != null){
            ListNode suc = cur.next;
            if(cur.val < tail.val){

                while(prev.next.val < cur.val) prev = prev.next;
                cur.next = prev.next;
                prev.next = cur;
                tail.next = suc;
            }else{
                tail = cur;
            }
            cur = suc;
//        prettyPrintLinkedList(head);
        }
        return dummy.next;
    }

    // 326
    public ListNode oddEvenList(ListNode head) {
        if(head == null) return null;
        ListNode odd = head, even = head.next;
        ListNode evenHead = even;
        while(even!= null && odd != null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
    // 61
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null) return head;
        ListNode cur = head;
        int len = 0;
        while(cur != null){
            cur = cur.next;
            len += 1;
        }

        k = k % len;
//        System.out.println(len);
//        System.out.println(k);
        if(k == 0) return head;
        cur = head;
        for (int i = 0; i < len - k - 1; i++) {
            cur = cur.next;
        }
        ListNode right = cur.next, left = head;

        cur.next = null;
        head = right;
        while(right.next != null){
            right = right.next;
        }
        right.next = left;
        return head;
    }
//    public ListNode rotateRight(ListNode head, int k) {
//        if(head == null || head.next == null || k == 0) return head;
//        ListNode tail, newTail = head, newHead = head;
//        int len = 1;
//        ListNode cur = head;
//        while(cur.next != null){
//            cur = cur.next;
//            len ++;
//        }
//        tail = cur;
//
////        if(k % len == 0) return head;//
//
//        int cnt = 0;
//
//        while(cnt < len - k % len - 1){
//            newTail = newTail.next;
//            cnt ++;
//        }
//        newHead = newTail.next;
//        tail.next = head;
//        newTail.next = null;
//        return newHead;
//    } // k = 0
//    public ListNode rotateRight(ListNode head, int k) {
//        // base cases
//        if (head == null) return null;
//        if (head.next == null) return head;
//
//        // close the linked list into the ring
//        ListNode old_tail = head;
//        int n;
//        for(n = 1; old_tail.next != null; n++)
//            old_tail = old_tail.next;
//        old_tail.next = head;
//
//
//        ListNode new_tail = head;
//        for (int i = 0; i < n - k % n - 1; i++)
//            new_tail = new_tail.next;
//        ListNode new_head = new_tail.next;
//
//        // break the ring
//        new_tail.next = null;
//
//        return new_head;
//    }

    // 25
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy, end = dummy;
        while(end.next != null){
            for(int i = 0;i < k && end != null;i ++) end = end.next;
            if(end == null) break;
            ListNode start = prev.next;
            ListNode next = end.next;
            end.next = null;
            prev.next = reverse(start);
            start.next = next;
            prev = start;

            end = prev;

        }
        return dummy.next;
    }
    public ListNode reverse(ListNode head){
        if(head == null || head.next == null) return head; // !
        ListNode prev = null, cur = head, next;
        while(cur != null){
            next = cur.next;
            cur.next = prev; // 引用传递，会同时改变next的值
            prev = cur;
            cur = next;
        }
        prettyPrintLinkedList(prev);
        return prev;
    }

    //206 TODO: 为什么prev永远是null.和上面的reverse👆本质区别是什么-->别看这逻辑了， cur -> prev 就完了
    public ListNode reverseList(ListNode head) {
        if (head == null) return head;
        ListNode cur = head, prev = null;

        while (cur.next != null) {
//            System.out.printf("cur: %d\n", cur.val);
            ListNode suc = cur.next;
            System.out.printf("suc: %d\n", suc.val);

//            if(prev != null) System.out.printf("prev: %d\n", prev.val);

//            System.out.printf("prev': %d\n", prev.val);
            cur = suc;
            cur.next = prev;
            prev = cur;
//            System.out.printf("cur': %d\n", cur.val);
//            System.out.printf("cur': %d\n", cur.val);
        }
        return cur; // 不能return
    }

    // 234
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true; //
        ListNode fast = head.next, slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode mid = slow.next;
        ListNode prev = null, cur = mid, next;
        while(cur != null){
            System.out.println(cur.val);
            next = cur.next;

            cur.next = prev;
            prev = cur;
            cur = next;

        }
        slow.next = prev;
        prettyPrintLinkedList(head);
        while(prev != null){
            if(prev.val != head.val) return false;
            prev = prev.next;
            head = head.next;
        }
        return true;
    }

    // 639
    public int numDecodings(String s) {
        int MOD = 1000000007;
        char[] sA = s.toCharArray();
        int N = s.length();
        char[] sArr = new char[N + 1]; // s[0..N) sArr[1..N] dp[1..N]
        System.arraycopy(sA, 0, sArr, 1, N);
        sArr[0] = '#';
        int[] dp = new int[N + 1];
        dp[0] = 1;

        if(sArr[1] == '0') dp[1] = 0;
        else if(sArr[1] == '*') dp[1] = 9;
        else dp[1] = 1;

        for(int i = 2;i <= N;i ++){
            dp[i] = dp[i - 1] * 9;
            if(sArr[i] == '*'){
                if(sArr[i - 1] == '1'){
                    dp[i] = (dp[i] + dp[i - 2] * 9) % MOD;
                }else if(sArr[i - 1] == '2'){
                    dp[i] = (dp[i] + dp[i - 2] * 6) % MOD;
                }else if(sArr[i - 1] == '*'){
                    dp[i] = (dp[i] + dp[i - 2] * 15) % MOD;
                }
            }else{
                dp[i] = sArr[i] == '0' ? 0 : dp[i - 1];
                if(sArr[i - 1] == '1') dp[i] = (dp[i] + dp[i - 2]) % MOD; // 包含s[i-1]和不包含
                else if(sArr[i - 1] == '2' && sArr[i] <= '6') dp[i] = (dp[i] + dp[i - 2]) % MOD;
                else if(sArr[i - 1] == '*') dp[i] = (dp[i] + (sArr[i] <= '6' ? 2 : 1) * dp[i - 2]) % MOD;
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[N];
    }

    // 86
    public ListNode partition(ListNode head, int x) {

        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-1);
        ListNode smaller = dummy1;
        ListNode larger = dummy2;
        while(head != null){
            if(head.val < x){
                smaller.next = head;
                smaller = smaller.next;
            }else{
                larger.next = head;
                larger = larger.next;
            }
            head = head.next;
        }
        larger.next = null;
        smaller.next = dummy2.next;
        return dummy1.next;
    }
    // 725
//    public ListNode[] splitListToParts(ListNode root, int k) {
//        ListNode p = root;
//        int len = 0;
//        while(p != null){
//            len += 1;
//            p = p.next;
//        }
//
//        ListNode[] res = new ListNode[k];
//        int width = len / k;
//        System.out.println(len);
//        System.out.println(width);
//        p = root;
//        int i = 0;
//        while(p != null){
//            ListNode newRoot = new ListNode(-1), newTail = newRoot;
//            int m = 0;
//            while(m < width + (i < len % k ? 1 : 0)){
//                newTail.next = p;
//                newTail = newTail.next;
//                p = p.next;
//                m ++;
//            }
//            newTail.next = null;
//            res[i ++] = newRoot.next;
//        }
//        return res;
//    }
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] res = new ListNode[k];
        ListNode cur = root;
        int len = 0;
        while(cur != null){
            cur = cur.next;
            len += 1;
        }
        int n_len = len / k;
        int remain = len % k ;

        cur = root;
//        System.out.println(n_len);
        int i = 0;
        while(cur != null){
            ListNode dummy = new ListNode(0), tail = dummy;
            int l = 0;
            while(l < n_len + (i < remain ? 1 : 0)){
                tail.next = cur;
                tail = tail.next;
                cur = cur.next;
                l ++;
            }
            tail.next = null;
            res[i ++] = dummy.next;
        }
        return res;
    }

    public static void main(String[] args) {
        Solution_LinkedList s = new Solution_LinkedList();
//        ListNode head = s.stringToListNode("[1, 2, 3, 3,3,2, 1]");
//        s.prettyPrintLinkedList(s.removeDuplicateNodes(head));
//        ListNode head = s.stringToListNode("[4,2,1,3]");
//        s.prettyPrintLinkedList(s.sortList(head));
//        ListNode head = s.stringToListNode("[4,2,1,3]");
//        s.prettyPrintLinkedList(s.insertionSortList(head));
//        ListNode head = s.stringToListNode("[1,2,3,4,5,6]");
//        s.prettyPrintLinkedList(s.oddEvenList(head));
//        ListNode head = s.stringToListNode("[0, 1, 2]");
//        s.prettyPrintLinkedList(s.rotateRight(head, 4));
//        ListNode head = s.stringToListNode("[1, 2, 3, 4, 5]");
//        s.prettyPrintLinkedList(s.rotateRight(head, 2));
//        ListNode head = s.stringToListNode("[1, 2]");
//        s.prettyPrintLinkedList(s.rotateRight(head, 2));
//        ListNode head = s.stringToListNode("[1, 2, 3, 4, 5]");
//        s.prettyPrintLinkedList(s.reverseList(head));
//        s.prettyPrintLinkedList(s.reverseKGroup(head, 3));
//        ListNode head = s.stringToListNode("[1,4,3,2,5,2]");
//        s.prettyPrintLinkedList((s.partition(head, 3)));
        ListNode head = s.stringToListNode("[1,2,3,4]");
        ListNode[] reses = s.splitListToParts(head, 5);
        for(ListNode res: reses) s.prettyPrintLinkedList(res);
//        ListNode head = s.stringToListNode("[1,2,2,1]");
//        System.out.println(s.isPalindrome(head));

//        System.out.println(s.numDecodings("1*"));
//        System.out.println(s.numDecodings("*1")); // 11
//        System.out.println(s.numDecodings("0*1*8")); // 0
//        System.out.println(s.numDecodings("0")); // 0

    }
}
