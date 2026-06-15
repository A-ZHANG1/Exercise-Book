import java.sql.SQLOutput;
import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by Wayne.A.Z on 2020-06-27.
 */
public class Solution_Tree {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

    }
    public static String treeNodeToString(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        String output = "";
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while(!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (node == null) {
                output += "null, ";
                continue;
            }

            output += String.valueOf(node.val) + ", ";
            nodeQueue.add(node.left);
            nodeQueue.add(node.right);
        }
        return "[" + output.substring(0, output.length() - 2) + "]";
    }

    public TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while(!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }
    // 235 BST 公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 都在左子树
        if(p.val < root.val && q.val < root.val) return lowestCommonAncestor(root.left, p, q);
        // 都在右子树
        else if(p.val > root.val && q.val > root.val) return lowestCommonAncestor(root.right, p, q);
        else return root;
    }

    // 98
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    public boolean valid(TreeNode root, long low, long high) {
        System.out.println(low);
        System.out.println(high);
        if (root == null) return true;
        if (root.val <= low || root.val >= high) return false;
        return valid(root.left, low, root.val) && valid(root.right, root.val, high);
    }
    // 必须要小于左子树的最小值
//    public boolean isValidBST(TreeNode root) {
//        if(root == null) return true;
//        if(root.left == null && root.right == null) return true;
//
//        if(root.left != null && root.left.val >= root.val || root.right != null && root.val >= root.right.val) return false;
//        return isValidBST(root.left) && isValidBST(root.right);
//    }

    // 687
    int ans687 ;
    public int longestUnivaluePath(TreeNode root) {

        ans687 = 0;
        dfs687(root);
        return ans687;
    }
    public int dfs687(TreeNode root){
        if(root == null) return 0;

        int left = dfs687(root.left);
        int right = dfs687(root.right);
        int leftAns = 0, rightAns = 0;
        if(root.left != null && root.left.val == root.val) leftAns += left + 1;
        if(root.right != null && root.right.val == root.val) rightAns += right + 1;
        ans687 = Math.max(ans687, leftAns + rightAns);
        return Math.max(leftAns, rightAns);
    }

    // 95
    public List<TreeNode> generateTrees(int n) {
        if(n == 0){
            return new LinkedList<TreeNode>();
        }
        return divide(1, n);

    }
    public List<TreeNode> divide(int start, int end){
        List<TreeNode> res = new ArrayList<>();
        if(start > end){
            res.add(null);
            return res;
        }
        for(int i = start;i <= end;i ++){
            List<TreeNode> leftTrees = divide(start, i - 1);
            List<TreeNode> rightTrees = divide(i + 1, end);
            for(TreeNode leftTree: leftTrees){
                for(TreeNode rightTree: rightTrees){
                    TreeNode r = new TreeNode(i);
                    r.left = leftTree;
                    r.right = rightTree;
                    res.add(r);
                }
            }
        }
        return res;
    }

    // 230
//    int ans = 0;
//    int cnt = 0;
//    public int kthSmallest(TreeNode root, int k) {
//        inorder(root, k);
//        return ans;
//    }
//    public void inorder(TreeNode node, int k){
//        if(node == null) return;
//        inorder(node.left, k);
//        cnt ++;
////        System.out.println(cnt);
//        if(cnt == k) ans = node.val;
//        inorder(node.right, k);
//    }
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.push(root);
        while(!stack.isEmpty() || root != null){
            stack.push(root);

            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
//            System.out.println(node.val);
            k --;
            if(k == 0) return root.val;
            root = root.right;
        }
        return -1;
    }


        // 508
    Map<Integer, Integer> map;
    public int[] findFrequentTreeSum(TreeNode root) {
        map = new HashMap<>();
        sum(root);
        int max = Collections.max(map.values());
//         System.out.println(max);
        List<Integer> res = new ArrayList<>();
        for(int k :map.keySet()){
            if(map.get(k) == max) res.add(k);
        }
        return res.stream().mapToInt(i->i).toArray();
    }
    public int sum(TreeNode node){
        if(node == null) return 0;
        int left = sum(node.left), right = sum(node.right);
        int sum = node.val + left + right;
//        System.out.println(sum);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        return sum;
    }

    // 1443 edge[from, to]，len(edge)=n-1的情况；错用例 edges = [[0,2],[0,3],[1,2]]

    public int minTime2(int n, int[][] edges, List<Boolean> hasApple) {
        // 建树 tree[child] = parent;
        tree = new int[n];
        for(int[] edge : edges){
            tree[edge[1]] = edge[0];
        }
        // dfs
        visited = new boolean[n];
        visited[0] = true;

        for(int i = 1;i < n;i ++){
            if(hasApple.get(i)){
                dfs(i);
            }
        }
        return res * 2; //等价于res<<1;
    }

//    int res = 0;
    int[] tree;
    boolean[] visited;

    public void dfs(int to){
        if(!visited[to]){
            visited[to] = true;
            res ++;
            dfs(tree[to]);
        }
    }

    // TODO: 1443 边不是from, to 形式给的情况

    // 654
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length);
    }
    public TreeNode construct(int[] nums, int l, int r){
        if(l >= r) return null;
        int maxIdx = findMax(nums, l, r);
        TreeNode root = new TreeNode(nums[maxIdx]);
        root.left = construct(nums, l, maxIdx);
        root.right = construct(nums, maxIdx + 1, r);
        return root;
    }
    public int findMax(int[] nums, int l, int r){
        int maxIdx = l;
        for(int i = l;i < r;i ++){
//            System.out.println(maxIdx);
            if(nums[maxIdx] < nums[i])
                maxIdx = i;
        }
        return maxIdx;
    }

    // 1028
    int idx = 0;
    public TreeNode recoverFromPreorder(String S) {
        return helper(S, 0);
    }
    private TreeNode helper(String S,  int depth){
        // getDepth
        int d = 0;
        while(idx < S.length() && S.charAt(idx) == '-'){
            d ++;
            idx ++;
        }
        if(d < depth) {
            idx -= d;
            return null; // 深度不等于期待深度： 退栈  // 递归的出口，向上返回null
        }
        // getVal
        int val = 0;
//        while(idx < S.length() && S.charAt(idx) <='9' && S.charAt(idx) >= '0'){
        while(idx < S.length() && S.charAt(idx) !='-'){
            val = val * 10 + (S.charAt(idx) - '0');
            idx ++;
        }
        System.out.printf("%d, %d\n",idx, val);
        TreeNode root = new TreeNode(val);
//        TreeNode root = new TreeNode(getVal(S, idx));
        root.left = helper(S, d + 1);
        root.right = helper(S, d + 1);
        return root;
    }

    // 103
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> nodes = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res; // !!
        int level = 0;
        nodes.add(root);

        while(!nodes.isEmpty()){ // 判断linkedList 空不能有==null
            int cnt = nodes.size();
            List<Integer> tmp = new ArrayList<Integer>();
            for(int i = 0;i < cnt;i ++){
                TreeNode node = nodes.remove();
                tmp.add(node.val);
                if(node.left != null) nodes.add(node.left);
                if(node.right != null) nodes.add(node.right);
            }

            if(level % 2 == 1) Collections.reverse(tmp);
            System.out.println(tmp.stream().map(Object::toString)
                    .collect(Collectors.joining(", ")));
            res.add(tmp);
            level += 1;
        }
        return res;
    }
    /*
    * 前缀树，见 208
    * 插入 ： O(m) O(m) m为键长
    * 查找：O(m) O(1)
    *
    * 比较哈希 查找O(1) --> O(n) 出现大量冲突时的插入
    * 以及不能剪枝减小搜索空间
    */
    // 面试题 17.13. Re-Space LCCI
//    TrieNode dummyRoot = new TrieNode();
//    public int respace(String[] dictionary, String sentence) {
//        makeTrie(dictionary);
//        int N = sentence.length();
//        int[] dp = new int[N + 1];
//
//
//        for(int i = 1;i <= N;i ++){
//            dp[i] = dp[i - 1] + 1; // 初始默认不匹配
//            TrieNode node = dummyRoot;
//            for(int j = i;j > 0;j --){
//                int c = sentence.charAt(j - 1) - 'a';
//
//                if(node.childs[c] == null) break;
//                else if(node.childs[c].isEnd) dp[i] = Math.min(dp[i], dp[j - 1]);
//                if(dp[i] == 0) break;
//
//                node = node.childs[c];
//            }
//
//        }
//        return dp[N];
//    }
//    public void makeTrie(String[] dictionary){
//        for(String str: dictionary){
//            TrieNode node = dummyRoot;
//            for(int i = str.length() - 1;i >= 0 ;i --){
//                int c = str.charAt(i) - 'a';
//                if(node.childs[c] == null){
//                    node.childs[c] = new TrieNode();
//                }
//                node = node.childs[c];
//            }
//            node.isEnd = true;
//        }
//    }
//    public class TrieNode{
//        TrieNode[] childs; // int 26
//        boolean isEnd;
//        public TrieNode(){
//            this.childs = new TrieNode[26];
//            this.isEnd = false;
//        }
//    }

    // 140
    TrieNode dummyRoot = new TrieNode();
    public List<String> wordBreak(String s, List<String> wordDict) {
        makeTrie(wordDict);
        int N = s.length();
        List<List<Integer>> dp = new ArrayList(N);
        List<String> res = new ArrayList();
        List<String> tmp = new ArrayList();

        for(int r = 1;r < N;r ++){
            TrieNode node = dummyRoot;
            for(int l = r;l > 0;l --){
                int c = s.charAt(l) - 'a';
//                System.out.println(s.charAt(l));
//                System.out.println(node.child[c]);
                if(node.child[c] == null) break;
                else if(node.child[c].isEnd){ // 为什么不会进这个判断？
//                    System.out.println("jidvsljlj");
                    dp.get(r).add(l);
                }
                node = node.child[c];
            }
        }
        for(List<Integer> d: dp){
            System.out.println(Arrays.toString(d.toArray()));
        }
        if(dp.get(N - 1) != null) dfs(s, dp, N - 1, new ArrayList<String>(), res);
        return res;
    }
    public void dfs(String s, List<List<Integer>> dp, int r, List<String> path, List<String> res){
        if(r == 0){
            StringBuilder sb = new StringBuilder();
            for(String tmp: path){
                sb.append(tmp);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            res.add(sb.toString());
        }
        for(int l: dp.get(r)){
            path.add(0, s.substring(l, r + 1));
            dfs(s, dp, l, path, res);
            path.remove(0);
        }
    }

    public void makeTrie(List<String> dic){

        for(String s: dic){
            TrieNode node = dummyRoot;
            for(int i = 0;i < s.length();i ++){
                int c = s.charAt(i) - 'a';
                if(node.child[c] == null) node.child[c] = new TrieNode();
                node = node.child[c];
            }
            node.isEnd = true;
        }
    }
    class TrieNode{
        TrieNode[] child;
        boolean isEnd;
        public TrieNode(){
            this.child = new TrieNode[26];
            this.isEnd = false;
        }
    }

    // 周赛 199 树状动归 没有完全理解
    int resu = 0;
    int distance = 0;
    public int countPairs(TreeNode root, int distance) {
        this.distance = distance;
        dfs(root);
        return resu;
    }
    public int[] dfs(TreeNode root) {
        int[] dist = new int[distance + 1];
        if (root == null) return dist;
        if (root.left == null && root.right == null) {
            dist[1] = 1;
            return dist;
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        for (int i = 1; i < distance; i++) {
            for (int j = 1; j <= distance - i; j++) {
                resu += left[i] * right[j];
            }
        }
        for (int i = 2; i < distance; i++) {
            dist[i] = left[i - 1] + right[i - 1];
        }
        return dist;
    }

    // 199
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode cur = new TreeNode(-1);
        while(!queue.isEmpty()){
            int size_level = queue.size();
            for(int i = 0;i < size_level;i ++){
                cur = queue.poll();
                if(cur.left != null) queue.add(cur.left);
                if(cur.right != null) queue.add(cur.right);
            }
            res.add(cur.val);

        }
        return res;
    }

    // TODO： jian 54 ?? 为什么不能传参k -- >对这个递归调用栈并没有完全理解
    int res;
    int k;
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        helper(root);
        return res;
    }
//    public void helper(TreeNode node, int k){
//        if(node == null) return;
//        helper(node.right, k); //
//
//        if(k == 0) return;
//        k -= 1;
//        if(k == 0) res = node.val;
//        helper(node.left, k);
//    }
    public void helper(TreeNode node){
        if(node == null) return;
        helper(node.right); //
        if(k == 0) return;
        if(-- k == 0) res = node.val;
        helper(node.left);
    }

    // jian 28
    public boolean isSymmetric(TreeNode root) {
//        if(root == null) return true;
        return helper(root.left, root.right);
    }
    public boolean helper(TreeNode l, TreeNode r){
//        System.out.println(l.val);
//        System.out.println(r.val);
        if(l == null && r == null) return true;
        else if(l == null || r == null) return false;
        else if(l.val == r.val) return helper(l.left, l.right) && helper(r.left, l.right);
        else return false;
    }

    // jian 65

    // 297 dfs
//    public String serialize(TreeNode root) {
//        String res = "";
//        res = dfs(root, res);
//        System.out.println(res);
//        return res;
//    }
//    public String dfs(TreeNode node, String res){
//        if(node == null) res += "null,";
//        else{
//            res += node.val;
//            res += ",";
//            res = dfs(node.left, res);
//            res = dfs(node.right, res);
//        }
//        return res;
//    }

    public String rserialize(TreeNode root, String str) {
        if (root == null) {
            str += "null,";
        } else {
            str += str.valueOf(root.val) + ",";
            str = rserialize(root.left, str);
            str = rserialize(root.right, str);
        }
        return str;
    }

    public String serialize(TreeNode root) {
        String str = rserialize(root, "");
        str = str.substring(0, str.length() - 1);
        return str;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        List<String> l = new LinkedList<String>(Arrays.asList(nodes));
        return dfs2(l);
    }
    public TreeNode dfs2(List<String> l){
        if(l.get(0).equals("null")){
            l.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
        l.remove(0);
        root.left = dfs2(l);
        root.right = dfs2(l);

        return root;
    }

    // 617
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        TreeNode merged = new TreeNode(0);
        if(t1 != null && t2 != null){
            merged.val = t1.val + t2.val;
        }else if(t1 == null){
            return t2;
        }else if(t2 == null){
            return t1;
        }
        merged.left = mergeTrees(t1.left, t2.left);
        merged.right = mergeTrees(t1.right, t2.right);
        return merged;
    }

    // 1673
    public boolean isBalanced(TreeNode root) {
        if(root == null) return true;
        return Math.abs(dfss(root.left) - dfss(root.right)) <= 1;
    }

    public int dfss(TreeNode root){
        if(root == null) return 0;
        int left = dfss(root.left);
        int right = dfss(root.right);
        System.out.printf("%d %d %d\n", root.val, left, right);
        if(Math.abs(left - right) >= 1) return Integer.MAX_VALUE;
        return 1 + Math.max(left, right);
    }

    //426
//    Node first = null;
//    Node last = null;
//    public Node treeToDoublyList(Node root) {
//
//        dfs426(root);
//
//        first.left = last;
//        last.right = first;
//        return first;
//    }
//    public void dfs426(Node root){
//        if(root == null){
//            return;
//        }
//        dfs426(root.left);
//        if(last != null){
//            root.left = last;
//            last.right = root;
//        }else{
//            first = root;
//        }
//        last = root;
//        dfs426(root.right);
//    }

    // 周赛209 #2

    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int level = 0;
        while(!q.isEmpty()){
            int len = q.size();
            level ++;
            int last_odd = Integer.MIN_VALUE;
            int last_even = Integer.MAX_VALUE;
            for (int i = 0; i < len; i++) {
                TreeNode node = q.poll();
                if(node == null) return true;
                int val = node.val;

                if(node.left != null) q.add(node.left); // 防止空指针异常
                if(node.right != null) q.add(node.right);

                if(level % 2 == 1){
                    if(last_odd >= val || val % 2 == 0) return false;
                    last_odd = val;
                }else{
                    if(last_even <= val || val % 2 == 1) return false;
                    last_even = val;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
        Solution_Tree st = new Solution_Tree();
        // 95
//        List<TreeNode> res = st.generateTrees(3);
//        for(TreeNode node: res){
//            System.out.println(treeNodeToString(node));
//        }
        // 1443
//        int[][] edges = {{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}};
//        List<Boolean> hasApple = Arrays.asList(false,false,true,false,true,true,false);
//        st.minTime2(7, edges, hasApple);
        // 654
//        int[] nums = {3,2,1,6,0,5};
//        System.out.println(st.treeNodeToString(st.constructMaximumBinaryTree(nums)));
//        String S = "1-2--3--4-5--6--7";
//        System.out.println(st.treeNodeToString(st.recoverFromPreorder(S)));
//
//        TreeNode root = st.stringToTreeNode("[3,9,20,null,null,15,7]");
//        List<List<Integer>> res = st.zigzagLevelOrder(root);
//        System.out.println(res.stream().map(Object::toString)
//                .collect(Collectors.joining(", ")));

//        String[] dictionary = {"looked","just","like","her","brother"};
//        String sentence = "jesslookedjustliketimherbrother";
//        System.out.println(st.respace(dictionary, sentence));
//
//        List<String> dic = new ArrayList<>(Arrays.asList("cat","cats","and","sand","dog"));
//        System.out.println(st.wordBreak("catsanddog", dic));

//        TreeNode root = st.stringToTreeNode("[1,2,3,4,5,6,7]");
//        System.out.println(st.countPairs(root, 3));

//        TreeNode root = st.stringToTreeNode("[1,2,3,null,5,null,4]");
//        System.out.println(st.rightSideView(root));

//        TreeNode root = st.stringToTreeNode("[3,1,4,null,2]");
//        System.out.println(st.kthLargest(root, 1));

//        System.out.println(st.isSymmetric(st.stringToTreeNode("[1,2,2,null,3,null,3]")));

//        System.out.println(st.add(1, 1));
//        System.out.println(st.add(-1, 2));

//        TreeNode root = st.stringToTreeNode("[1,2,3,null,null,4,5]");
//        String sel = st.serialize(root);
//        System.out.println(sel);
//        System.out.println(st.treeNodeToString(st.deserialize(sel)));

//        TreeNode r1 = st.stringToTreeNode("[1,3,2,5]");
//        TreeNode r2 = st.stringToTreeNode("[2,1,3,null,4,null,7]");
//        System.out.println(st.treeNodeToString(st.mergeTrees(r1, r2)));

//        TreeNode root = st.stringToTreeNode("[1,2,2,3,null,null,3,4,null,null,4]");
//        System.out.println(st.isBalanced(root));

//        TreeNode root = st.stringToTreeNode("[5,9,1,3,5,7]");
//        TreeNode root = st.stringToTreeNode("[1]");
//        TreeNode root = st.stringToTreeNode("[11,8,6,1,3,9,11,30,20,18,16,12,10,4,2,17]");
//        System.out.println(st.isEvenOddTree(root));

//        TreeNode root = st.stringToTreeNode("[1,4,5,4,4,5]");
//        System.out.println(st.longestUnivaluePath(root));

//        TreeNode root = st.stringToTreeNode("[5,2,-5]");
//        System.out.println(Arrays.toString(st.findFrequentTreeSum(root)));


//        TreeNode root = st.stringToTreeNode("[2,1,3]");
//        TreeNode root = st.stringToTreeNode("[1,1]");
//        TreeNode root = st.stringToTreeNode("[10,5,15,null,null,6,20]");
//        System.out.println(st.isValidBST(root));

        TreeNode root = st.stringToTreeNode("[3,1,4,null,2]");
        System.out.println(st.kthSmallest(root, 1));

    }
}

