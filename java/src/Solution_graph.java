import java.util.*;
import java.util.LinkedList;

/**
 * Created by Wayne.A.Z on 2020-07-26.
 */
public class Solution_graph {
    public boolean canFinish(int numCourses, int[][] prerequisites){
        // 课程从0开始编号，用list<List<Integer>>做邻接表能get(courseID)
        // 相似的 802，用list<Set<Integer>>做邻接表
        // 一般的元素（char,非从0开始编号）最好做map
        List<List<Integer>> adjList = new ArrayList<>();// 转换为图的邻接链表表示
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            adjList.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if(!dfs207(adjList, visited, i)) return false;
        }
        return true;
    }
    public boolean dfs207(List<List<Integer>> adjList, int[] visited, int idx){
        if(visited[idx] == 1) return false;
        visited[idx] = 1;
        for (int i = 0; i < adjList.get(idx).size(); i++) {
            if(!dfs207(adjList, visited, adjList.get(idx).get(i))) return false;
        }
        visited[idx] = 0;
        return true;
    }
    // bfs 拓扑排序 法一：求indegree； 法二：写rgraph(见802)
    public boolean canFinishbfs(int numCourses, int[][] prerequisites){
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        int[] indegrees = new int[numCourses]; //
        for(int[] p: prerequisites){
            adjList.get(p[1]).add(p[0]);
            indegrees[p[0]] ++;
        }
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < numCourses; i++) {
            if(indegrees[i] == 0) q.add(i);
        }
        while(!q.isEmpty()){
            numCourses -- ;
            int c = q.poll();
            for(int suc: adjList.get(c)){
                indegrees[suc] --;
                if(indegrees[suc] == 0) q.add(suc);
            }
        }
        return numCourses == 0;
    }

    // 并查集

    // 802
    public List<Integer> eventualSafeNodes(int[][] G) {
        int N = G.length;
        List<Set<Integer>> graph = new ArrayList<>();
        List<Set<Integer>> rgraph = new ArrayList<>();
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            if(G[i].length == 0) q.addLast(i);
            for(int nei: G[i]){
                graph.get(i).add(nei);
                rgraph.get(nei).add(i);
            }
        }
        boolean[] safe = new boolean[N];
        while(!q.isEmpty()){
            int i = q.poll();
            safe[i] = true;
            for (int j : rgraph.get(i)) {
                graph.get(j).remove(i);
                if(graph.get(j).isEmpty()) q.addLast(j);
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if(safe[i]) res.add(i);
        }
        return res;
    }
    //
    // 764
    public int orderOfLargestPlusSign(int N, int[][] mines) {
        int[][][] dp = new int[N][N][4];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if((i == 0 || j == 0) && mines[i][j] == 1) {
                    dp[i][j][0] = 1;
                    dp[i][j][1] = 1;
                    dp[i][j][2] = 1;
                    dp[i][j][3] = 1;
                }
            }
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if(mines[i][j] == 1){
                    dp[i][j][0] = dp[i][j - 1][0] + 1;
                    dp[i][j][1] = dp[i - 1][j][1] + 1;
                }
            }
        }
        for (int i = N - 2; i > 0; i--) {
            for (int j = N - 2; j > 0; j--) {
                if(mines[i][j] == 1){
                    dp[i][j][1] = dp[i + 1][j][2] + 1;
                    dp[i][j][1] = dp[i][j + 1][3] + 1;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int min = Math.min(Math.min(dp[i][j][0], dp[i][j][1]), Math.min(dp[i][j][2], dp[i][j][3]));
                max = Math.max(max, dp[i][j][0]);
            }
        }
        return max;
    }
//    public int orderOfLargestPlusSign(int N, int[][] mines) {
//        int[][] dp = new int[N][N];
//        Set<Integer> zeros = new HashSet<>();
//        for(int[] m : mines){
//            zeros.add(m[0] * N + m[1]);
//        }
//
//        int res = 0;
//
//        for(int r = 0;r < N;r ++){
//            int cntOnes = 0;
//            for(int c = 0;c < N;c ++){ // 左到右
//                if(zeros.contains(r * N + c)) cntOnes = 0;
//                else cntOnes ++;
////                cntOnes = zeros.contains(r * N + c) ? 0 : cntOnes ++;
//                dp[r][c] = cntOnes;
//            }
////            System.out.println(Arrays.toString(dp[r]));
//            cntOnes = 0;
//            for(int c = N - 1;c >= 0;c --){ // 右到左
//                System.out.println(zeros.contains(r * N + c));
//                if(zeros.contains(r * N + c)) cntOnes = 0;
//                else cntOnes ++;
////                cntOnes = zeros.contains(r * N + c) ? 0 : cntOnes ++;
//                dp[r][c] = Math.min(dp[r][c], cntOnes);
//            }
//        }
//        for(int c = 0;c < N;c ++){
//            int cntOnes = 0;
//            for(int r = 0;r < N;r ++){ // 上到下
////                cntOnes = zeros.contains(r * N + c) ? 0 : cntOnes ++;
//                if(zeros.contains(r * N + c)) cntOnes = 0;
//                else cntOnes ++;
//
//                dp[r][c] = Math.min(dp[r][c], cntOnes);
//            }
//            cntOnes = 0;
//            for(int r = N - 1;r >= 0;r --){ // 下到上
////                cntOnes = zeros.contains(r * N + c) ? 0 : cntOnes ++;
//                if(zeros.contains(r * N + c)) cntOnes = 0;
//                else cntOnes ++;
//                dp[r][c] = Math.min(dp[r][c], cntOnes);
//                res = Math.max(res,dp[r][c]);
//            }
//        }
//        return res;
//    }

    // 好像是错的
    public boolean exist(char[][] board, String word) {
        int M = board.length;
        int N = board[0].length;
        for(int i = 0;i < M;i ++){
            for(int j = 0;j < N;j ++){
                if(backtrack(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }

    public boolean backtrack(char[][] board, String word, int start, int r, int c){
        int M = board.length;
        int N = board[0].length;
        if(r < 0 || r >= M || c < 0 || c >= N || board[r][c] != word.charAt(start)) return false;
        char tmp = board[r][c];
        board[r][c] = '0';
        if(backtrack(board, word, start + 1, r + 1, c) ||
                backtrack(board, word, start + 1, r - 1, c) ||
                backtrack(board, word, start + 1, r , c + 1) ||
                backtrack(board, word, start + 1, r , c - 1)) return true;
        board[r][c] = tmp;
        return false;
    }

    // 1719 dfs
//    List<Integer> res;
//    public int[] pondSizes(int[][] land) {
//        int M = land.length;
//        int N = land[0].length;
//
//        res = new ArrayList<>();
//        for(int i = 0;i < M;i ++){
//            for(int j = 0;j < N;j ++){
//                if(land[i][j] == 0){
//                    int cnt = dfs(i, j, land);
//                    if(cnt != 0) res.add(cnt);
//                }
//            }
//        }
//        return res.stream().mapToInt(i -> i).toArray();
//    }
//    public int dfs(int r, int c, int[][] land){
//        int cnt = 0;
//        if(r < 0 || r >= land.length || c < 0 || c >= land[0].length || land[r][c] != 0){
//            return cnt;
//        }
//        land[r][c] = -1;
//        cnt ++;
//        cnt += dfs(r - 1, c - 1, land);
//        cnt += dfs(r - 1, c, land);
//        cnt += dfs(r, c - 1, land);
//        cnt += dfs(r + 1, c - 1, land);
//        cnt += dfs(r - 1, c + 1, land);
//        cnt += dfs(r + 1, c + 1, land);
//        cnt += dfs(r + 1, c, land);
//        cnt += dfs(r, c + 1, land);
//        return cnt;
//    }

    // 1254
    /*
    [
    [0,0,1,1,0,1,0,0,1,0],
    [1,1,0,1,1,0,1,1,1,0],
    [1,0,1,1,1,0,0,1,1,0],
    [0,1,1,0,0,0,0,1,0,1],
    [0,0,0,0,0,0,1,1,1,0],
    [0,1,0,1,0,1,0,1,1,1],
    [1,0,1,0,1,1,0,0,0,1],
    [1,1,1,1,1,1,0,0,0,0],
    [1,1,1,0,0,1,0,1,0,1],
    [1,1,1,0,1,1,0,1,1,0]]
     */
    public int closedIsland(int[][] grid) {
        int res = 0;
        for(int i = 1;i < grid.length;i ++){ // 封闭，从1开始
            for(int j = 1;j < grid[0].length;j ++){
                if(grid[i][j] == 0){
//                    if(dfs(i, j, grid))
                    if(dfs( grid, i, j))
                        res ++;
                }
            }
        }
        return res;
    }


//    public boolean dfs(int r, int c, int[][] grid){
    private boolean dfs(int[][] grid,int r ,int c){
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
            return false;
        }
        if(grid[r][c] == 1) return true;
        grid[r][c] = 1;

//        if(dfs(grid,r-1,c) && dfs(grid,r+1,c) &&
//                dfs(grid,r,c-1) && dfs(grid,r,c+1)) return true;
//        如果前面的&&逻辑运算符不成立时，后面的dfs就不会执行了

        boolean up = dfs(grid,r-1,c);
        boolean down = dfs(grid,r+1,c);
        boolean left = dfs(grid,r,c-1);
        boolean right = dfs(grid,r,c+1);
        if(up && down && left && right){
            return true;
        }
        return false;
    }

//    private boolean dfs(int[][] grid,int i ,int j){
//        int rows = grid.length;
//        int cols = grid[0].length;
//        if(i < 0 || j < 0 || i >=rows || j >= cols){
//            return false;
//        }
//        if(grid[i][j] == 1){
//            return true;
//        }
//        grid[i][j] = 1;
//        boolean up = dfs(grid,i-1,j);
//        boolean down = dfs(grid,i+1,j);
//        boolean left = dfs(grid,i,j-1);
//        boolean right = dfs(grid,i,j+1);
//        if(up && down && left && right){
//            return true;
//        }
//        return false;
//    }


    public int movingCount(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n]; // 默认初始化为false
        return backtrack(0, 0, k, m, n, visited);
    }
    public int backtrack(int r, int c, int k, int m, int n, boolean[][] visited){
        if(r < 0 || r >= m || c < 0 || c >= n || visited[r][c] == true) return 0;
        int cnt = 0;
        if(check(r, c, k) == false) return cnt;
        visited[r][c] = true;
        System.out.printf("r: %d, c: %d\n",r, c);
        cnt = 1 + backtrack(r - 1, c, k, m, n, visited) + backtrack(r + 1, c, k, m, n, visited) + backtrack(r, c - 1, k, m, n, visited) + backtrack(r, c + 1, k, m, n, visited);
//        return 1 + backtrack(r - 1, c, k - 1, m, n) + backtrack(r + 1, c, k - 1, m, n) + backtrack(r, c - 1, k - 1, m, n) + backtrack(r, c + 1, k - 1, m, n);
//        visited[r][c] = false;
        System.out.printf("cnt: %d\n", cnt); // ?? 为什么这句只执行一次
        return cnt;
    }
    public boolean check(int r, int c, int k){ // 逻辑：threshold 不会变
        int sum = 0;
        System.out.printf("checking r: %d, c: %d\n",r, c);
        while(c != 0){
            sum += c % 10;
            c /= 10;
        }
        while(r != 0){
            sum += r % 10;
            r /= 10;
        }
        System.out.printf("sum: %d\n",sum);
        return sum <= k;
    }

    // 1391 BFS 模板
    public boolean hasValidPath(int[][] grid) {
        int M = grid.length;
        int N = grid[0].length;
        int[][] vectors = new int[][]{{-1, 0},{1, 0},{0, -1},{0, 1}}; //方向 上下左右
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});

        int[][] visited = new int[M][N];
        while(!q.isEmpty()){
            int[] next = q.poll();
            if(next[0] == M - 1 && next[1] == N - 1) return true;

            int path = 0;
            for(int[] vector: vectors){
                int a = next[0] + vector[0];
                int b = next[1] + vector[1];
                if(a >= 0 && a < M && b >= 0 && b < N && visited[a][b] == 0){
                    int before = grid[next[0]][next[1]];
                    int after = grid[a][b];
                    if(isOk(before, after, path)){
                        q.add(new int[]{a, b});
                        visited[a][b] = 1;
                        break;
                    }
                }
                path ++;
            }
        }
        return false;
    }

    // 733
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        Queue<int[]> q = new LinkedList<>(); //
        q.add(new int[]{sr, sc});
        int oldColor = image[sr][sc];

        if(oldColor == newColor) return image; // {{0,0,0},{0,1,1}}; 1,1,1

        image[sr][sc] = newColor;

        int[] dirs = {1, 0, -1, 0, 1};
        while(!q.isEmpty()){
            int[] cell = q.poll();

            for(int i = 0; i < 4;i ++){
                int r = cell[0] + dirs[i];
                int c = cell[1] + dirs[i + 1];
                if(r >= image.length || r < 0 || c >= image[0].length || c < 0 || image[r][c] != oldColor) continue;
                System.out.printf("r: %d, c: %d, imgrc: %d\n", r, c, image[r][c]);

                image[r][c] = newColor;
                q.add(new int[]{r, c});
            }
        }
        return image;
    }

    // TODO : 1162 ?
    public int maxDistance(int[][] grid) {
        int M = grid.length;
        int N = grid[0].length;

        Queue<int[]> q = new LinkedList<>();
        for(int i = 0;i < M;i ++){
            for(int j = 0;j < N;j ++){
                if(grid[i][j] == 1) q.add(new int[]{i, j});
            }
        }

        int hasOcean = 0, step = -1; // 超级原点
        int[] dirs = { 1, 0, -1, 0, 1};
        while(! q.isEmpty()){
            System.out.println(q.size());
            for(int j = 0;j < q.size();j ++){ // 处理一层
                int[] cell = q.poll();
                System.out.println(Arrays.toString(cell));
                for(int i = 0;i < 4;i ++){
                    int r = cell[0] + dirs[i];
                    int c = cell[1] + dirs[i + 1];
                    if(r >= 0 && r < M && c >= 0 && c < N && grid[r][c] == 0){
                        grid[r][c] = -1;
                        q.add(new int[]{ r, c});
                        hasOcean = 1;
                    }
                }
            }
            if(hasOcean == 1) step += 1;
        }
        return step;
    }

    // 51

    List<List<String>> res = new ArrayList<>();
    int n;
    char[][] board;

    public boolean couldPlace( int row, int col) {
        // 检查列是否有皇后互相冲突
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q')
                return false;
        }
        // 检查右上方是否有皇后互相冲突
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }
        // 检查左上方是否有皇后互相冲突
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }
        return true;
    }


    public void backtrack(int row){
        if(row == n){
            List<String> sol = new ArrayList<>();
            for(int i = 0;i < n;i ++){
                StringBuilder sb = new StringBuilder();
                for(int j = 0;j < n;j ++){
                    sb.append(board[i][j]);
                }
                sol.add(sb.toString());
            }
            res.add(sol);
            return;
        }
        for(int col = 0;col < n;col ++){
            if(couldPlace(row, col)){
                board[row][col] = 'Q';
                backtrack(row + 1);
                board[row][col] = '.'; // 状态恢复
            }
        }
    }

        public List<List<String>> solveNQueens(int n) {
            this.n = n;
            this.board = new char[n][n];
            for(int i = 0;i < n;i ++) Arrays.fill(board[i], '.');
            backtrack(0);
            return res;
        }


    public boolean isOk(int before, int after, int path){
        // TODO: 状态机（表驱动法）
        return true;
    }




    public static void main(String[] args) {
        Solution_graph s = new Solution_graph();
//        System.out.println(s.movingCount(1, 2, 1));
//        System.out.println(s.movingCount(3, 2, 17));

//        int[][] grid = {
//                {0, 0, 1, 1, 0, 1, 0, 0, 1, 0},
//                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
//                {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
//                {0, 1, 1, 0, 0, 0, 0, 1, 0, 1},
//                {0, 0, 0, 0, 0, 0, r1, 1, 1, 0},
//                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
//                {1, 0, 1, 0, 1, 1, 0, 0, 0, 1},
//                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 0, 0, 1, 0, 1, 0, 1},
//                {1, 1, 1, 0, 1, 1, 0, 1, 1, 0}};
//        System.out.println(s.closedIsland(grid));

//        int[][] images = {{1,1,1},{1,1,0},{1,0,1}};
//        int[][] images = {{0,0,0},{0,1,1}};
//        System.out.println(s.floodFill(images, 1, 1, 2));
//        System.out.println(s.floodFill(images, 1, 1, 1));

//        int[][] grid = {{1,0,1},{0,0,0},{1,0,1}};
//        System.out.println(s.maxDistance(grid));

//        String[][] sudoku = {{"5","3",".",".","7",".",".",".","."},{"6",".",".","1","9","5",".",".","."},{".","9","8",".",".",".",".","6","."},{"8",".",".",".","6",".",".",".","3"},{"4",".",".","8",".","3",".",".","1"},{"7",".",".",".","2",".",".",".","6"},{".","6",".",".",".",".","2","8","."},{".",".",".","4","1","9",".",".","5"},{".",".",".",".","8",".",".","7","9"}};
//        s.solveSudoku(sudoku);
//        System.out.println(sudoku);

//        System.out.println(s.solveNQueens(4));

//        System.out.println(s.canFinish(2, new int[][]{{1, 0}}));
        System.out.println(s.orderOfLargestPlusSign(5, new int[][]{{4,2}}));


    }
}

