import java.util.*;


/**
 * Created by Wayne.A.Z on 2020-10-14.
 */
public class Solution_search {

    // 315 二叉搜索树 TODO 专题
    Integer[] res315;
    public List<Integer> countSmallerBinarySearchTree(int[] nums) {
        res315 = new Integer[nums.length];
        Arrays.fill(res315, 0);

        TreeNode root = null;
        for (int i = nums.length - 1; i >= 0 ; i--) {
            root = addAndCount(root, new TreeNode(nums[i]), i, 0);
        }
        List<Integer> res = Arrays.asList(res315);
        return res;
    }
    public TreeNode addAndCount(TreeNode root, TreeNode node, int i,int sum){
        if(root == null){
            res315[i] = sum;
            return node;
        }
        if(root.val > node.val){
            root.count ++;
            root.left = addAndCount(root.left, node, i, sum);
        }else if(root.val < node.val){
            root.right = addAndCount(root.right, node, i, sum + root.count + 1);
        }else{
            root.right = addAndCount(root.right, node, i, sum + root.count);
        }
        return root;
    }
    class TreeNode{
        int val;
        int count;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
            this.count = 0;
            left = null;
            right = null;
        }
    }

    public static class ListNode { // 静态类 才能在下面的静态方法中被访问
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    // 127 BFS
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet();
        for(String w : wordList) wordSet.add(w);

        if(!wordSet.contains(endWord)) return 0;

        Queue<String> q = new ArrayDeque<>();
        q.add(beginWord);
        wordSet.remove(beginWord);

        int res = 2;

        while(!q.isEmpty()){

            int size = q.size();
            for (int i = 0; i < size; i++) {
                String word = q.poll();
                char[] wordArr = word.toCharArray();
                for (int j = 0; j < word.length(); j++) {
                    char tmp = wordArr[j];
                    for (char c = 'a'; c <= 'z'; c++) {
//                        if(c == tmp) continue;
                        wordArr[j] = c;
                        String nextWord = new String(wordArr);
//                        System.out.println(nextWord);
                        if(nextWord.equals(endWord)) {
//                            System.out.println(nextWord);
                            return res++;
                        }
                        if(wordSet.contains(nextWord)){
//                            System.out.printf("%d, %s\n", res, nextWord);
                            q.add(nextWord);
                            wordSet.remove(nextWord);
                        }
                        wordArr[j] = tmp;
                    }
                }
            }
            res ++;
        }
        return 0;
    }
    // TODO 126

    // 752
    public int openLock2(String[] deadends, String target) {
        String start = "0000";
        Set<String> dead = new HashSet();
        for (String d: deadends) dead.add(d);
        if (dead.contains(start)) return -1;

        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);

        Set<String> visited = new HashSet();
        visited.add(start);

        int steps = 0;
        while (!queue.isEmpty()) {
            ++steps;
            int size = queue.size();
            for (int s = 0; s < size; ++s) {
                String node = queue.poll();
                for (int i = 0; i < 4; ++i) {
                    for (int j = -1; j <= 1; j += 2) {
                        char[] chars = node.toCharArray();
                        chars[i] = (char)(((chars[i] - '0') + j + 10) % 10 + '0');
                        String next = new String(chars);
                        if (next.equals(target)) return steps;
                        if (dead.contains(next) || visited.contains(next))
                            continue;
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }
        }
        return -1;
    }
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>();
        for(String s: deadends) dead.add(s);
        if(dead.contains(target)) return -1;
        Queue<String> q = new ArrayDeque<>();
        q.add("0000");
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        int res = 0;
        while (!q.isEmpty()){
            res ++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                char[] curArr = cur.toCharArray();
                for (int j = 0; j < 4; j++) {

//
                    for (int k = -1; k <= 1; k+=2) {
                        char tmp = curArr[j];
//                        char[] curArr = cur.toCharArray();
                        curArr[j] = (char)((curArr[j] - '0' + k + 10) % 10 + '0');
                        String next = new String(curArr);
////                        System.out.println(next);
                        if(next.equals(target)) return res;
                        if(dead.contains(next) || visited.contains(next)) continue;
//                        System.out.println(next);
                        visited.add(next);
                        q.add(next);
                        curArr[j] = tmp;

                    }
//                    curArr[j] = tmp;
                }
            }
        }


//                    }
//                }
//            }
//        }
        return -1;
    }



    // 22 DFS
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        dfs(n, 0, 0, sb, res);
        dfs(n, 0, 0, "", res);
        return res;
    }
//    public void dfs(int n, int l, int r, StringBuilder sb, List<String> res){
    public void dfs(int n, int l, int r, String s, List<String> res){
        if(l < r) return;
        if(l == n && r == n){
            // TODO:不能用stringbuilder为什么
//            StringBuilder tmp = new StringBuilder(sb);
//            res.add(tmp.toString());
            res.add(s);
            return;
        }

//        if(l < n) dfs(n, l + 1, r, sb.append("("), res);
//        if(r < n) dfs(n, l, r + 1, sb.append(")"), res);
        if(l < n) dfs(n, l + 1, r, s + "(", res);
        if(r < n) dfs(n, l, r + 1, s + ")", res);
    }

    // 301
    public List<String> removeInvalidParentheses(String s) {
        int l = 0, r = 0;
        for(char c: s.toCharArray()){
            if(c == '(') l += 1;
            else if(c == ')'){
                if(l == 0) r += 1;
                else l -= 1;
            }
        }
        List<String> res = new ArrayList<>();
        dfs301(s, 0, l, r, res);
        return res;
    }
    public void dfs301(String s, int start, int l, int r, List<String> res){
        System.out.printf("%s, %d, %d\n", s, l, r);
        if(isValid(s) && l == 0 && r == 0) res.add(s);
        for (int i = start; i < s.length(); i++) {
            if(i != start && s.charAt(i) == s.charAt(i - 1)) continue;
            if(l > 0 && s.charAt(i) == '(') dfs301(s.substring(0, i) + s.substring(i + 1), i, l - 1, r, res);
            if(r > 0 && s.charAt(i) == ')') dfs301(s.substring(0, i) + s.substring(i + 1), i, l, r - 1, res);
        }
    }
    public boolean isValid(String s){
        char[] sArr = s.toCharArray();
        int cnt = 0;
        for(char c : sArr){
            if(c == '(') cnt ++;
            else if(c == ')') cnt --;
            if(cnt < 0) return false;
        }
        return cnt == 0;
    }

    // 37
    char[][] board37;
    int[][] row37 = new int[9][10];
    int[][] col37 = new int[9][10];
    int[][] box37 = new int[9][10];
    boolean solved = false;
    public void solveSudoku(char[][] board) {
        board37 = board;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board37[i][j] != '.') {
                    place(i, j, board37[i][j] - '0');
                }
            }
        }
        dfs37(0, 0);
    }
    public boolean couldPlace(int r, int c, int n){
        int idx = (r / 3) * 3 + c / 3;
        if(row37[r][n] > 0 || col37[c][n] > 0 || box37[idx][n] > 0) return false;
//        System.out.println("true");
        return true;
    }
    public void place(int r, int c, int n){
        row37[r][n] ++;
        col37[c][n] ++;
        int idx = (r / 3) * 3 + c / 3;
        box37[idx][n] ++;
        board37[r][c] = (char)('0' + n); // intger to char
//        System.out.println(board37[r][c]);
    }
    public void remove(int r, int c){
        int n = board37[r][c] - '0';
        row37[r][n] --;
        col37[c][n] --;
        int idx = (r / 3) * 3 + c / 3;
        box37[idx][n] --;
        board37[r][c] = '.';
    }

    public void placeNext(int i, int j){
        if (i == 8 && j == 8) {
            System.out.println("solved");
            solved = true;
        }
        else if (j == 8) dfs37(i + 1, 0);
        else dfs37(i, j + 1);
    }

    public void dfs37(int i, int j) {
        if (solved) return;

        if (board37[i][j] != '.') {
            placeNext(i, j);
        } else {
            for (int n = 1; n < 10; n++) {
//                System.out.printf("%d, %d, %d\n", i, j, n);
                if (couldPlace(i, j, n)) {
                    place(i, j, n);
                    placeNext(i, j);
                    if (!solved) remove(i, j);
                }
            }
        }
    }

    // 51
    char[][] board51;
    public List<List<String>> solveNQueens(int n) {
        board51 = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board51[i], '.');
        }
        List<List<String>> res = new ArrayList<>();
        dfs51(n, 0, res);
        return res;
    }
    public boolean couldPlace51(int n, int r, int c){
        for (int i = 0; i < r; i++) if(board51[i][c] == 'Q') return false;
        for (int i = 0; i < c; i++) if(board51[r][i] == 'Q') return false;
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) if(board51[i][j] == 'Q') return false; // 左上
        for (int i = r - 1, j = c + 1; i >= 0 && j < n; i--, j++) if(board51[i][j] == 'Q') return false; // 右上
        return true;

    }
    public void dfs51(int n, int i, List<List<String>> res){ //按行递归
        if(i == n){
            List<String> cur = new ArrayList<>();
            for (int rows = 0; rows < n; rows++) {
                StringBuilder sb = new StringBuilder();
                for (int cols = 0; cols < n; cols++) {
                    if(board51[rows][cols] == 'Q') sb.append('Q');
                    else sb.append('.');
                }
                cur.add(sb.toString());
            }
            res.add(cur);
//            return;
        }
        for (int j = 0; j < n; j++) {
            if(couldPlace51(n, i, j)) {
                board51[i][j] = 'Q';
                dfs51(n, i + 1, res);
                board51[i][j] = '.';
            }
        }

    }

    // 79
    char[][] board;
    String word;
    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        for(int i = 0;i < board.length;i ++){
            for(int j = 0;j < board[i].length;j ++){
                if(dfs(0, i, j)) return true;
            }
        }
        return false;
    }
    public boolean dfs(int start, int r, int c){
        if(start == word.length() - 1){
            if(word.charAt(start) == board[r][c]){
                return true;
            }else{
                return false;
            }
        }

        int[] d = {1, 0 , -1, 0, 1};
        if(board[r][c] == word.charAt(start)) {
            char tmp = board[r][c];
            board[r][c] = '0';
            for (int i = 0; i < 4; i++) {
                int newR = r + d[i];
                int newC = c + d[i + 1];
                if (newR < 0 || newR >= board.length || newC < 0 || newC >= board[0].length) continue;

                if (dfs(start + 1, newR, newC)) return true;
            }
            board[r][c] = tmp;
        }
        return false;
    }
    // 212 暴力。参考79加查找成功时也状态放回
    public List<String> findWords(char[][] board, String[] words) {
        Set<String> res = new HashSet<>();
//        List<String> res = new ArrayList<>();

        for (String word : words) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (dfs212(board, i, j, 0, word)) {
                        res.add(word);
//                        res2.add(word);
                    }
                }
            }
        }
//        for (int k = 0; k < board.length; k++) {
//            System.out.println(Arrays.toString(board[k]));
//        }
//        System.out.println(res2.toString());
        return new ArrayList<>(res);
    }
    public boolean dfs212(char[][] board, int r, int c, int start, String word){
//        System.out.println(word);
        if(start == word.length() - 1){
            return (word.charAt(start) == board[r][c]);
        }

        int[] d = {1, 0 , -1, 0, 1};
        if(board[r][c] == word.charAt(start)) {
            char tmp = board[r][c];
            board[r][c] = '0';
            for (int i = 0; i < 4; i++) {

                int newR = r + d[i];
                int newC = c + d[i + 1];
                if(r == 2 && c == 2 && newR == 2 && newC == 1) System.out.println("true");
//                if(r == 2 && c == 2) System.out.printf("%d %d\n" ,newR ,newC);
                if (newR < 0 || newR >= board.length || newC < 0 || newC >= board[0].length) continue;
                if (dfs212(board, newR, newC, start + 1, word)){
                    board[r][c] = tmp; //每次放回状态
                    return true;
                }
            }
            board[r][c] = tmp;
        }
        return false;
    }


    // 212 TODO: Trie

    // 542 bfs
    public int[][] updateMatrix(int[][] matrix) {
        Queue<int[]> q = new ArrayDeque<>();
        int M = matrix.length;
        int N = matrix[0].length;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if(matrix[i][j] == 0) q.add(new int[]{i, j}); // 转换成每个0到1的距离
                else matrix[i][j] = -1;// 未被访问过的1
            }

        }
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int[] d = {1, 0, -1, 0, 1};
            for (int i = 0; i < 4; i++) {
                int dx = x + d[i];
                int dy = y + d[i + 1];
                if(dx < 0 || dx >= M || dy < 0 || dy >= N) continue;
                if(matrix[dx][dy] == -1) {
                    matrix[dx][dy] = matrix[x][y] + 1;
                    q.add(new int[]{dx, dy});
                }
            }
        }
        return matrix;
    }
    // 675 bfs 问题抽象
    public int cutOffTree(List<List<Integer>> forest) {
        int M = forest.size();
        int N = forest.get(0).size();
        List<int[]> trees = new ArrayList<>(); // {h, r, c}
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int h = forest.get(i).get(j);
                if (h > 1){
                    trees.add(new int[]{h, i, j});
                }
            }
        }
        Collections.sort(trees, (o1, o2) -> o1[0] - o2[0]);

//        for (int i = 0; i < trees.size(); i++) {
//            System.out.println(Arrays.toString(trees.get(i)));
//        }
        int sr = 0, sc = 0, res = 0;
        // 每个树到下一棵树的距离
        for(int[] tree: trees){
            int dist = bfs675(forest, sr, sc, tree[1], tree[2]);
//            System.out.println(dist);
            if(dist == -1) return -1;
            res += dist;
            sr = tree[1]; sc = tree[2];
        }
        return res;

    }

    public int bfs675(List<List<Integer>> forest, int sr, int sc, int tr, int tc){
        Queue<int[]> q = new ArrayDeque<>(); // r, c, dist to former node
        q.add(new int[]{sr,sc,0});
        int M = forest.size();
        int N = forest.get(0).size();
        boolean[][] visited = new boolean[M][N]; // 不能直接在forest上修改的原因：到达下一点后，下一点到下下一点的过程中，前一次的路径上节点被置0
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int curR = cur[0];
            int curC = cur[1];
            int curD = cur[2];
            if(curR == tr && curC == tc) return curD;
            int[] d = {1, 0, -1, 0, 1};
            for (int i = 0; i < 4; i++) {
                int dx = curR + d[i];
                int dy = curC + d[i + 1];
                if(dx >= 0 && dx < M && dy >= 0 && dy < N && !visited[dx][dy] && forest.get(dx).get(dy) > 0){
//                    forest.get(dx).set(dy, 0);
                    visited[dx][dy] = true;
//                    System.out.printf("%d %d %d\n", dx, dy, curD);
                    q.add(new int[]{dx, dy, curD + 1});
                }
            }
        }
        return -1;
    }

    // 934
    public int shortestBridge(int[][] A) {
        Queue<int[]> q = new ArrayDeque<>();
        int M = A.length;
        int N = A[0].length;
        boolean found = false;
        for (int i = 0; i < M && !found; i++) {
            for (int j = 0; j < N && !found; j++) {
                if(A[i][j] == 1) {
                    dfs934(A, i, j, q);
                    found = true;
                }
            }

        }
//        System.out.println(q.size());

        int res = 0;
        int[] d = {1, 0, -1, 0, 1};
        while(!q.isEmpty()){
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] node = q.poll();
                for (int i = 0; i < 4; i++) {
                    int dx = node[0] + d[i];
                    int dy = node[1] + d[i + 1];
                    if (dx >= 0 && dx < M && dy >= 0 && dy < N) {
                        if (A[dx][dy] == 1) {
//                            System.out.printf("%d %d %d\n", dx, dy, node[0], node[1]);
                            return res;
                        }

                        if (A[dx][dy] == 0) {
                            A[dx][dy] = -1;
                            q.add(new int[]{dx, dy});
                        }

                    }
                }
            }
            res += 1;

        }
        return res;
    }
    public void dfs934(int[][] A, int r, int c, Queue<int[]> q){
        if(A[r][c] == 1) A[r][c] = 2;
        q.add(new int[]{r, c});
//        System.out.printf("%d %d\n", r, c);
        int[] d = {1, 0, -1, 0, 1};
        for (int i = 0; i < 4; i++) {
            int dx = r + d[i];
            int dy = c + d[i + 1];
            if (dx >= 0 && dx < A.length && dy >= 0 && dy < A[0].length && A[dx][dy] == 1) {
//                System.out.printf("%d %d %d\n", dx, dy, A[dx][dy]);
                dfs934(A, dx, dy, q);
            }
        }
    }



    public static void main(String[] args){
        Solution_search s = new Solution_search();

//        int[] nums = {5, 2, 6, 1};
//        System.out.println(s.countSmallerBinarySearchTree(nums));
        // 109
//        ListNode head = null;
//        s.sortedListToBST(head);
//        System.out.println(s.generateParenthesis(3));
//        System.out.println(s.removeInvalidParentheses("()())()"));
//        char[][] board = {{'5','3','.','.','7','.','.','.','.'},
//                {'6','.','.','1','9','5','.','.','.'},
//                {'.','9','8','.','.','.','.','6','.'},
//                {'8','.','.','.','6','.','.','.','3'},
//                {'4','.','.','8','.','3','.','.','1'},
//                {'7','.','.','.','2','.','.','.','6'},
//                {'.','6','.','.','.','.','2','8','.'},
//                {'.','.','.','4','1','9','.','.','5'},
//                {'.','.','.','.','8','.','.','7','9'}};
//        s.solveSudoku(board);
//        for (int i = 0; i < 9; i++) {
//            System.out.println(s.board37[i]);
//        }
//        System.out.println(s.solveNQueens(4));
//            char[][] board = {
//                    {'A', 'B', 'C', 'E'},
//                    {'S', 'F', 'C', 'S'},
//                    {'A', 'D', 'E', 'E'}
//            };
//            System.out.println(s.exist(board, "ABCCED"));
//            char[][] board = {{'A'}};
////            System.out.println(s.exist(board, "A"));
//        char[][] board = {
//                    {'A', 'B', 'C', 'E'},
//                    {'S', 'F', 'C', 'S'},
//                    {'A', 'D', 'E', 'E'}
//            };
////        String[] words = {"ABC","SABC","SC","CS"};
//        char[][] board = {
//                {'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}
//            };
//        String[] words = {"oath","pea","eat","rain"};
//        System.out.println(s.findWords(board, words));

//        int[][] matrix = {{0, 0, 0},{0, 1, 0},{0, 0, 0}};
//        s.updateMatrix(matrix);
//
//        List<List<Integer>> forest = new ArrayList<>();
//        forest.add(Arrays.asList(1,2,3));
//        forest.add(Arrays.asList(0,0,4));
//        forest.add(Arrays.asList(7,6,5));
//
//        List<List<Integer>> forest = new ArrayList<>();
//        forest.add(Arrays.asList(2,3,4));
//        forest.add(Arrays.asList(0,0,5));
//        forest.add(Arrays.asList(8,7,6));

//        List<List<Integer>> forest = new ArrayList<>();
//        forest.add(Arrays.asList(1,2,3));
//        forest.add(Arrays.asList(0,0,0));
//        forest.add(Arrays.asList(7,6,5));

//        List<List<Integer>> forest = new ArrayList<>();
//        forest.add(Arrays.asList(54581641,64080174,24346381,69107959));
//        forest.add(Arrays.asList(86374198,61363882,68783324,79706116));
//        forest.add(Arrays.asList(668150,92178815,89819108,94701471));
//        forest.add(Arrays.asList(83920491,22724204,46281641,47531096));
//        forest.add(Arrays.asList(89078499,18904913,25462145,60813308));
//        System.out.println(s.cutOffTree(forest));

//        int[][] A = new int[][]{{0, 1}, {1, 0}};
//        int[][] A = new int[][]{{0, 1, 0}, {0, 0, 0}, {0, 0, 1}};
//        int[][] A = new int[][]{{1,1,1,1,1},{1,0,0,0,1},{1,0,1,0,1},{1,0,0,0,1},{1,1,1,1,1}};
//        System.out.println(s.shortestBridge(A));

//        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
//        System.out.println(s.ladderLength("hit", "cog", wordList));

        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
        System.out.println(s.openLock(deadends, "0202"));

    }
}
