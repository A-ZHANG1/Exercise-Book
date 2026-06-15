import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.*;
import java.util.Comparator;
import java.util.LinkedList;


/**
 * Created by Wayne.A.Z on 2020-06-20.
 */
public class Solution {
    public String tictactoe(String[] board) {
        int R = board.length;
        int C = R;
        int N = R;
        char[] row_arr;
        // row
        int row;
        for(int i = 0; i < R; i ++){
            row = 0;
            row_arr = board[i].toCharArray();
            for(char c : row_arr){
                row += Integer.valueOf(c);
            }
            if(row == Integer.valueOf('X') * C){
                return "X";
            }
            if(row == Integer.valueOf('O') * C){
                return "O";
            }
//            System.out.println(Integer.valueOf('X') * N);
//            System.out.println(row);
        }
        //col
        int col;
        int diag = 0;
        int diag_ = 0;
        int pending = 0;
//        System.out.println(Integer.valueOf('O') * N);
//        System.out.println(Integer.valueOf('X') * N);

        for(int j = 0; j < C; j ++){
            col = 0;
            for(int i = 0; i < R; i ++){
                if(board[i].charAt(j) == ' '){pending = 1;}
                col += board[i].charAt(j);
                //diag
                if(i + j == N - 1){
//                    System.out.println(board[i].charAt(j));
                    diag_ += board[i].charAt(j);
                }
                if(i == j){
//                    System.out.println(board[i].charAt(j));
                    diag += board[i].charAt(j);
                }
//                System.out.println(diag_);
                if(col == Integer.valueOf('X') * N || diag == Integer.valueOf('X') * N || diag_ == Integer.valueOf('X') * N){ return "X";}
                if(col == Integer.valueOf('O') * N || diag == Integer.valueOf('O') * N || diag_ == Integer.valueOf('O') * N){ return "O";}
            }
        }
        if(pending == 0){
            return "Draw";
        }else{
            return "Pending";
        }
    }

    // 65
    // TODO 表驱动法 状态机法
    /*

    * false:
    *  e. ..
    *  ee e前无num
    *  +-不在首或e后
    * 0e
     */
    public boolean isNumber(String s) {
        boolean numSeen = false, pointSeen = false, eSeen = false;
        for(int i = 0;i < s.length();i ++){
            if(s.charAt(i) >='0' && s.charAt(i) <= '9'){
                numSeen = true;
            }else if(s.charAt(i) == '.'){
                if(pointSeen || eSeen) return false;
                pointSeen = true;
            }else if(s.charAt(i) == 'e'){
                if(eSeen || !numSeen) return false;
                eSeen = true;
                numSeen = false;
            }else if(s.charAt(i) == '+' || s.charAt(i) == '-'){
                if(i != 0 && s.charAt(i - 1) != 'e') return false;
            }else{
                return false;
            }
        }
        return numSeen;
    }

        // 1144 分类讨论
    public int movesToMakeZigzag(int[] nums) {
//        int[] tmp = nums; //软拷贝,同一内存地址,内容内存仅仅存在1份
        int[] tmp = Arrays.copyOf(nums, nums.length);
        return Math.min(compute(tmp, 0),compute(nums, 1));// 分类讨论 偶数位较大 奇数位较大
    }

    private int compute(int[] nums, int idx){
        int l,r;
        int res = 0;
        for(int i = idx; i < nums.length; i += 2) {
            l = i - 1;
            r = i + 1;
            if (i > 0 && nums[l] >= nums[i]) {
                res += nums[l] - nums[i] + 1;
                nums[l] = nums[i] - 1;
            }
            if (i < nums.length - 1 && nums[r] >= nums[i]) {
                res += nums[r] - nums[i] + 1;
                nums[r] = nums[i] - 1;
            }
            System.out.println(Arrays.toString(nums)); // 打印数组元素debug
        }
        return res;
    }

    // 1414
//    public int findMinFibonacciNumbers(int k) {
//        int[] fiNumbers = {};
//        for(int i = 0; i < k; i++){
//            fiNumbers.add(fibonacciNumber(i));
//        }
//        return 1;
//    }
//
//    private int fibonacciNumber(int idx){
//        if(idx == 0 || idx == 1){
//            return 1;
//        }
//        return fibonacciNumber(idx-1) + fibonacciNumber(idx-2);
//    }
    public int findMinFibonacciNumbers(int k) {
        int dp[] = new int[45];
        dp[0] = 1;
        dp[1] = 1;
        // 求所有fib数。45: 10 ^ 9
        for(int i = 2;i<45;i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        System.out.println(Arrays.toString(dp));

        // 贪心
        int s = 0;
        for(int j = 44;j>=0;j--){
            if(k >= dp[j]){
                k = k - dp[j];
                s++;
            }
        }
        return s;
    }


    // 309 花花酱 从状态机看dp方程 https://www.bilibili.com/video/BV1qW411C7Xc/?spm_id_from=333.788.videocard.1
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int[][] dp = new int[len + 1][3]; // i: 天数，j 0, 1, 2分别对应hold, sold, rest
//        int[] hold = {Integer.MIN_VALUE}, sold = {0}, rest = {0};// 静态数组
        dp[0][0] = Integer.MIN_VALUE;
        dp[0][1] = 0;
        dp[0][2] = 0;
        for(int i = 1; i <= len; i ++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i - 1]);
            dp[i][1] = dp[i - 1][0] + prices[i - 1];
            dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2]);
        }
//        for(int i = 0; i <= len; i ++){
//            System.out.println(java.util.Arrays.toString(dp[i]));
//        }
        return Math.max(dp[len][1], dp[len][2]);
    }

    // 714 没有cooldown(rest)状态. 股票问题官方题解 https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/solution/yi-ge-fang-fa-tuan-mie-6-dao-gu-piao-wen-ti-by-lab/
    public int maxProfit(int[] prices, int fee) {
        int len = prices.length;
        int[][] dp = new int[len + 1][2]; // i: 天数. 0为初始状态,1为第一天；j 0, 1分别对应hold, sold
        dp[0][0] = Integer.MIN_VALUE;
        dp[0][1] = 0;
        for(int i = 1; i <= len; i ++){
//            int tmp = dp[i][0];
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i - 1] - fee); // hold
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i - 1]); // sold
        }
//        for(int i = 0; i <= len; i ++){
//            System.out.println(java.util.Arrays.toString(dp[i]));
//        }
        return dp[len][1];
    }

    // 122
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        int[][] dp = new int[len + 1][2];
        dp[0][0] = Integer.MIN_VALUE; // hold
        dp[0][1] = 0; // sold
        for(int i = 1; i <= len; i ++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i - 1]);
        }
        return dp[len][1];
    }


    // 16
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        int sum = 0;
        int res = nums[0] + nums[1] + nums[2];
        for(int i = 0;i < len - 2;i ++){
            int l = i + 1;
            int r = len - 1;
            while(l < r){
                sum = nums[i] + nums[l] + nums[r];
                if(Math.abs(sum - target) < Math.abs(res - target)){
                    res = sum;
                }
                if(sum > target){
                    r -= 1;
                }else if(sum < target){
                    l += 1;
                }else{
                    return res;
                }
            }
        }
        return res;
    }

    // 15
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0;i < len - 1;i ++){
            if(i > 0 && nums[i] == nums[i - 1]) continue; //剪枝
            int l = i + 1, r = len - 1;
            while(l < r){
                sum = nums[i] + nums[l] + nums[r];
                if(sum < 0){
                    l++; // 去重
                }
                else if(sum > 0){
                    r--;
                }
                else{
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while(l < r && nums[l] == nums[l + 1]) l++; // 去重
                    while(l < r && nums[r] == nums[r - 1]) r--;
                    l ++;
                    r --;

                }
            }
        }
        return res;
    }

    // 695 java 岛屿最大面积
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        for(int i = 0;i < m;i ++){
            for(int j = 0;j < n;j++){
                if(grid[i][j] == 0){
                    res = Math.max(res, dfs(i, j, grid));
                }
            }
        }
        return res;
    }

    private int dfs(int i, int j, int[][] grid){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0){
            return 0;
        }
        grid[i][j] = 0;
        int area = 1;
        area += dfs(i, j - 1, grid);
        area += dfs(i, j + 1, grid);
        area += dfs(i - 1, j, grid);
        area += dfs(i + 1, j, grid);
        return area;
    }

    // 162 找峰值。分三种情况讨论。找规律。 --> 二分查找O(log(n))852
    public int findPeakElement(int[] nums) {
        for(int i = 0;i < nums.length - 1;i ++){
            if(nums[i] > nums[i + 1]){
                return i;
            }
        }
        return nums.length - 1;
    }
    // 287
    public int findDuplicate(int[] nums){
        int l = 0, r = nums.length;
        while(l < r) {
            int mid  = l + (r - l) / 2;
            int cnt = 0;
            for(int n : nums) {
                if (n <= nums[mid]) cnt++;
            }
            if(cnt <= mid) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    // 剑指 42
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        int res = nums[0];
        dp[0] = nums[0];
        for(int i = 1;i < len;i ++){
            if(dp[i - 1] > 0){
                dp[i] = dp[i - 1] + nums[i];
            }else{
                dp[i] = nums[i];
            }
            res = Math.max(dp[i], res);
        }
        return res;
    }

    // 96 不同二叉搜索树个数
    public int numTrees(int n) {
        int[] res = new int[n + 1]; // java 数组被初始化为0
        res[0] = 1;
        res[1] = 1;
        for(int i = 2;i <= n;i ++){
            for(int j = i;j > 0;j --){
                res[i] += res[j - 1] * res[i - j]; // 以 i 为根节点的二叉树个数
            }
        }
        return res[n];
    }

    // 241 dp
    public boolean isOperation(char c){
        if(c == '+' || c == '-' || c == '*'){
            return true;
        }
        return false;
    }
    public int cal(int left, int right, char op){
        if(op == '+'){
            return left + right;
        }else if(op == '-'){
            return left - right;
        }else{
            return left * right;
        }
    }

    // 四重循环？以及下标还没有正确index.
    public List<Integer> diffWaysToCompute2(String input){
        int N = input.length();
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();
        int num = 0;
        for(int i = 0;i < N;i ++){
            char c = input.charAt(i);
            if(isOperation(c) == true){
                opList.add(c);
                numList.add(num);
                num = 0;

            }else {
                num = num * 10 + c - '0';
            }
        }
        numList.add(num); // 添加最后一个num(没有后接运算符)

//        System.out.println(java.util.Arrays.toString(numList.toArray()));
//        System.out.println(java.util.Arrays.toString(opList.toArray()));

        ArrayList<Integer>[][] dp = (ArrayList<Integer>[][]) new ArrayList[N][N];

        ArrayList<Integer> res = new ArrayList<>(); // Array of arrayList是不可行的。=> arrayList of arrayList
        for(int i = 0;i < N;i ++){
            for(int j = i;j > 0;j --){
                for(int s = j;s < i; s ++){
                    ArrayList<Integer> result1 = dp[j][s];
                    ArrayList<Integer> result2 = dp[s + 1][i];
                    for(int x = 0;x < result1.size();x ++){
                        for(int y = 0;y < result2.size();y ++){
                            res.add(cal(result1.get(x), result2.get(y), opList.get(s)));
                        }
                    }
                }
                dp[i][j] = res;
            }

        }
        return dp[0][N - 1];
    }

    //241 分治(递归)
//    public Map<String, List<Integer>> map = new HashMap<>(); // 已经计算过的字符串
//    public List<Integer> diffWaysToCompute(String input){
//        if(map.containsKey(input)) return map.get(input);
//        int len = input.length();
//        List<Integer> res = new ArrayList<>();
//        for(int i = 0;i < len;i ++){
//            char op = input.charAt(i);
//            if(op == '+' || op == '-' || op == '*'){
//                List<Integer> left = diffWaysToCompute(input.substring(0, i));
//                List<Integer> right = diffWaysToCompute(input.substring(i + 1, len));
//                for(int l: left){
//                    for(int r: right){
//                        switch(op){
//                            case '+':
//                                res.add(l + r);
//                                break;
//                            case '-':
//                                res.add(l - r);
//                                break;
//                            case '*':
//                                res.add(l * r);
//                                break;
//                        }
//                    }
//                }
//            }
//        }
//        // 递归到没有符号只有数字(组合)
//        if(res.size() == 0) {
//            System.out.println(input);
//            res.add(Integer.valueOf(input));
//        }
//        map.put(input, res);
//        return res;
//    }
    Map<String, List<Integer>> map241 = new HashMap<>();
    public List<Integer> diffWaysToCompute(String input) {
        if(map241.containsKey(input)) return map241.get(input);

        int len = input.length();
        List<Integer> res = new ArrayList<>();

        for(int i = 0;i < len;i ++){
            char c = input.charAt(i);
            if( c == '+' ||  c == '-' ||  c == '*'){
                List<Integer> left = diffWaysToCompute(input.substring(0, i));
                List<Integer> right = diffWaysToCompute(input.substring(i + 1, len));

                for(int l: left){
                    for(int r: right){
                        switch(c){
                            case '+': res.add(l + r);break;
                            case '-': res.add(l - r);break;
                            case '*': res.add(l * r);
                        }
                    }
                }
            }
        }

        if(res.size() == 0) {
            System.out.println(input);
            res.add(Integer.valueOf(input));
        }
        map241.put(input, res);
        return res;
    }

    // 209 O(n) 双指针
    public int minSubArrayLen(int s, int[] nums) {
        int l = 0, r = 0;
        int len = nums.length;
        int sum = 0;
        int res = Integer.MAX_VALUE; //
        if(len == 0) return 0;
        while(r < len){
            sum += nums[r];
            while(sum >= s){
                sum -= nums[l];
                int cur_len = r - l + 1;
                res = Math.min(res, cur_len);
//                System.out.printf("sum: %d, l: %d, r: %d, len: %d\n", sum, l, r, cur_len);
                l += 1;
            }
//            System.out.println(sum);
            r += 1;
        }
        return res == Integer.MAX_VALUE? 0: res;
    }

    // 240
    // TODO: 法二： https://www.cnblogs.com/grandyang/p/4669134.html
    public boolean searchMatrix(int[][] matrix, int target) {
        int M = matrix.length, N = matrix[0].length;
//        while (l < r){
//            int mid = l + (r - l) / 2;
//            if(matrix[mid][N - 1] < target) l = mid + 1;
//            else r = mid;
//        }
//        System.out.println(l);
        for (int i = 0; i < M; i++) {
            if(matrix[i][0] > target) continue;
            if(matrix[i][N - 1] < target) continue;
            if(binanrySearch(matrix[i], target)) return true;
        }
        return false;
    }
    public boolean binanrySearch(int[] nums, int target){
        int l = 0, r = nums.length;
        while(l < r){
            int mid = l + (r - l) / 2;
            if(nums[mid] < target) l = mid + 1;
            else r = mid;
        }
        return nums[l] == target;
    }


    // 26 同向指针
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0 ) return 0;
        int l = 0, r = 1; // l指向新数组，r迭代原始数组中的所有元素
        while(r < nums.length){
            if(nums[l] != nums[r]){
                l ++;
                nums[l] = nums[r];
            }
            r ++;
        }
        return l + 1;
    }
    // 80
    public int removeDuplicates2(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int l = 1, r = 2;
        while(r < nums.length){
            if(nums[l - 1] != nums[r]){
                l ++;
                nums[l] = nums[r];
            }
            r ++;
        }
        return l + 1;
    }

    // k
    public int removeDuplicates3(int[] nums, int k) {
        if(nums == null || nums.length == 0) return 0;
        int l = k - 1, r = k;
        while(r < nums.length){
            if(nums[l - k + 1] != nums[r]){
                l ++;
                nums[l] = nums[r];
            }
            r ++;
        }
        return l + 1;
    }

    // 407
    public int trapRainWater(int[][] heightMap) {
        int M = heightMap.length, N = heightMap[0].length;
        boolean[][] visited = new boolean[M][N];
        int res = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 || i == M - 1 || j == 0 || j == N - 1) {
                    pq.add(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }

        int[] dirs = {-1, 0, 1, 0, -1};
        while(!pq.isEmpty()){
            int[] poll = pq.poll();
            for(int k = 0;k < 4;k ++){
                int nx = poll[0] + dirs[k];
                int ny = poll[1] + dirs[k + 1];
                if(nx >=0 && nx < M && ny >=0 && ny < N && !visited[nx][ny]){
                   if(poll[2] > heightMap[nx][ny]) res += poll[2] - heightMap[nx][ny];
                   pq.add(new int[]{nx, ny, Math.max(poll[2], heightMap[nx][ny])});
                    visited[nx][ny] = true;
                }
            }
        }

        return res;
    }
    // 238
    public int[] productExceptSelf(int[] nums) {
        int N = nums.length;
        int[] productsUp = new int[N];
        int[] productsDown = new int[N];
        int[] res = new int[N];
        Arrays.fill(productsUp, 1);
        Arrays.fill(productsDown, 1);
        for(int i = 0;i < N;i ++){
            productsUp[N - i - 1] = i == 0 ? nums[N - i - 1] : nums[N - i - 1] * productsUp[N - i];
            productsDown[i] = i == 0 ? nums[i] : nums[i] * productsDown[i-1];

        }
//        System.out.println(Arrays.toString(productsUp));
//        System.out.println(Arrays.toString(productsDown));
        for(int i = 1;i < N - 1 ;i ++){
            res[i] = productsUp[i + 1] * productsDown[i - 1];
        }
        res[0] = productsUp[1];
        res[N - 1] = productsDown[N - 2];
        return res;
    }

    // 29
    public int divide(int dividend, int divisor) {
        if(dividend == Integer.MIN_VALUE){
            if(divisor == 1) return Integer.MIN_VALUE;
            if(divisor == -1) return Integer.MAX_VALUE;
        }
        long res = 0;
        boolean sign = (dividend > 0) ^ (divisor > 0); //
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while(dividend >= 0){
            dividend -= divisor;
            res ++;
        }
        res --;
        if(sign) {
            res = -res;
        }
        return (int)res;
    }
    // 30
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        if(words.length == 0) return res; // "",[]
        Map<String, Integer> allWords = new HashMap<>();

        for(String w: words){
            allWords.put(w, allWords.getOrDefault(w, 0) + 1);
        }

        int len = words[0].length();
        int num = words.length;
        for(int i = 0;i <= s.length() - len * num;i ++){
            Map<String, Integer> hasWords = new HashMap<>();
            for(int j = 0;j < num;j ++){
                String sub = s.substring(i + j * len, i + (j + 1) * len);
                System.out.println(sub);
                if(allWords.containsKey(sub)){
                    hasWords.put(sub, hasWords.getOrDefault(sub, 0) + 1);
                }else break;
                if(hasWords.get(sub) > allWords.get(sub)) {
                    j --;
                    break;
                }
                System.out.println(j);
                if(j + 1 == num) res.add(i);
            }

        }

        return res;
    }



        // 二分查找
    public int peakIndexInMountainArray(int[] A) {
        int l = 0, r = A.length - 1;
        while(l < r){
            int mid = l + (r - l) / 2;
            if(A[mid] < A[mid + 1]){ // 在上坡
                l = mid + 1; //
            }else{
                r = mid;
            }
//            System.out.printf("l: %d, r: %d, mid: %d\n",l,r,mid);
        }
        return l;
    }

    // 34
    public int[] searchRange(int[] nums, int target) {
        int l = 0, r = nums.length;
        int[] res = new int[]{-1, -1};
        while(l < r){
            int mid = l + (r - l) / 2;
            if(nums[mid] < target) l = mid + 1;
            else if(nums[mid] > target) r = mid;
            else{
                int resL = mid, resR = mid;
                while(nums[resL] == target) resL --;
                while(nums[resR] == target) resR ++;
                res = new int[]{ resL + 1, resR - 1};
                break;
            }
        }
        return res;
    }

    // 1011

    public boolean overCapacity(int[] weights,int mid, int D){
        int subSum = 0;
        int d = 1;
        for(int i = 0;i < weights.length;i ++) {
            subSum += weights[i];
            if (subSum > mid) {
                d++;
                subSum = 0;
                i --;

            }
        }
        if(d > D)
            return true;
        else{
//            System.out.println(d);
            return false;
        }

    }

    // 二分的区间范围是 [max(weights), sum(weights)]
    public int shipWithinDays(int[] weights, int D) {
        int len = weights.length;
//        int l = Arrays.stream(weights).max().getAsInt();
//        int r = Arrays.stream(weights).sum();
        int l = 0, r = 0;
        for (int weight : weights) {
            l = Math.max(weight, l);
            r += weight;
        }

        while(l < r){
            int mid = l + (r - l) / 2;
            System.out.println(mid);
            if(overCapacity(weights, mid, D)){
                l = mid + 1;
            }else {
                r = mid;
            }
        }
        return l;
    }

    // 347
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.printf("%d %d\n",entry.getKey(), entry.getValue());
        }

//        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> map.get(n2) - map.get(n1)); // 大顶堆.
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2)); // 默认是n1 - n2,小顶堆
        for(int key: map.keySet()){
            pq.add(key); // 符合堆的插入
            if(pq.size() > k){
                pq.poll(); // poll堆顶元素
//                System.out.println(pq.poll()); // poll堆顶元素
            }
        }
//         Iterator it = pq.iterator();
//         while(it.hasNext()){
//             System.out.println(it.next());
//         }
        int[] res = new int[k];
        int i = 0;
        while(!pq.isEmpty()){
            res[k-i-1] = pq.poll();
//            System.out.println(res[i]);
            i ++;
        }
        return res;
    }

    // 692
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        PriorityQueue<String> q = new PriorityQueue<>(k, (w1, w2) -> map.get(w1).equals(map.get(w2)) ?
                w2.compareTo(w1) : map.get(w1) - map.get(w2)); // minHeap
//        PriorityQueue<String> q = new PriorityQueue<>(k, (o1, o2) -> map.get(o1) - map.get(o2)); // minHeap
        // 如果超过Init capacity仍然会继续添加
        for(String s: words){
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        for(String word: map.keySet()){
            q.add(word);
            if(q.size() > k) q.poll();
        }
        List<String> res = new ArrayList<>();
//        for(int i = 0; i < k; i ++){
//            res.add(q.poll());
//        }
        while(!q.isEmpty()){
            res.add(q.poll());
        }
        Collections.reverse(res); // 如此倒转
        return res;
    }

    // 973
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((n1, n2) -> cal(n2) - cal(n1));
        for(int i = 0; i < points.length; i ++){
            maxHeap.add(points[i]);
            if(maxHeap.size() > K) maxHeap.poll();
        }
        int[][] res = new int[K][2];
        int i = 0;
        while(!maxHeap.isEmpty()){
            res[i] = maxHeap.poll();
            i ++;
//            System.out.println(Arrays.toString(res[i]));
        }
        return res;
}
    private int cal(int[] p){
        return p[0] * p[0] + p[1] * p[1];
    }

    // 502
    private class Project{
        int capital;
        int profit;

        private Project(int capital, int profit){
            this.capital = capital;
            this.profit = profit;
        }
    }

    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        PriorityQueue<Project> capital = new PriorityQueue<>(new Comparator<Project>(){
            public int compare(Project p1, Project p2){
                return p1.capital - p2.capital;
            }
        }); // minHeap
        PriorityQueue<Project> profit = new PriorityQueue<>(new Comparator<Project>(){
            public int compare(Project p1, Project p2){
                return p2.profit - p1.profit;
            }
        }); // maxHeap
        int N = Profits.length;
        Project[] projects = new Project[N];
        // (小顶堆)拿出所有资金可以做的项目，放入profit大顶堆
        for(int i = 0; i < N; i ++){
            projects[i] = new Project(Capital[i], Profits[i]);
        }
        for(int i = 0; i < N; i ++){
            capital.add(projects[i]);
            System.out.println(projects[i].capital);
        }
        int cnt = 0;
        while(cnt < k){
            System.out.println(capital.peek().capital);
            while(!capital.isEmpty() && capital.peek().capital <= W){
                Project p = capital.poll();
                System.out.println(p.capital);
                profit.add(p);
            }
            if(!profit.isEmpty()){
                Project chosen = profit.poll();
                System.out.println(chosen.profit);
                W = W + chosen.profit;
                cnt ++;
            } else{
                break;
            }
        }
        return W;
    }

    public int search(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        if(r == -1) return -1; //
        if(arr[l] > target){
            while(arr[l + 1] >= arr[l] && l < arr.length - 1){
                l += 1;
            }
            l += 1;
        } else{
            while(arr[r - 1] <= arr[l] && r > 1){
                r -= 1;
            }
            r -= 1;
        }
        while(l < r){
            int mid = l + (r - l) / 2;
            if(arr[mid] < target){
                l = mid + 1;
            } else {
                r = mid;
            }
        }
//        System.out.println(l);
//        System.out.println(r);
        return arr[l] == target ? l : -1;
    }

    // 853
    class Car{
        int position;
        double time;
        Car(int position, double time){
            this.position = position;
            this.time = time;
        }
        public String toString(){
            return this.position + "，"+ this.time;
        }
    }
    // 如果position在后面的车花更少的时间能到达目的地，那么一定在一个车队里
    // 问又几个车队，而不是一个车队几辆车
    public int carFleet(int target, int[] position, int[] speed) {
        int N = position.length;
        Car[] cars = new Car[N];
        for(int i = 0; i < N; i ++){
            cars[i] = new Car(position[i], (double)(target - position[i]) / speed[i]);
        }
        Arrays.sort(cars, (c1, c2) -> c1.position - c2.position); // 小的在前，从小到大
        System.out.println(Arrays.asList(cars).stream().map(e -> e.toString()).reduce("", String::concat)); // 打印array of Car object
        int res = 0, i = N - 1;
        for(i = N - 1; i > 0; i --){
            if(cars[i].time < cars[i - 1].time){ // i早到， i-1追不上，分开车队
                res += 1;
            } else {
                cars[i - 1].time = cars[i].time;
            }
        }
        return res+ (i == 0 ? 1 : 0);
//        Arrays.sort(cars, (a, b) -> Integer.compare(a.position, b.position));

    }

    // 56
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(l1, l2) -> l1[0] - l2[0]);

//        .sorted(new Comparator<int[]>(){
//	            public int compare(int[] l1, int[] l2){
//	                return l1[1] == l2[1] ? l2[0] - l1[0] : l2[1] - l1[1]; // 从高到低c
//            }
        System.out.println(intervals[0][0]);
        List<int[]> res = new ArrayList<>();
        for(int[] interval: intervals){

            if(res.size() == 0) res.add(interval);
            else{
                int[] lastInterval = res.get(res.size() - 1);
                if(lastInterval[1] < interval[0]){ // arrayList get last idx
                    res.add(interval);
                }else{
                    lastInterval[1] = Math.max(interval[1], lastInterval[1]);
                }
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    // 986
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> res = new ArrayList<>();
        int idxA = 0, idxB = 0;
        while(idxA < A.length && idxB < B.length){
            int l = Math.max(A[idxA][0], B[idxB][0]);
            int h = Math.min(A[idxA][1], B[idxB][1]);
            if(l <= h) res.add(new int[]{l, h});
            if(A[idxA][1] < B[idxB][1]) idxA ++;
            else idxB ++;
        }
        return res.toArray(new int[res.size()][2]);
    }

    // 438 滑窗
    public List<Integer> findAnagrams(String s, String p) { // 花花
        char[] arrS = s.toCharArray();
        char[] arrP = p.toCharArray();
        char[] need = new char[26];
        char[] window = new char[26];
        for(char c: arrP) {
            need[c - 'a'] += 1;
        }
        System.out.println(Arrays.toString(need));
        List<Integer> res = new ArrayList<>();
        int left = 0, right = 0;
        // offset增大
        while(right < arrS.length){
            int cur = arrS[right] - 'a';
            window[cur] += 1;
            right ++;
            while(window[cur] > need[cur]){
                System.out.println('h');
                // start左移
                int curL = arrS[left] - 'a';
                window[curL] -= 1;
                left ++;
            }
            System.out.println(Arrays.toString(Arrays.copyOfRange(arrS, left, right)));
            if(right - left == arrP.length) res.add(left);
        }
        return res;
    }


    // 39
//    List<List<Integer>> res;
    private void backtrack(int idx, int[] candidates, List<Integer> path, int target){
//        System.out.println(target);
//        System.out.println(path.stream().map(Object::toString)
//                .collect(Collectors.joining(", ")));
        Arrays.sort(candidates);
        if(target == 0) res.add(new ArrayList(path)); // 深拷贝 !!
        for(int i = idx; i < candidates.length; i ++){
            if(target - candidates[i] < 0){
                break;
            } else {
                // List<Integer> tmp = path;
                path.add(candidates[i]);
//                backtrack(i + 1, candidates, path, target - candidates[i]); // 允许重复
                backtrack(i, candidates, path, target - candidates[i]);
                path.remove(path.size() - 1);
            }
        }
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(0, candidates, path, target);
        return res;
    }

    // 216
    List<List<Integer>> res;
    public void backtrack(int idx, List<Integer> path, int target, int n, int k){
        System.out.println(path.stream().map(Object::toString)
        .collect(Collectors.joining(", ")));
        System.out.printf("target: %d, path size: %d, %b\n", target, path.size(), (target == 0 && path.size() == k));
        if(target < 0) return;
        if(target == 0 && path.size() == k){
            res.add(new ArrayList(path));
        }
        for(int i = idx; i <= 9; i ++){
//            System.out.println(i);
            path.add(i);
            backtrack(i + 1, path, target - i, n, k);
            path.remove(path.size() - 1);
        }
    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(1, path, n, n, k);
        return res;
    }

    // 32
    public int longestValidParentheses(String s) {
        if(s == null || s.length() == 0) return 0; // !
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 边界： s = "()";
        int res = 0;
        for(int i = 0; i < s.length();i ++){
            System.out.println(stack);
            if(s.charAt(i) == ')' && stack.size() >= 1 && s.charAt(stack.peek()) == '('){
                stack.pop();
                res = Math.max(res, i - stack.peek());
            }else{
                stack.push(i);
            }
        }
        return res;
    }
    // 84
    public int largestRectangleArea(int[] heights) {
        int N = heights.length;
        if(N == 1) return heights[0]; // [1]
        int[] rightZero = new int[N + 2];
        System.arraycopy(heights, 0, rightZero, 1, N);
        rightZero[N + 1] = 0;
        rightZero[0] = 0;
        System.out.println(Arrays.toString(rightZero));
        heights = rightZero;
        Deque<Integer> stack = new ArrayDeque<>(N + 2);
        int maxArea = 0;
        stack.addLast(0);
        for(int i = 1;i < heights.length;i ++){
            while (heights[i] < heights[stack.peekLast()]) {//                int left = stack.pollLast();

                int left = stack.pollLast();
                int curArea = heights[left] * (i - stack.peekLast() - 1);
                maxArea = Math.max(maxArea, curArea);
                System.out.printf("i:%d, stackPeek:%d, width:%d, height:%d, left:%d, curArea:%d\n",i, stack.peekLast(), i - stack.peekLast() - 1,heights[left], left, curArea);
            }
            stack.addLast(i);
        }
        return maxArea;
    }

    // 68
    public List<String> fullJustify(String[] words, int maxWidth) {
        int blank = 0, idx = 0, wrdCnt = 0;
        List<String> res = new ArrayList<>();

        while(idx < words.length) {
            blank = maxWidth;
            wrdCnt = 0;
            while(idx < words.length && blank >= words[idx].length()){
                blank -= words[idx].length();
                blank -= 1;
                idx ++;
                wrdCnt ++;
            }
            blank = blank + wrdCnt;
            StringBuilder tmp = new StringBuilder();
            System.out.printf("blank:%d\n", blank);
            if(wrdCnt == 1){
                tmp.append(words[idx - 1]);
                for (int i = 0; i < blank; i++) tmp.append(" ");
            }else{
                for (int i = wrdCnt; i >= 1; i --) {
                    System.out.printf("idx:%d, i:%d,  idx - i:%d\n", idx, i, idx - i);
                    tmp.append(words[idx - i]);
                    int avgBlank = blank / (wrdCnt - 1);
                    int resBlank = blank % (wrdCnt - 1);
                    System.out.printf("avgBlank:%d, resBlank:%d\n", avgBlank, resBlank);
                    if(i != 1) for (int j = 0; j < avgBlank; j++) tmp.append(" ");
                    if(i > wrdCnt - resBlank) tmp.append(" ");
                }
            }
            System.out.println(new String(tmp));
            res.add(new String(tmp));
        }
//        for(String s : res){
//            System.out.println(s);
//        }
        return res;
    }

    // 494
    int ans494 = 0;
    public int findTargetSumWays(int[] nums, int S) {

        helper(nums, S, 0);
        return ans494;
    }
    public void helper(int[] nums, int sum, int start){
        if(start == nums.length) {
            if (sum == 0) {
                ans494 += 1;
            }
            return;
        }else{
//        for (int i = start; i < nums.length; i++) { // 每条路径必须从nums[0]开始
            helper(nums, sum + nums[start], start + 1);
            helper(nums, sum - nums[start], start + 1);
//        }
        }
    }

    // 351



    // 312 区间动归 546消消乐 和 加左右隔板84 最大矩形区间
    public int maxCoins(int[] nums) {
        int N = nums.length;
        int[][] dp = new int[N + 2][N + 2];
        int[] vals = new int[N + 2];
        vals[0] = vals[N + 1] = 1;
        System.arraycopy(nums, 0, vals, 1, N);
        nums = vals;
//        System.out.println(Arrays.toString(nums));

        for(int i = N - 1;i >= 0;i --){
            for(int j = i + 2;j <= N + 1;j ++){
                for(int k = i + 1;k < j; k++){
                    dp[i][j] = Math.max(dp[i][j], nums[i]*nums[k]*nums[j] + dp[i][k] + dp[k][j]);
                }
            }
        }
//        for(int[] d: dp) System.out.println(Arrays.toString(d));
        return dp[0][N + 1];
    }


    // 41
    public int firstMissingPositive(int[] nums) {
        // 原地hash使数组满足nums[i] == nums[nums[i]-1].i.e 4 在 nums[3]
        for(int i = 0;i < nums.length;i ++){
            while(nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]){//交换nums[i]到最终位置
                swap(nums, i, nums[i] - 1);
            }
        }
        System.out.println(Arrays.toString(nums));
        for(int i = 0;i < nums.length;i ++){
            if(nums[i] != i + 1) return i + 1;
        }
        return nums.length + 1;//自己考虑这种情况
    }

    public void swap(int[] nums, int idxa, int idxb){ // 注意swap的引用传递
        int tmp = nums[idxa];
        nums[idxa] = nums[idxb];
        nums[idxb] = tmp;
    }

    // 90 subset
//    List<List<Integer>> res;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        res = new ArrayList();
        Arrays.sort(nums);
        dfs(0, new ArrayList<>(), nums);
        return res;
    }
    public void dfs(int left, List<Integer> path, int[] nums){
        res.add(new ArrayList<>(path));
        for(int i = left;i < nums.length;i ++){
            if(i > left && nums[i] == nums[i - 1]) continue;
            path.add(nums[i]);
            dfs(i + 1, path, nums);
            path.remove(path.size() - 1);
        }
    }

    // 47
//    List<List<Integer>> res;
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        res = new ArrayList<>();
        dfs(nums, new boolean[nums.length], new ArrayList());
        return res;

    }
    public void dfs(int[] nums, boolean[] visited, ArrayList<Integer> path){
        if(path.size() == nums.length) res.add(new ArrayList(path));
        for(int i = 0;i < nums.length;i ++){
            if(visited[i]) continue;
            if(i > 0 && nums[i] == nums[i - 1] && visited[i - 1]) break;
            path.add(nums[i]);
            visited[i] = true;
            dfs(nums, visited, path);
            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }


    // 125
    public boolean isPalindrome(String s) {

        StringBuilder sb = new StringBuilder();
        char[] sArr = s.toLowerCase().toCharArray(); // toCharArr会去掉空格
        for(char c: sArr){
            if(Character.isLetterOrDigit(c)) sb.append(c);
        }
        return sb.toString().equals(sb.reverse().toString());
    }

    // 139 单词能拆解成字典 dp
    public boolean wordBreak130(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for(int i = 1;i < s.length() + 1;i ++){
            for(int j = i;j >= 0;j --) {
                String word = s.substring(j, i);
//                if(word.equals("code")){ // == 比较对象内存地址
//                    System.out.println(j);
//                    System.out.println(java.util.Arrays.toString(dp));
//                }
                if(wordDict.contains(word) && dp[j] == true){ // dp[j]:[0, j-1]在字典中能截取
                    dp[i] = true;
                    break;
                }
            }
        }
//        System.out.println(wordDict.contains("code"));
        return dp[s.length()];
    }

    // 140 解1：dp 这个解法比Trie代码量小很多
    /*
    有个超时的用例
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
["a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"]
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        int N = s.length();

        List<String>[] dp = new ArrayList[N + 1];
//        System.out.println(dp[0]);
//        List<String> initial = Arrays.asList(""); // 等号右边是abstractlist
        List<String> initial = new ArrayList<>();
        initial.add("");
        dp[0] = initial; // 不能直接赋值
        for (int i = 1; i <= N; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                String str = s.substring(j, i);
                if(wordDict.contains(str) && dp[j].size() != 0){
                    for(String strj : dp[j]){
                        list.add(strj + (strj.equals("") ? str : " " + str));
                    }
                }
                dp[i] = list;
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[N];
    }

    // 140 解2 ：深搜 O(n3)
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet();
        for(String word: wordDict){
            wordSet.add(word);
        }
        int N = s.length();
        boolean[] dp = new boolean[N];
        for(int r = 0; r < N;r ++){
            if(wordSet.contains(s.substring(0, r + 1))){ // 所有substring r + 1从而取到r
                dp[r] = true;
                continue;
            }
            for(int l = 0; l < r; l ++){
                if(dp[l] && wordSet.contains(s.substring(l + 1, r + 1))){
                    dp[r] = true;
                    break;
                }
            }
        }

        List<String> res = new ArrayList<>();
        if(dp[N - 1]){
//            dfs(N - 1, new ArrayList(), res, wordSet, dp, s);
            dfs(N - 1, new LinkedList<>(), res, wordSet, dp, s);
            return res;
        }

        return res;
    }
    public void dfs(int r, LinkedList<String> path, List<String> res, Set<String> wordSet, boolean[] dp, String s){
        if(wordSet.contains(s.substring(0, r + 1))){
            path.add(0, s.substring(0, r + 1));
            StringBuilder sb = new StringBuilder();
            for(String tmp: path){
                sb.append(tmp);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            res.add(sb.toString());

            path.remove(0);
        }

        for(int l = 0;l < r;l ++){
            if(dp[l]){
                String suffix = s.substring(l + 1, r + 1);
                if(wordSet.contains(suffix)){
//                    path.addFirst(suffix);
                    path.add(0, suffix);
                    dfs(l, path, res, wordSet, dp, s);
                    path.remove(0);
//                    path.removeFirst();
//                    System.out.println(Arrays.toString(path.toArray()));
                }
            }
        }
    }

    // 140 解3 Trie 见 solution_tree

    // 10
    public boolean matches(char[] sArr, char[] pArr, int i, int j){
        if(i == 0) return false;
        if(pArr[j - 1] == '.') return true;
        return sArr[i - 1] == pArr[j - 1];
    }
    public boolean isMatch(String s, String p) {
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int M = sArr.length;
        int N = pArr.length;
        // dp[i][j] => is s[0, i - 1] match p[0, j - 1] ?
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;

        for(int i = 0;i <= M; i ++){
            for(int j = 1;j <= N; j ++){
//                System.out.println(pArr[j - 1]);
                if(pArr[j - 1] == '*'){
                    dp[i][j] = dp[i][j - 2];
//                    System.out.printf("i: %d, j: %d, dpij: %d", i, j, dp[i][j]);
                    if(matches(sArr, pArr, i, j - 1)) dp[i][j] |= dp[i - 1][j];
                } else {
                    if(matches(sArr, pArr, i, j)) dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[M][N];
    }

    // 44
    public boolean matches(int i, int j, String s, String p){
        if(i == 0) return false; // 不要让他去找i - 1和j - 1
        if(j == 0) return false;
        if(p.charAt(j - 1) == '?') return true;
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
    public boolean isMatch44(String s, String p) {
        int M = s.length(), N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;
        for (int i = 1; i <= N; i++) {
            if(p.charAt(i - 1) == '*') dp[0][i] = true;
            else break;
        }

        for(int i = 1;i < M + 1;i ++){
            for(int j = 1;j < N + 1;j ++){
                if(p.charAt(j - 1) == '*'){
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }else{
                    if(matches(i, j, s, p)) dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        for (int i = 0; i < M + 1; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        return dp[M][N];
    }

    // 91
    public int numDecodings(String s) {
        // 对0 分类讨论
        int[] dp = new int[s.length() + 1];
        char[] sA = s.toCharArray();
        if(sA[0] == '0') return 0; // s = "0" "01"
        dp[0] = 1;
        dp[1] = 1;
        char[] sArr = new char[s.length() + 1];
        System.arraycopy(sA, 0, sArr, 1, s.length());
        sArr[0] = 'p';
        for(int i = 2;i <= s.length();i ++){
            if(sArr[i] == '0'){
                if(sArr[i - 1] == '1' || sArr[i - 1] == '2'){
                    dp[i] = dp[i - 2];
                }
                else return 0;// 没有解码方式
            }else{
                if(sArr[i - 1] == '1' || (sArr[i - 1] == '2' && sArr[i] <= '6'))
                    dp[i] = dp[i - 1] + dp[i - 2];
                else{
                    dp[i] = dp[i - 1];
                }
            }
        }
        return dp[s.length()];
    }


    // 93
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if(s.length() > 12) return res; // 用例 11..1 (1008个1)
        dfs(s, 0, new ArrayList<>(), res);
        return res;
    }

    public void dfs(String s, int segStart, List<Integer> path, List<String> res){
        if(segStart == s.length() && path.size() == 4){
            StringBuilder sb = new StringBuilder();
            for(int p: path){
                sb.append(p);
                sb.append('.');
            }
            sb.deleteCharAt(sb.length() - 1);
            res.add(sb.toString());
            return;
        }
        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯

        if(segStart == s.length()){
            System.out.println(segStart);
            System.out.println(s.length());
            return;
        }

        int seg = 0;
        for(int i = segStart;i < s.length();i ++){
            seg = seg * 10 + s.charAt(i) - '0';
            if(s.charAt(segStart) == '0' && i - segStart >= 1) break;
            if(seg >= 0 && seg <= 0xFF){
                path.add(seg);
//                path.add(String.valueOf(seg));
//                System.out.println(path.toString());
                dfs(s, i + 1, path, res);
                path.remove(path.size() - 1);
            }else{
                break;
            }
        }

    }

    // TODO: 60
    StringBuilder result;
    public String getPermutation(int n, int k) {
        int[] fac = new int[n + 1];
        fac[0] = 1;
        for(int i = 1;i <= n;i ++) fac[i] = i * fac[i - 1];// fac[n] = n!
        boolean[] used = new boolean[n];
        result = new StringBuilder();
        return dfs(n, k, 0, result, used);
    }
    public String dfs(int n, int k, int start, StringBuilder sb, boolean[] used){
//        System.out.println(sb.toString());

        if(start == n) return sb.toString(); // start 可理解为当前层数
        int cur = factorial(n - start - 1);
        for(int i = 0;i < n ;i ++){
            if(used[i] == true) continue;
            if(cur < k){ //当前的排列组合数小于k，说明就算这一层排完了，也到不了第k个，剪
                k -= cur;
                continue;
            }
            sb.append(i + 1);
            used[i] = true;
//            System.out.println(i);
            return dfs(n , k, start + 1, sb, used);
//            sb.deleteCharAt(sb.length() - 1);
        }
        return null;
    }
    private int factorial(int n){
        int res = 1;
        while(n > 0) res *= n--;
        return res;
    }

    // 150
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack();
        for(String token: tokens){
//            if(Character.isDigit(token.charAt(0))){
            if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){
                int op2 = stack.pop();
                int op1 = stack.pop();
                int res = 0;
                switch(token){
                    case "+":
                        res = op1 + op2;
                        break;
                    case "-":
                        res = op1 - op2;
                        break;
                    case "*":
                        res = op1 * op2;
                        break;
                    case "/":
                        res = op1 / op2;
                        break;
                }
                stack.push(res);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.peek();
    }

    // TODO: treemap
    // stream
    public String restoreString(String s, int[] indices) {
        Map<Integer, Character> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < indices.length; i++) {
            map.put(indices[i], chars[i]);
        }
        map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return map.values().stream().map(String::valueOf).collect(Collectors.joining(""));
//        List<Character> res = map.values();
//        return String.valueOf(res);

    }


    public boolean isSubsequence(String s, String t) {
        int M = s.length(), N = t.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        System.out.println(dp[0][0]);
        for(int i = 0;i < N + 1;i ++) dp[0][i] = true;
        for(int i = 1;i < M + 1;i ++){
            for(int j = i;j < N + 1;j ++){

                if(s.charAt(i - 1) == t.charAt(j - 1)){
//                    System.out.println(dp[0][0]);
//                    System.out.printf("%d, %d", i, j);
//                    System.out.println(dp[1][1]);
                    dp[i][j] = dp[i - 1][j - 1];
//                    System.out.println(dp[1][1]);
                } else{
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        for(boolean[] r:dp){
            System.out.println(Arrays.toString(r));
        }
        return dp[M][N];
    }

    // 546 消消乐
    public int removeBoxes(int[] boxes){
        int[][][] dp = new int[100][100][100];
        return cal(boxes, dp, 0, boxes.length - 1, 0);
    }
    public int cal(int[] boxes, int[][][] dp, int l, int r, int k){
        if(l > r) return 0;
        if(dp[l][r][k] != 0) return dp[l][r][k];
        while(r > l && boxes[r] == boxes[r - 1]){
            r --;
            k ++;
        }
        dp[l][r][k] = cal(boxes, dp, l, r - 1, 0) + (k + 1) * (k + 1);
        for (int i = l; i < r; i ++) {
            if(boxes[i] == boxes[r]){
                dp[l][r][k] = Math.max(dp[l][r][k], cal(boxes, dp, l, i, k + 1) + cal(boxes, dp, i + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }
    // 221
    public int maximalSquare(String[][] matrix) {
        if(matrix.length == 0) return 0;
        int M = matrix.length, N = matrix[0].length, res = 0;
        int[][] dp = new int[M][N];
        for(int i = 0;i < M;i ++){
            for(int j = 0;j < N;j ++){
                if(i == 0 || j == 0) dp[i][j] = matrix[i][j] == "1"? 1 : 0;
                else if(matrix[i][j] == "1")
                {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1],dp[i - 1][j]), dp[i][j - 1]) + 1;
                }
                res = Math.max(res, dp[i][j]);
            }
            System.out.println(Arrays.toString(dp[i]));

        }
        return res * res;
    }
    // 85
    public int maximalRectangle(String[][] matrix) {
        if(matrix.length == 0) return 0;
        int M = matrix.length, N = matrix[0].length, area = 0;
        int[][] dp = new int[M][N];
        for(int i = 0;i < M;i ++){
            for(int j = 0;j < N;j ++){
                if(matrix[i][j] == "1"){
                    dp[i][j] = j > 0 ? dp[i][j - 1] + 1 : 1;
                    int width = N;
                    for(int k = i;k >=0;k --) {
                        if(matrix[k][j] == "0") break;
                        width = Math.min(width, dp[k][j]);
                        area = Math.max(area, width * (i - k + 1));
//                        System.out.printf("%d,%d,%d,%d\n",i, j, k, area);
                    }
                }
            }
//            System.out.println(Arrays.toString(dp[i]));
        }
        return area;
    }


    // 219
    public boolean containsNearbyDuplicate(int[] nums, int k) {
//        if(k == 0 || nums == null || nums.length ==0 || nums.length == 1) return false;
//        if(nums.length == 2) return nums[0]  == nums[1];
        int l = 0, r = 1;
        while(l <= nums.length - 2){
//            if(l == 2) System.out.println(nums[4] == nums[2]);
            r = l + 1;
            while(r - l <= k && r <= nums.length){
                if(r == 4) {
                    System.out.println(l);
                }
                if(nums[l] == nums[r]) return true;
                r ++;
            }
            l ++;
        }
        return false;
    }

    // 76
    Map<Character, Integer> map = new HashMap<>();
    Map<Character, Integer> cnt = new HashMap<>();

    public String minWindow(String s, String t) {
        for(int i = 0;i < t.length();i ++){
            char c = t.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        String res = s;
        int minLen = Integer.MAX_VALUE;
        int l = 0, r = -1, ansL = -1, ansR = -1;
        while(r < s.length()){
            r ++;
            if(r < s.length() && map.containsKey(s.charAt(r))) cnt.put(s.charAt(r), cnt.getOrDefault(s.charAt(r), 0) + 1);
            System.out.println(cnt.entrySet());
            while(l <= r && check()){
                if(r - l + 1 < minLen){
//                    System.out.println(l);
//                    System.out.println(r);
                    minLen = r - l + 1;
                    ansL = l;
                    ansR = r + 1;
                }
                char cl = s.charAt(l);
                if(map.containsKey(cl)) cnt.put(cl, cnt.getOrDefault(cl, 0) - 1);
                l ++;
            }

        }
        return ansL == -1 ? "" : s.substring(ansL, ansR);
    }

    public boolean check() {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Character key = (Character) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (cnt.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }

    // TODO 301
    List<String> ans = new ArrayList<>();
    public List<String> removeInvalidParentheses(String s) {
        int l = 0, r = 0;
        for(char c: s.toCharArray()){
            l +=  (c == '(') ? 1 : 0;
            if(l == 0) r += (c == ')') ? 1 : 0;
            else l -= c == ')' ? 1 : 0;
        }
        dfs(s, 0, l, r);
        return ans;
    }

    public void dfs(String s, int start, int l, int r){
        if(l == 0 && r == 0 && isValid(s)) {
//            String s_copy = s;
//            System.out.println(s_copy);
//            ans.add(s_copy);
            ans.add(s);
            return;
        }
        for(int i = start;i < s.length();i ++){
            if(i != start && s.charAt(i) == s.charAt(i - 1)) continue;// 剪枝："))"中删除前一个和后一个无区别
            if(s.charAt(i) == '(' || s.charAt(i) == ')'){
                if(r > 0 && s.charAt(i) == ')') dfs(s.substring(0, i) + s.substring(i + 1), i, l, r - 1);
                if(l > 0 && s.charAt(i) == '(') dfs(s.substring(0, i) + s.substring(i + 1), i, l - 1, r);
//
            }
        }
    }
    public boolean isValid(String s){
        int cnt = 0;
        for(char c: s.toCharArray()){
            if(c == '(') cnt ++;
            if(c == ')') cnt --;
            if(cnt < 0) return false;//右括号多于左括号
        }
        return cnt == 0;
    }

    // # 200 WC
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int N = arr.length;
        int res = 0;
        for(int i = 0;i < N - 2;i ++){
            for(int j = i + 1; j < N - 1;j ++){
                // if(j == i) continue;
                for(int k = j + 1;k < N;k ++){
                    // if(k == i || k == j) continue;
                    if(Math.abs(arr[i] - arr[j]) <= a && Math.abs(arr[k] - arr[j]) <= b && Math.abs(arr[i] - arr[k]) <= c) res ++;
                }
            }
        }
        return res;
    }
    // idx 指针后移代替数字转
    public int getWinner(int[] arr, int k) {
        int res = arr[0];
        k = Math.min(k, arr.length);// 简化判断

        int idx = 1, cnt = 0;
        while(idx < arr.length && cnt < k){
            if(res > arr[idx]){
                cnt ++;
            }else{
                cnt = 1;
                res = arr[idx];
            }
            idx ++;
        }
        return res;
    }
// 错的解
//    public int getWinner(int[] arr, int k) {
//        int N = arr.length;
//        int max = Integer.MIN_VALUE;
//        if(k < N){
//            for(int i = 0;i < N;i ++){
//                int l = i > 0 ? i - 1 : 0, r = i < N - 1 ? i + 1: N;
//                while(l >= 0 && arr[l] < arr[i]) l --;
//                while(r <= N - 1 && arr[r] < arr[i]) r ++;
//                if(r - l > k) return arr[i];
//            }
//        }else{
//            for(int i = 0;i < N;i ++){
//                max = Math.max(arr[i], max);
//            }
//        }
//        return max;
//    }

    public int minSwaps(int[][] grid){
        int N = grid.length;
        int cnt = 0;
        for(int i = 0;i < N;i ++){
            if(check(grid[i], i + 1)) continue;
            for(int j = i + 1; j < N; j ++){
                if(check(grid[j], i + 1)){
                    cnt += j - i;
                    int[] tmp = grid[j];
                    for(int k = j; k > i;k --){
                        grid[k] = grid[k - 1];
                    }
                    grid[i] = tmp;
                    break;
                }
                if(j == N - 1) return -1;
            }
        }
        return cnt;
    }

    public boolean check(int[] row, int i){
        for(int c = i;c < row.length; c ++){
            if(row[c] == 1) return false;
        }
        return true;
    }

//    public int hammingWeight(int n) {
//        int cnt = 0;
//        while(n != 0){
//            if((n & 1) == 1) cnt += 1;
//            n >>= 1;
//            System.out.println(n);
//        }
//        return cnt;
//    }
public int hammingWeight(int n) {
    int bits = 0;
    int mask = 1;
    for (int i = 0; i < 32; i++) {
        if ((n & mask) != 0) {
            bits++;
        }
        mask <<= 1;
    }
    return bits;
}

// 260
public int[] singleNumber(int[] nums) {
    int N = nums.length, sum = 0;
    for(int n: nums){
        sum ^= n;
    }

    int diff = 1;
    while((sum & 1) != 1){ // 运算符优先级
        sum >>= 1;
        diff <<= 1;
    }

    int a = 0, b = 0;
    for(int n: nums){
        if((n & diff) == 1){
            a ^= n;
        }else{
            b ^= n;
        }
    }
    return new int[]{a, b};
}

// 273
public String numberToWords(int num) {
    String[] list0 = {"", "Thousand", "Million", "Billion"};
    String[] list1 = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
            ,"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
    String[] list2 = {"Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
    if(num == 0) return "Zero";
    StringBuilder res = new StringBuilder();
    int idx = 0;
    while(num > 0){
        if(num % 1000 != 0){
            StringBuilder tmp = new StringBuilder();
            three(num % 1000, tmp, list1, list2);
            res.insert(0, tmp.append(list0[idx]).append(" "));
        }

        num /= 1000;
        idx ++;
    }
    return res.toString().trim();
}
public void three(int num, StringBuilder tmp, String[] list1, String[] list2){
        if(num == 0) return;
    if(num < 20) tmp.append(list1[num]).append(" ");
    else if(num < 100) three(num % 10, tmp.append(list2[num / 10 - 2]).append(" "), list1, list2);
    else three(num % 100, tmp.append(list1[num / 100]).append(" Hundred").append(" "), list1, list2);
}

public int nextGreaterElement(int n) {
    char[] num = String.valueOf(n).toCharArray();
    int N = num.length;
//    if(N == 1) return -1;
//    if(N == 2){
//        reverse(num, 0, 1);
//    }
    int i = N - 1, j = N - 1;
    while(i > 0 && num[i - 1] >= num[i]) i --;
    if(i == 0) return -1;

    while(j > i - 1 && num[j] <= num[i - 1]) j --;

    System.out.println(j);
    swap(num, i - 1, j);
    System.out.println(Integer.parseInt(String.valueOf(num)));

    reverse(num, i);
    return Integer.parseInt(String.valueOf(num));
}
public void reverse(char[] num, int l){
        int r = num.length - 1;
        while(l < r) {
            swap(num, l, r);
            l ++;
            r --;
        }
}

private void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 周赛 201
    public String makeGood(String s) {
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()){
            if(sb.length() != 0 && (sb.charAt(sb.length() - 1) == (char)(c + 32))){

                sb.deleteCharAt(sb.length() - 1);
            }
            else sb.append(c);
        }
        return sb.toString();
    }
    // 31
//    public void nextPermutation(int[] nums) {
//        int N = nums.length;
//        if(N == 0 || N == 1) return;
//
//        int i = N - 1;
//        while(i > 0 && nums[i - 1] >= nums[i]) i --;
//        if(i >= 1){ // 不是最后一个排列
//            int min = i;
//
//            for(int j = i; j < N;j ++){
//                if(nums[j] <= nums[min] && nums[j] > nums[i - 1]){
//                    min = j;
//                }
//            }
//            System.out.println(i);
//
//            int tmp = nums[i - 1];
//            nums[i - 1] = nums[min];
//            nums[min] = tmp;
//            System.out.println(Arrays.toString(nums));
//        }
//
//
//        int l = i, r = N - 1;
//        System.out.println(l);
//        System.out.println(r);
//        while(l < r){
//            int tmp = nums[r];
//            nums[r] = nums[l];
//            nums[l] = tmp;
//            l ++;
//            r --;
//        }
//        return;
//    }
    public void nextPermutation(int[] nums) {
        int N = nums.length;
        if(N == 0 || N == 1) return;
        int i = N - 1;
        while(i > 0 && nums[i] <= nums[i - 1]) i --;
        System.out.println(i);
        if(i == 0) {
            int l = 0, r = N - 1;
            while(l < r){
                swap31(nums, l ++, r --);
            }

        }else{
            int k = N - 1;
            while(nums[k] <= nums[i]) k --;
            swap31(nums, i, k);
        }

    }
    public void swap31(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
        return;
    }

    // jian 66
    public double[] twoSum(int n){
        int[][] dp = new int[n + 1][6 * n + 1]; // 已抛第n个筛子， 出现某点数的次数
        for (int i = 1; i <= 6 ; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for(int j = i;j <= 6 * i;j ++){
                for(int cur = 1; cur <= 6; cur ++){
                    if(cur >= j) break;
                    dp[i][j] += dp[i - 1][j - cur];
                }
            }
        }

        double[] res = new double[5 * n + 1];
        int idx = 0;
        for (int i = n; i <= 6 * n ; i++) {
            res[idx ++] = dp[n][i] / Math.pow(6, n);
        }
//        System.out.println(Arrays.toString(dp[n]));
//        System.out.println(idx);
        return res;
    }

    // jian 64
//    public int[] maxSlidingWindow(int[] nums, int k) {
//        if(nums == null || nums.length == 0) return nums;
//        Deque<Integer> index = new LinkedList<>();
//
//        for(int i = 0;i < k - 1;i ++){
//            while(index.size() > 0 && nums[index.getLast()] < nums[i]) index.removeLast();
//            index.addLast(i);
//        }
//        int N = nums.length;
//        int[] res = new int[N - k + 1];
//        for(int i = k - 1;i < N;i ++){
//            System.out.println(Arrays.toString(index.toArray()));
//            while(index.size() > 0 && nums[index.getLast()] < nums[i]){
//                index.removeLast();
//            }
//            System.out.println(Arrays.toString(index.toArray()));
//            index.addLast(i);
//            System.out.println(Arrays.toString(index.toArray()));
//            // if(index.size() > 0)
//            res[i - k + 1] = nums[index.getFirst()]; // 队首为最大元素
//            // else res[i - k + 1] = nums[i];
//
//            if(index.getFirst() == i - k + 1) index.removeFirst();
//        }
//        return res;
//    }
    // 239
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> window = new ArrayDeque<>();
        int[] res = new int[nums.length - k + 1];
        System.out.println(res.length);
        for(int i = 0;i < nums.length;i ++){
            while(!window.isEmpty() && nums[window.getLast()] < nums[i]) window.removeLast();
            window.addLast(i);

            if(window.getFirst() == i - k) window.removeFirst();
            System.out.println(window.toString());
            // if(!window.isEmpty() && window.peek() == nums[i - 1]) window.pop();
            if(i >= k - 1) res[i - k + 1] = nums[window.getLast()];
        }
        return res;
    }

    // jian 62
    public String reverseWords(String s) {
        s = s.trim();
        String[] strings = s.split(" ");
        int N = strings.length;
        String res = "";
        for(int i = N - 1;i >= 0;i --){
            System.out.println(strings[i]+ ".");
            if(strings[i].equals("")) continue;  // "a good   example"会分割出strings[i]==null
        }
        return res.trim();
    }

    // jian 61
    public int[][] findContinuousSequence(int target) {
        int l = 1, r = 2;
        List<int[]> res = new ArrayList<>();
        while(r <= target / 2 + 1){
            int sum = (l + r) * (r - l + 1) / 2;
            if(sum < target) r ++;
            else if(sum > target) l ++;
            else {
                int[] cur = new int[r - l + 1];
                int idx = 0;
                for(int i = l;i <= r;i ++) cur[idx++] = i;
                res.add(cur);
                r ++;

            }

        }
        return res.toArray(new int[res.size()][]);
    }

    // 334
    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MAX_VALUE;
        for(int n: nums){
            if(n <= min) min = n;
            else if(n <= max) max = n;
            else return true;
        }
        return false;
    }

    // 646
    public int findLongestChain(int[][] pairs) {
        int N = pairs.length;
        int[] dp = new int[N];
        Arrays.fill(dp, 1);
        Arrays.sort(pairs,(a, b) -> a[0] - b[0]); // sort为了减少遍历次数
        for(int i = 1;i < N;i ++){
            for(int j = 0;j < N;j ++){
                if(pairs[j][1] < pairs[i][0]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
//        System.out.println(Arrays.toString(dp));
        return Arrays.stream(dp).max().getAsInt(); // 不是最后一个而是
    }

    // 647
    public int countSubstrings(String s) {
        int N = s.length();
        boolean[][] dp = new boolean[N][N];
        int cnt = 0;
//        for(int i = 0;i < N;i ++){ // ->j
//            for(int j = i;j < N;j ++){ // ->i
        for(int j = 0;j < N;j ++){
            for(int i = 0;i <= j;i ++){ // 为了接受左下到右上的更新，先行后列的更新顺序错了
                if(s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])){
                    dp[i][j] = true;
                    System.out.printf("%d %d\n", i + 1, j - 1);
                    cnt ++;
                }
            }
        }
        return cnt;
    }


    // 394
    public String decodeString(String s) {
        Deque<Integer> stack_multi = new ArrayDeque<>();
        Deque<String> stack_str = new ArrayDeque<>();
        int multi = 0;
        StringBuilder res = new StringBuilder();
        for(char c: s.toCharArray()){
            if(c == '['){
                stack_multi.addLast(multi);
                multi = 0;
                stack_str.addLast(res.toString());
                res = new StringBuilder();
            }else if(c == ']'){
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.removeLast();
                for(int i = 0;i < cur_multi;i ++) tmp.append(res);
                res = new StringBuilder(stack_str.removeLast() + tmp);
            }else if(c >= '0' && c <= '9'){
                multi = multi * 10 + Integer.parseInt(c + "");
            }else{
                res.append(c);
            }
        }
        return res.toString();
    }

    // 227
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        char lastOp = '+';
        int res = 0;
        char[] sArr =  s.toCharArray();
        for(int i = 0;i < sArr.length;i ++){
            if(sArr[i] == ' ') continue; //

            if(Character.isDigit(sArr[i])){
                int tmp = 0;
                while(i < sArr.length && Character.isDigit(sArr[i])){
                    tmp = tmp * 10 + sArr[i] - '0';
                    i ++;
                }
                i --;
                if(lastOp == '+'){
                    stack.addLast(tmp);
                    System.out.println(stack.toString());
                }else if(lastOp == '-'){
                    stack.addLast(-tmp); // 记住是addLast 不是push
                }else{
                    stack.addLast(calc(stack.removeLast(), tmp, lastOp));
                }
//                if(i < sArr.length) lastOp = sArr[i];
            }else{
                lastOp = sArr[i];
            }
        }
        System.out.println(stack.toString());
        while(!stack.isEmpty()) res += stack.removeLast();
        return res;
    }
    public int calc(int a, int b, char c){
        if(c == '*') return a * b;
        else return a / b;
    }

    // 912 求逆序对
    // 1. merge sort 归并排序
    public int sortArray(int[] nums) {
        return merge(nums, 0, nums.length - 1);
    }
    public int merge(int[] nums, int left, int right){
        if(left >= right) return 0;
        int mid = left + (right - left) / 2;

        int l = merge(nums, left, mid);
        int r = merge(nums, mid + 1, right); // 含左含右
        return l + r + mergeSorted(nums, left, right, mid);
    }

    public int mergeSorted(int[] nums, int left, int right, int mid){
        // 先把值复制到临时数组，再合并回去
        System.out.printf("left: %d, right: %d\n", left, right);
        int count = 0;
        int[] tmp = new int[right - left + 1];
        int i = left, j = mid + 1;
        for(int k = 0; k < right - left + 1;k ++){
            if(i == mid + 1){
                tmp[k] = nums[j ++];
            }else if(j == right + 1){
                tmp[k] = nums[i ++];
            }else if(nums[i] <= nums[j]){
                tmp[k] = nums[i ++];
            }else{
                tmp[k] = nums[j ++];
                System.out.printf("mid: %d , i: %d\n", mid, i);
                count += mid - i + 1;
            }
        }
        for(int k = 0; k < right - left + 1;k ++){
            nums[left + k] = tmp[k];
        }
        return count;
    }

    // TODO: 493
    public int reversePairs(int[] nums){
        return merge493(nums, 0, nums.length - 1);
    }
    public int merge493(int[] nums, int left, int right){
        if(left >= right) return 0;
        int mid = left + (right - left) / 2;
        int l = merge493(nums, left, mid);
        int r = merge493(nums, mid + 1, right);

        return l + r + mergeSorted493(nums, left, right);
    }
    public int mergeSorted493(int[] nums, int left, int right){
        int mid = left + (right - left) / 2;
        int[] tmp = new int[right - left + 1];
        int j = mid + 1;
        int cnt = 0;
        // 归并前暴力求翻转对
//        System.out.println(Arrays.toString(nums));
        for (int i = left; i <= mid ; i++) {
            while(j <= right && nums[i] > 2 * nums[j ++]) {
                System.out.println("true");
                System.out.printf("%d, %d\n", i, j);
                cnt += j - i ;
            }
        }
        System.out.println(Arrays.toString(nums));

        // 归并
        int i = left;
        j = mid + 1;
        for(int k = 0; k <= right - left;k ++){
//            System.out.printf("%d, %d,%d, %d, %b,%b, %d\n", left, right, j, i,j > right,i > mid, k);
            if(i > mid) {
                tmp[k] = nums[j ++];
            }
            else if(j > right) tmp[k] = nums[i ++];
            else if(nums[i] <= nums[j]){
                tmp[k] = nums[i ++];
            }else{
                tmp[k] = nums[j ++];
            }
        }
        for (int k = 0; k <= right - left; k++) {
            nums[k + left] = tmp[k];
        }
        return cnt;
    }

        // 315
//    public List<Integer> countSmaller(int[] nums) {
//        List<Integer> res = new ArrayList<>();
//        int N = nums.length;
//        if(N == 0) return res;
//
//        int tmp = new int[N];
//        int[] res = new int[N];
//
//        mergeAndCount(nums, left, right, tmp);
//    }
//    public void mergeAndCount(int[] nums, int left, int right, int tmp){
//        if(left == right) return;
//        int mid = left + (left + right) / 2;
//        mergeAndCount(nums, left, mid, tmp);
//        mergeAndCount(nums, mid + 1, right, tmp);
//
//        for(int k = left;k <= right;k ++){
//            if(i > mid){
//                tmp[j];
//            }
//        }
//    }
        public List<Integer> countSmallerInsersionSort(int[] nums) {
            List<Integer> res = new ArrayList<>();
            int N = nums.length;
            for (int i = N - 1; i >= 0 ; i--) {
                int j = i + 1, tmp = nums[i];
                while(j < N && nums[j] >= tmp){

                }
            }
            return res;
        }

    public List<Integer> countSmallerBinarySearchTree(int[] nums) {
        List<Integer> res = new ArrayList<>();
        return res;
    }
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            pq.add(nums[i]);
        }

        Map<Integer, Integer> map = new HashMap<>();
        int rank = 1;
        while(!pq.isEmpty()){
            map.put(pq.poll(), rank);
            rank ++;
        }

        Fenwick tree = new Fenwick(nums);
        for (int i = nums.length - 1; i >= 0; i --) {
            rank = map.get(nums[i]);
            tree.update(rank ,1);
            res.add(tree.query(rank - 1));
        }
        Collections.reverse(res);

        return res;
    }

    // √ 327
    int[] sum;
    public int countRangeSum327(int[] nums, int lower, int higher){
        sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return countNum(nums, 0, nums.length - 1, lower, higher);
    }
    private int countNum(int[] nums, int left, int right, int lower, int higher){
        if(left == right){
            if(nums[left] >= lower && nums[right] <= higher) return 1;
            return 0;
        }
        int mid = left + (right - left) / 2;
        int total = 0;
        for (int i = left; i <= mid; i++) {
            for (int j = mid + 1; j <= right; j++) {
                int tmp = sum[j] - sum[i] + nums[i];
                if(tmp >= lower && tmp <= higher) total ++;
            }
        }
        return total + countNum(nums, left, mid, lower, higher) + countNum(nums, mid + 1, right, lower, higher);
    }
    // 327 写法2
    public int mergeCnt327(int[] sum, int left, int right, int lower, int upper){
        if(left >= right) return 0;
        int mid = left + (right - left) / 2;

        int cnt = 0;
        int i = left, j = mid + 1;
        for (int k = 0; k < right - left + 1; k++) {
            while(i <= mid && sum[i] - sum[k] < lower) i ++;
            while(j <= right && sum[j] - sum[k] <= upper) j ++;
            cnt += 1;
        }

        return cnt + mergeCnt327(sum, left, mid, lower, upper) + mergeCnt327(sum, mid + 1, right, lower, upper) ;
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        int N = nums.length;
        int[] sum = new int[N];
        sum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return mergeCnt327(sum, 0, nums.length - 1, lower, upper);
    }

    // √ BIT
    public int countRangeSum3(int[] nums, int lower, int upper) {
        int total = 0;
        Fenwick tree = new Fenwick(nums);
        for (int i = 0; i < nums.length; i++) {
            tree.update(i + 1, nums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                long sum = tree.query(j + 1) - tree.query(i);
                if( sum >= lower && sum <= upper) {
                    total ++;
                }
            }
        }
        return total;
    }

    // TODO: 327 BIT 解 + 离散化 + 前缀和
    public int countRangeSum2(int[] nums, int lower, int upper) {
        int total = 0;

        PriorityQueue<Long> pq = new PriorityQueue<>();
//       或者用 TreeSet<Long> ts = new TreeSet<>();
        long[] prefix = new long[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
            pq.add(prefix[i]);
//            System.out.printf("prefix: %d\n", prefix[i]);
            pq.add(prefix[i] - lower);
//            System.out.printf("added: %d\n", prefix[i] - lower);
//            System.out.printf("added: %d\n", prefix[i] - upper);
            pq.add(prefix[i] - upper);
        }

        Map<Long, Integer> map = new HashMap<>();
        int rank = 1;
        while(!pq.isEmpty()){
            long key = pq.poll();
//            System.out.printf("key: %d\n", key);
            map.put(key, rank);
            rank ++;
        }

        Fenwick tree = new Fenwick(nums);
        System.out.println(Arrays.toString(prefix));
        for (int i = 1; i <= nums.length; i++) {
            long val = prefix[i];
//            System.out.println(val - lower);
//            System.out.println(val - upper);
            int high_rank = map.get(val - lower);
            int low_rank = map.get(val - upper);
            rank = map.get(val);

            total += tree.query(high_rank) - tree.query(low_rank);
            tree.update(rank ,1);
        }

        return total;
    }

    // 64
    public int maximumGap(int[] nums) {
        int N = nums.length;
        if (N < 2) return 0; // 用例[10]
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int i = 0;i < N;i ++){
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        int size = Math.max((max - min) / N, 1); // 防止size为0

        Bucket[] bucket = new Bucket[max - min];

        for(int i = 0;i < N;i ++){
            int no = (nums[i] - min) / size;
//            System.out.println(no);
//            System.out.println(bucket[no]);
            if (bucket[no] == null) bucket[no] = new Bucket();// 对象数组初始化，防止空指针异常
            bucket[no].min = Math.min(bucket[no].min, nums[i]);
            bucket[no].max = Math.max(bucket[no].max, nums[i]);
        }

        int prevMax = -1;
        int maxGap = Integer.MIN_VALUE;
        for(int i = 0;i < bucket.length;i ++){
            if(bucket[i] != null && prevMax != -1){ // 空指针异常
                maxGap = Math.max(maxGap, bucket[i].min - prevMax);
            }
            if(bucket[i] != null) prevMax = bucket[i].max;
        }
        return maxGap;

    }
    class Bucket{
        int max;
        int min;
        public Bucket(){
            this.max = Integer.MIN_VALUE;
            this.min = Integer.MAX_VALUE;
        }
    }

    // 49
    public String encode(String a){
        StringBuilder sb = new StringBuilder();
        int[] arr = new int[26];
        for(int i = 0;i < a.length();i ++){
            int idx = a.charAt(i) - 'a';
            arr[idx] ++;
        }
        for(int i = 0;i < 26;i ++){
            sb.append(arr[i]);
            sb.append('#');
        }
        return sb.toString();
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        List<String> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for(String str: strs){
            String encoded = encode(str);
            List<String> l = map.getOrDefault(encoded, new ArrayList());
            l.add(str);
            map.put(encoded, l);
        }
        return new ArrayList(map.values()); // Collection<List<String>> -> ArrayList<List<String>>
    }

    // 324 O(n)时间 O(1)空间
    // 找中位数 方法类似快排
    // TODO
    int getMid(int[] nums, int l, int r, int rank){
        int cur = nums[l];
        int left = l, right = r;
        while(left < right){
            while(left < right && nums[right] >= cur) right--;
            nums[left] = nums[right];
            while(left < right && nums[left] <= cur) left ++;
            nums[right] = nums[left];
        }
        nums[left] = cur;
        if(left - l == rank) return cur;
        else if(left - l < rank) return getMid(nums, left + 1, r, rank - (left - l) + 1);
        return -1;
    }


    // 983
    public int mincostTickets(int[] days, int[] costs) {
        int N = days[days.length - 1];
        int[] dp = new int[N + 1];
        Set<Integer> daySet = new HashSet();
        for(int d : days){
            daySet.add(d);
        }

        for (int i = 1; i <= N ; i ++) {
            if(daySet.contains(i)){
                dp[i] = Math.min(Math.min(
                        dp[Math.max(0, i - 1)] + costs[0],
                        dp[Math.max(0, i - 7)] + costs[1]),
                        dp[Math.max(0, i - 30)] + costs[2]);
            }else{
                dp[i] = dp[i - 1];
            }
        }
//        System.out.println(Arrays.toString(dp));
        return dp[N];
    }

        // 629
    public int kInversePairs(int n, int k) {
        int M = 100000007;
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i ++){
            dp[i][0] = 1;
            for(int j = 1;j <= k && j <= i * (i - 1) / 2;j ++){
                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - 1]) % M;
            }
        }
        for(int i = 0;i <= n;i ++){
            System.out.println(Arrays.toString(dp[i]));
        }
        return dp[n][k];
    }

    // 775
    public boolean isIdealPermutation(int[] A) {
        int floor = A.length;
        for (int i = A.length - 1; i >= 2 ; i --) {
            floor = Math.min(floor, A[i]);
            if(A[i - 2] > floor) return false;
        }
        return true;
    }


    // TODO: 969
//    public List<Integer> pancakeSort(int[] A) {
//        int N = A.length;
//        List<Integer> res = new ArrayList<>();
//        for(int i = N - 1;i > 0;i --){
//            int idx = 0;
//            while(idx != i) idx ++;
//            res.add(idx + 1);
//            A = reverse(idx, A);
////            Collections.reverse(Arrays.asList(A))
//            System.out.println(Arrays.toString(A));
//            A = swap(i - 1, A);
//            res.add(i);
//            // res.add(i);
//        }
//        return res;
//    }
//    public int[] swap(int idx, int[] A){
//        int l = 0, r = idx - 1;
//        while(l < r){
//            int tmp = A[l];
//            A[l] = A[r];
//            A[r] = tmp;
//            l ++;
//            r --;
//        }
//        return A;
//    }

    // 6
    public String convert(String s, int numRows) {
        if(numRows < 2) return s; // "AB", 1
        List<StringBuilder> res = new ArrayList<>();
        for(int i = 0;i < numRows;i ++) res.add(new StringBuilder());
        int i = 0;
        int flag = -1;
        for(char c : s.toCharArray()){
             res.get(i).append(c);
            if(i == 0 || i == numRows - 1) flag = - flag;
            i += flag;
        }
        StringBuilder ans = new StringBuilder();
        for(StringBuilder str: res){
            ans.append(str);
        }
        return ans.toString();
    }

    // 8
    public int myAtoi(String str) {
        str = str.trim();

        int res = 0;
        int flag = 1;
        for(int i = 0;i < str.length();i ++){
            char c = str.charAt(i);
            if(i == 0 && c == '-'){ // "+-2"
                flag = -flag;
            } else if(i == 0 && c == '+'){
                flag = flag;
            } else if(c >= '0' && c <= '9'){
                if(res > (Integer.MAX_VALUE - c + '0') / 10){ // 如果大于MAX_VALUE或只能这样比较
                    return flag == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                }
                res = res * 10 + c - '0';
            }else{
                break;
            }
        }
        res = res * flag;
        return res;
    }


    // 149
    public int maxPoints(int[][] points) {
        if(points.length < 3) return points.length;
        int max = 0, res = 0, duplicate = 0;

        for(int i = 0;i < points.length;i ++){
            Map<String, Integer> map = new HashMap<>();
            duplicate = 0;
            for(int j = i + 1;j < points.length;j ++){

                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                if(dx == 0 && dy == 0) {
//                    System.out.printf("%d, %d, %d\n", j, points[j][0], points[j][1]);
                    duplicate ++;
                    continue;
                }
                int gcd = gcd(dx, dy);
                int y = dy / gcd;
                int x = dx / gcd;
                String k = x + "#" + y;
                map.put(k, map.getOrDefault(k, 0) + 1);
            }
            if(map.size() > 0) max = Collections.max(map.values());
//            System.out.println(duplicate);
            res = Math.max(res, max + duplicate + 1); // 1 当前考虑的点 duplicate重合的点
        }
        return res;
    }

    int gcd(int a, int b){
        while ((b != 0)){
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }

    public int maxProduct(int[] nums) {
        int mini = 1;
        int maxi = 1;
        int maxVal = Integer.MIN_VALUE;
        for(int i = 0;i < nums.length;i ++){
            if(nums[i] < 0){
                int tmp = mini; mini = maxi; maxi = tmp;
            }
            maxi = Math.max(nums[i], maxi * nums[i]);
            mini = Math.min(nums[i], mini * nums[i]);

            maxVal = Math.max(maxVal, maxi);
//            System.out.printf("%d, %d, %d, %d\n", nums[i], mini, maxi, maxVal);
        }
        return maxVal;
    }

    // 401
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        dfs(num, 0, 0, 0, res);
        return res;
    }
    public void dfs(int num, int start, int hour, int min, List<String> res){
        if(start == 11 || hour > 11 || min > 59) return;

        // if(num > 10 - start) return; // 亮灯数大于剩下的位置数
        if(num == 0){
            System.out.println(start);
            if(start == 9) System.out.println(min);
            String minStr = String.valueOf(min);
            if(min < 10) minStr = "0" + minStr;
            res.add(String.valueOf(hour) + ":" + minStr);
            return;
        }
        // 不选当前位置
        dfs(num, start + 1, hour, min, res);
        // 选当前位置
        if(start < 4) hour += 1 << start;
        else min += 1 << (start - 4);
        dfs(num - 1, start + 1, hour, min, res);
    }

    // 17
    Map<Integer, char[]> map17;
    public List<String> letterCombinations(String digits) {
        map17 = new HashMap<>(); // ！！map赋初值的方式
        map17.put(2, new char[]{'a','b','c'});
        map17.put(3, new char[]{'d','e','f'});
        map17.put(4, new char[]{'g','h','i'});
        map17.put(5, new char[]{'j','k','l'});
        map17.put(6, new char[]{'m','n','o'});
        map17.put(7, new char[]{'p','q','r','s'});
        map17.put(8, new char[]{'t','u','v'});
        map17.put(9, new char[]{'w','x','y','z'});

        List<String> res = new ArrayList<>();
        if(digits.length() == 0) return res; // 用例 ""
        dfs(digits, 0, new StringBuilder(), res);
        return res;
    }
    public void dfs(String digits, int start, StringBuilder path, List<String> res){
        if(start == digits.length()){
            res.add(path.toString()); // stringBuilder没有传引用的问题
            return;
        }
        char[] choices = map17.get(digits.charAt(start) - '0');
        for(int i = 0;i < choices.length;i ++){
            path.append(choices[i]);
            dfs(digits, start + 1, path, res);
            path.deleteCharAt(path.length() - 1);
        }
    }

    // 190 TODO: long n to String s 是78519472704。明明没有超过Long的界
    public int reverseBits(long n) {
        String s = "" + n;
        System.out.println(s);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8 ; i++) {
            sb.append(s.charAt(s.length() - i - 1));
        }
        for (int i = 8; i < 16; i++) {
            sb.append(s.charAt(i - 8));
        }
        System.out.println(sb.toString());
        return Integer.parseInt(sb.toString());
    }

    public int countPrimeSetBits(int L, int R) {
        int cnt = 0;
        for(int i = L;i <= R;i ++){
            int ones = Integer.bitCount(i);
            if(isPrime(ones)){
                cnt ++;
            }
        }
        return cnt;
    }
    public boolean isPrime(int n){
        if(n == 1) return false; // 1不是质数
        for(int i = 2;i <= Math.sqrt(n);i++){ // 注意等于号
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // 70, 198 滚动数组代替dp[], 减少异常处理情况： nums.length == 0 或1

//    public String longestPalindrome(String s) {
//
//        int N = s.length();
//        boolean[][] dp = new boolean[N][N];
//        int maxLen = 0, start = 0;
//        for (int j = 0; j < N; j++) {
//            for (int i = 0; i <= j; i++) {
//                if(i == j) dp[i][j] = true;
//                else if(s.charAt(i) == s.charAt(j)){
//                    if(j == i + 1) dp[i][j] = true;
//                    else dp[i][j] = dp[i + 1][j - 1];
//                }
//
//            }
//        }
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if(dp[i][j] && j - i > maxLen){
//                    maxLen = j - i;
//                    start = i;
//                }
//            }
////            System.out.println(Arrays.toString(dp[i]));
//        }
//        return s.substring(start, start + maxLen + 1);
//    }
public String longestPalindrome(String s) {
    int N = s.length();
    int[][] dp = new int[N][N];
    for (int i = N - 1; i > -1; i --) {
        for (int j = i ; j < N; j++) {
            if (i == j) dp[i][j] = 1;
            else if (s.charAt(i) == s.charAt(j)) {
                if (j == i + 1) dp[i][j] = 2;
                else dp[i][j] = dp[i + 1][j - 1] + 2;
            } else {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
    }
    for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
    int maxLen = 0, start = 0;
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            if (dp[i][j] > maxLen) {
                maxLen = dp[i][j];
                start = i;
            }
        }
    }
    return s.substring(start, start + maxLen);
}
//public int longestPalindromeSubseq(String s) {
//    int N = s.length();
//    int[][] dp = new int[N][N];
////    dp[0][0] = 1;
//    for (int i = N - 1; i > -1; i --) {
//        for (int j = i; j < N; j ++) {
//            if(i == j) dp[i][j] = 1;
//            else if(j == i + 1) dp[i][j] = 2;
//            else if(s.charAt(i) == s.charAt(j)) dp[i][j] = Math.max(Math.max(dp[i + 1][j], dp[i][j - 1]), dp[i + 1][j - 1] + 2);
//            else if(s.charAt(i) != s.charAt(j))dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
//        }
//
//    }
//    for (int i = 0; i < N; i++) {
//        System.out.println(Arrays.toString(dp[i]));
//    }
//    return dp[0][N - 1];
//}
public int longestPalindromeSubseq(String s) {
    int N = s.length();
    int[][] dp = new int[N][N];
    dp[0][0] = 1;
    for (int i = N - 1; i > -1; i --) {
        for (int j = i; j < N; j++) {
            if(i == j) dp[i][j] = 1;
            else if(s.charAt(i) == s.charAt(j) && j == i + 1) dp[i][j] = 2;
            else if(s.charAt(i) == s.charAt(j)) dp[i][j] = Math.max(Math.max(dp[i + 1][j], dp[i][j - 1]), dp[i + 1][j - 1] + 2);
            else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
        }

    }
    return dp[0][N - 1];
}


    public static void main(String[] args) {
        Solution s = new Solution();

//        String[] board = {"OX ","OX ","O  "};
//        String[] board = {" O    X"," O     ","     O ","XXXXXXX","  O    "," O   O ","O  X OO"};
//        String[] board = {"O X"," XO","X O"};
//        String[] board = {"   O","X OX"," O  ","O  X"};
//        String[] board = {"OX    O ","OX  X O ","XX  O O ","OX      "," X      "," X    O ","XX      ","OX  O O "
//        System.out.println(s.tictactoe(board));
//        int[] nums = {1,2,3};
//        int[] nums = {9,6,1,6,2};
//        int[] nums = {2,7,10,9,8,9};
//        int[] nums = {7,4,8,9,7,7,5};
//        System.out.println(s.movesToMakeZigzag(nums));
//        int[] prices = {1,2,3,0,2};
//        System.out.println(s.maxProfit(prices));
//        int[] prices = {1, 3, 2, 8, 4, 9};
//        System.out.println(s.maxProfit(prices, 2));
//        int[] prices = {7,1,5,3,6,4};
//        System.out.println(s.maxProfit2(prices));
//        System.out.println(s.findMinFibonacciNumbers(7));
//        int[] nums = {-1,2,1,-4};
//        System.out.println(s.threeSumClosest(nums, 1));
//        int[] nums = {-1, 0, 1, 2, -1, -4};
//        int[] nums = {-2, 0, 1, 1, 2};
//        int[] nums = {-2,0,0,2,2};
//        int[] nums = {-1,0,1,2,-1,-4};
//        int[] nums = {0, 0, 0, 0};
//        System.out.println(s.threeSum(nums));
//        String str = "leetcode";
//        List<String> wordDict = new ArrayList<>(Arrays.asList("leet", "code")); // List赋值
//        System.out.println(s.wordBreak(str, wordDict));
//
//        int[][] grid = {{0, 0, 0, 0}};
//        System.out.println(s.maxAreaOfIsland(grid));
//        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        System.out.println(s.maxSubArray(nums));
//        System.out.println(s.numTrees(3));
//        String input = "2-1-1";
//        String input = "2*3-4*5";
//        System.out.println(s.diffWaysToCompute(input));
//        int[] nums = {2,3,1,2,4,3};
//        System.out.println(s.minSubArrayLen(7, nums));
//        int[] A = {24,69,100,99,79,78,67,36,26,19};
//        System.out.println(s.peakIndexInMountainArray(A));
//        int[] weights = {1,2,3,4,5,6,7,8,9,10};
//        System.out.println(s.shipWithinDays(weights, 5));
//        int[] nums = {1,1,1,2,2,3};
//        int[] res = s.topKFrequent(nums, 2);
//        System.out.println(Arrays.toString(res));
//        String[] words1 = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
//        System.out.println(s.topKFrequent(words1, 4));
//
//        String[] words = {"i", "love", "leetcode", "i", "love", "coding"};
//        System.out.println(s.topKFrequent(words, 2));
//        int[][] points = {{3,3},{5,-1},{-2,4}};
//        int[][] res = s.kClosest(points, 2);
//        for(int[] r : res){
//            System.out.println(Arrays.toString(r));
//        }
//        int[] profit = {1, 2, 3};
//        int[] capital = {0, 1, 1};
//        System.out.println(s.findMaximizedCapital(2, 0, profit,capital));
//        int[] arr = {1, -2};
//        int[] arr = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
//        System.out.println(s.search(arr, 5));
//        int[] position = {10, 8, 0, 5, 3};
//        int[] speed = {2, 4, 1, 1, 3};
//        int[] position = {6, 8};
//        int[] speed = {3, 2};
//        System.out.println(s.carFleet(10, position, speed));
//        int[][] arr = {{1, 4}, {0, 4}};
//        System.out.println(s.merge(arr));
//        System.out.println(s.findAnagrams("cbaebabacd","abc"));
//        int[] arr = {2, 3, 6, 7};
//        System.out.println(s.combinationSum(arr, 7));
//        int[] arr = {8, 7, 4, 3};
//        System.out.println(s.combinationSum(arr, 11));
//        System.out.println(s.combinationSum3(3, 15));
//        System.out.println(s.isMatch("aa", "a*"));
//        System.out.println(s.longestValidParentheses(")()())"));
//        System.out.println(s.longestValidParentheses("(()"));
//        int[] nums = {2, 1};
//        System.out.println(s.firstMissingPositive(nums));
//        int[] nums = {1, 2, 2};
//        System.out.println(s.subsetsWithDup(nums));
//        int[] nums = {1, 1, 2};
//        System.out.println(s.permuteUnique(nums));
//        System.out.println(s.getPermutation(3, 1));
//        System.out.println(s.getPermutation(3, 2));
//        System.out.println(s.getPermutation(3, 3));
//        System.out.println(s.getPermutation(4, 2));
//        System.out.println(s.isPalindrome("A man, a plan, a canal: Panama"));

//        String[] l = {"cat","cats","and","sand","dog"};
//        List<String> dic = new ArrayList(Arrays.asList("cat","cats","and","sand","dog"));
//        System.out.println(s.wordBreak2("catsanddog", dic));
//        System.out.println(s.wordBreak2("catsandog", dic));
//        List<String> dic = new ArrayList(Arrays.asList("apple", "pen", "applepen", "pine", "pineapple"));
//        System.out.println(s.wordBreak2("pineapplepenapple", dic));

//        String[] in = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
//        System.out.println(s.evalRPN(in));

//        int[] indices = {1, 0, 2};
//        System.out.println(s.restoreString("art", indices));

//        System.out.println(s.isSubsequence("abc", "ahbgdc"));
//        int[] nums = {0, 1, 2, 3, 2, 5};
//        System.out.println(s.containsNearbyDuplicate(nums, 3));

//        System.out.println(s.minWindow("ADOBECODEBANC", "ABC"));
//        System.out.println(s.minWindow("a", "a"));
//        System.out.println(s.removeInvalidParentheses("(a)())()"));
//        int[] arr = {1, 1, 2, 2, 3};
//        int[] arr = {3,0,1,1,9,7};
//        System.out.println(s.countGoodTriplets(arr, 0, 0, 1));
//        System.out.println(s.countGoodTriplets(arr, 7, 2, 3));

//        int[] arr = {1,11,22,33,44,55,66,77,88,99};
//        System.out.println(s.getWinner(arr, 3));
//        int[] arr = {2,1,3,5,4,6,7};
//        System.out.println(s.getWinner(arr, 2));

//        int[][] grid = {{0, 0, 1}, {1, 1, 0}, {1, 0 ,0}};
//        System.out.println(s.minSwaps(grid));

//        int x = Integer.parseInt("11111111111111111111111111111101", 2);
////        System.out.println(s.hammingWeight(x));

//        System.out.println(s.nextGreaterElement(230241));
//        System.out.println(s.nextGreaterElement(12));

//        System.out.println(s.makeGood("abBAcC"));
//        System.out.println(s.makeGood("Pp"));

//        System.out.println(s.restoreIpAddresses("25525511135"));
//        System.out.println(s.restoreIpAddresses("010010"));
//        System.out.println(s.restoreIpAddresses("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"));

//        int[] nums = {3, 2, 1};
//        int[] nums = {1, 2, 3};
//        int[] nums = {5, 1, 1};
//        int[] nums = {2,3,1,3,3};
//        int[] nums = {1, 2};
//        s.nextPermutation(nums);
//        System.out.println(Arrays.toString(nums));
//        System.out.println(s.isNumber("-1e."));
//        System.out.println(Arrays.toString(s.twoSum(2)));
//        int[] nums2 = {1, -1};
//        System.out.println(Arrays.toString(s.maxSlidingWindow(nums2,1)));
//        int[] nums = {1,3,-1,-3,5,3,6,7};
//        System.out.println(Arrays.toString(s.maxSlidingWindow(nums,3)));

//        System.out.println(s.reverseWords("a good   example"));
//        int[][] res = s.findContinuousSequence(9);
//        for(int[] r : res){
//            System.out.println(Arrays.toString(r));
//        }

//        int[] boxes = {1,3,2,2,2,3,4,3,1};
//        System.out.println(s.removeBoxes(boxes));

//        int[] nums = {5, 7, 7, 8, 8, 10};
//        System.out.println(Arrays.toString(s.searchRange(nums, 8)));

//        int[] heights = {2, 1, 5, 6, 2, 3};
//        int[] heights = {0, 1};
//        int[] heights = {1, 1};
//        int[] heights = {2,1,4,2,3};
//        System.out.println(s.largestRectangleArea(heights));
//        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        String[] words = {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
//        System.out.println(s.fullJustify(words, 16));
        System.out.println(s.fullJustify(words, 20));

        //        int[] nums = {2, 4, -2, -3};
//        System.out.println(s.increasingTriplet(nums));

//        int[][] pairs = {{-10,-8},{8,9},{-5,0},{6,10},{-6,-4},{1,7},{9,10},{-4,7}};
//        System.out.println(s.findLongestChain(pairs));

//        System.out.println(s.decodeString("3[a]2[bc]"));

//        System.out.println(s.calculate("3+2*2"));

//        System.out.println(s.countSubstrings("aaa"));
//        System.out.println(s.countSubstrings("abc"));
//        System.out.println(s.countSubstrings("fdsklf"));
//        int[] coins = {3,1,5,8};
//        System.out.println(s.maxCoins(coins));

//        int[] nums = {3,2,4,1};
//        System.out.println(s.pancakeSort(nums));

//        System.out.println(s.convert("LEETCODEISHIRING", 3));

//        System.out.println(s.myAtoi("  -0012a42"));
//        System.out.println(s.myAtoi("2147483646"));
//        System.out.println(s.isMatch44("aa", "aa"));
//        System.out.println(s.isMatch44("aa", "*"));
//        System.out.println(s.isMatch44("aa", "a*"));
//        System.out.println(s.isMatch44("acdcb", "a*c?b"));
//        System.out.println(s.isMatch44("aab", "c*a*b"));
//        int[] nums = {1, 2, 3, 4};
//        System.out.println(Arrays.toString(s.productExceptSelf(nums)));

//        int[] nums = {1, 1, 2};
//        System.out.println(s.removeDuplicates(nums));
//        System.out.println(Arrays.toString(nums));

//        int[] nums = {1,1,1,2,2,3};
//        System.out.println(s.removeDuplicates2(nums));
//        System.out.println(Arrays.toString(nums));

//        System.out.println(s.divide(-2147483648, -1));
//        System.out.println(s.divide(-2147483648, 1));

//        String[] words = {"foo","bar"};
//        System.out.println(s.findSubstring("barfoothefoobarman", words));

//        // 91
//        System.out.println(s.numDecodings("12"));
//        System.out.println(s.numDecodings("27"));
//        System.out.println(s.numDecodings("1212"));
//        System.out.println(s.numDecodings("110"));

//        int[] nums = {7,5,6,4};
//        System.out.println(s.sortArray(nums));

//        System.out.println(s.kInversePairs(3, 1));
//        int[] nums = {1, 1, 1, 1, 1};
//        System.out.println(s.findTargetSumWays(nums, 3));
//
//        int[] A = {1, 0, 2};
//        System.out.println(s.isIdealPermutation(A));

//        int[] nums = {-2, 5, -1};
//        System.out.println(s.countRangeSum2(nums, -2, 2));

//        int[] days = {1,4,6,7,8,20};
//        int[] costs = {2,7,15};
//        System.out.println(s.mincostTickets(days, costs));

//        int[][] A = {{0,2},{5,10},{13,23},{24,25}};
//        int[][] B = {{1,5},{8,12},{15,24},{25,26}};
//        System.out.println(Arrays.toString(s.intervalIntersection(A, B)));

//        int[] nums = {5,2,6,1};
//        System.out.println(s.countSmaller(nums).stream().map(Object::toString).collect(Collectors.joining(",")));

//        int[] nums = {3,6,9,1};
//        System.out.println(s.maximumGap(nums));

//        int[] nums = {1, 5, 1, 1, 6, 4};
//        System.out.println(s.getMid(nums, 0, nums.length - 1, nums.length /

//        int[][] points = {{0, 0},{1, 1},{0, 0}};
//        int[][] points = {{0, 0},{0, 0},{0, 0}};
//        System.out.println(s.maxPoints(points));

//        int[] nums = {2, 3, -2, 4};
//        int[] nums = {-2, 0, -1};
//        System.out.println(s.maxProduct(nums));

//        int[] nums = {1,3,2,3,1};
//        int[] nums = {2,4,3,5,1};
//        System.out.println(s.reversePairs(nums));

//        System.out.println(s.readBinaryWatch(1));

//        System.out.println(s.letterCombinations("23"));

//        System.out.println(s.reverseBits(0001111010011100L));
//        System.out.println(s.countPrimeSetBits(6, 10));

//        String[][] matrix = {{"1","0","1","0","0"},{"1","0","1","1","1"},{"1","1","1","1","1"},{"1","0","0","1","0"}};
//        String[][] matrix = {};
//        String[][] matrix = {{"1","0","1","0"},{"1","0","1","1"},{"1","0","1","1"},{"1","1","1","1"}};
//        String[][] matrix = {{"1","0","1","1","1"},{"0","1","0","1","0"},{"1","1","0","1","1"},{"1","1","0","1","1"},{"0","1","1","1","1"}};
//        System.out.println(s.maximalSquare(matrix));
//        System.out.println(s.maximalRectangle(matrix));

//        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
//
//        System.out.println(s.searchMatrix(matrix, 5));

//        System.out.println(s.numberToWords(1234567));
//        int[] nums = {1,3,2,3,4};
//        System.out.println(s.findDuplicate(nums));
//        System.out.println(s.longestPalindrome("cbbd"));
//        System.out.println(s.longestPalindrome("babad"));
//        System.out.println(s.longestPalindrome("aacabdkacaa"));
//        System.out.println(s.longestPalindromeSubseq("abcdef"));
//        System.out.println(s.longestPalindromeSubseq("cbbd"));
    }
}
class Fenwick{
    int[] sums;

    public Fenwick(int[] nums){
        this.sums = new int[nums.length + 1];
    }
    public void update(int idx, int delta){
        while(idx < sums.length){
            sums[idx] += delta;
            idx += idx & -idx;
//            System.out.printf("%d, %d\n", delta, idx);
        }
    }
    public int query(int idx){
        int sum = 0;
        while(idx > 0){
            sum += sums[idx];
            idx -= idx & -idx;
        }
        return sum;
    }
}
