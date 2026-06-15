package companyCompetition; /**
 * Created by Wayne.A.Z on 2020-08-15.
 */



import java.util.*;


// 美团点评2020校招系统开发方向笔试题
public class Meituan {
//    public static void main(String args[]){
//        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//
//        // 获取输入值
////        int n = scanner.nextInt();
//        String a = scanner.nextLine();
//        String b = scanner.nextLine();
//
//        a = a.substring(1, a.length() - 1);
//        b = b.substring(1, b.length() - 1);
//
//        int intA = Integer.parseInt(a);
//        int intB = Integer.parseInt(b);
//        int sum = intA + intB;
//
//        // 输出
//        out.println(sum);
//
//        //刷新缓冲区
//        out.flush();
//    }


    public int numPalindrome(String s){
        if(s == null || s.length() == 0) return 0;
        if(s.length() == 1) return 1;

        int N = s.length();
        int ans = N;
        int[][] dp = new int[N][N];
        for (int i = N - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for(int j = i + 1;j < N;j ++){
                if(s.charAt(i) == s.charAt(j) && (i == j - 1 || dp[i + 1][j - 1] == 1)){
                    dp[i][j] = 1;
                    ans ++;
                }
            }
        }
        return ans;
    }
    //TODO:  1000 花花  区间dp模板
    public int mergeStones(int[] stones, int K){
        int N = stones.length;

        if((N - 1) % (K - 1) != 0) return -1;

        // 求前缀
        int[] prefixSum = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }

        // 初始化
        int[][][] dp = new int[N][N][K + 1]; // start, end, 堆。表示将 [i, j] 区间的石头缩小成 k 堆的最小体力花费
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
            dp[i][i][1] = 0;
        }

        // 区间dp模板
        for(int len = 2;len <= N; len++){ // sub problem length
            for(int i = 0;i <= N-len;i ++){
                int j = i + len - 1;
                for(int k = 2;k <= K;k ++){
                    for(int m = i;m < j;m += K-1){ // m 跳步应该是K-1,不应该用k-1;
                        dp[i][j][k] = Math.min(dp[i][j][k],dp[i][m][1]+dp[m+1][j][k-1]);
                    }
                    dp[i][j][1] = dp[i][j][k] + prefixSum[j + 1] - prefixSum[i];
                }
            }
        }
        return dp[0][N - 1][1];
    }

//    public void split(String[] strs){
//        int N = strs.length;
//        int[] match_count = new int[N];
//        for(int i = 0;i < N;i ++){
//            for(int j = i + 1;j < N;j ++){
//                int cur_match_count = 0;
//                while(strs[i].charAt(cur_match_count) < strs[j].charAt(cur_match_count)) cur_match_count ++;
//                if(match_count[i] < cur_match_count) match_count[i] = cur_match_count;
//                if(match_count[j] < cur_match_count) match_count[j] = cur_match_count;
//            }
//        }
//        String[] res = new String[N];
//        for(int i = 0;i < N;i ++){
//            res[i] =
//        }
//    }

    // 牛客 腾讯 2020 校招 后台
    public void decode(String s){
        StringBuilder res = new StringBuilder();

        Deque<Integer> numStack = new ArrayDeque<>();
        Deque<String> strStack = new ArrayDeque<>();
        StringBuilder tmp = new StringBuilder();
        int multi = 0;
        for(Character c: s.toCharArray()){

            if(c >= 'A' && c <= 'Z') {
                res.append(c);
            }
            if(c >= '0' && c <= '9') {
                multi = multi * 10 + c - '0';

            }
            if(c == '|'){
                numStack.push(multi);
                multi = 0;

            }
            if(c == '[') {
                strStack.push(new String(res));
                res = new StringBuilder();
            }
            if(c == ']') {
                int num = numStack.pop();
                tmp = new StringBuilder();
                for(int n = 0; n < num;n ++){
                    tmp.append(res);
                }
                res = new StringBuilder(strStack.pop() + tmp);
            }
        }
        System.out.println(res.toString());
    }

    public void building(int N, int[] heights){
        int[] res = new int[N];
        Deque<Integer> stkL = new ArrayDeque<>();
        Deque<Integer> stkR = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            res[i] += stkL.size();
            while(!stkL.isEmpty() && stkL.peek() <= heights[i]) stkL.pop();
            stkL.push(heights[i]);
        }
        for (int i = N - 1; i >= 0; i --) {
//            System.out.println(stkR.toString());
            res[i] += stkR.size();
            while(!stkR.isEmpty() && stkR.peek() <= heights[i]) stkR.pop();
            stkR.push(heights[i]);
        }

        for(int i = 0;i < N - 1; i ++){
            System.out.printf("%d ", res[i] + 1);
        }
        System.out.printf("%d", res[N - 1] + 1);
    }

    public int qWork(int n, int[] work, int[] keep){
        int[][] dp = new int[3][n + 1]; // 休息， 工作， 健身
        for (int i = 0; i < 3; i++) {
            Arrays.fill(dp[i], n);
        }
        dp[0][0] = 1;
        if(work[0] == 1) dp[1][0] = 0;
        if(keep[0] == 1) dp[2][0] = 0;
        for (int i = 1; i < n; i++) {
            dp[0][i] = Math.min(Math.min(dp[0][i - 1], dp[1][i - 1]), dp[2][i - 1]) + 1;
            if(work[i] == 1) dp[1][i] = Math.min(dp[0][i - 1], dp[2][i - 1]);
            else dp[1][i] = n; // 答案最大值为n.
            if(keep[i] == 1) dp[2][i] = Math.min(dp[0][i - 1], dp[1][i - 1]);
            else dp[2][i] = n;
        }
//        for (int i = 0; i < 3; i++) {
//            System.out.println(Arrays.toString(dp[i]));
//        }
        return Math.min(Math.min(dp[0][n - 1], dp[1][n - 1]), dp[2][n - 1]);
    }

    public int minEye(int[] nums, int L){
        int cnt = 0;
        int max = 0;
        int end = 0;
//        while(max < L){
            for (int i = 0; i < L; i++) {
                if(nums[i] > max) max = nums[i];
                if(i == end){
                cnt ++;
                end = max;
            }
//            }
//            cnt ++;
        }
//        for (int i = 0; i < eyes.length; i++) {
//            max = Math.max(max, i + eyes[i][1]);
//            if(max < i) return -1;
//            if(i == end){
//                cnt ++;
//4 6
//3 6
//2 4
//0 2
//4 7
//                end = max;
//            }
//        }

        return cnt;
    }

    public Deque<Integer> countSmaller(int[] nums){
        Deque<Integer> res = new ArrayDeque<>();
        res.addFirst(0);

        int N = nums.length;
        TreeNode root = new TreeNode(nums[N - 1]);
        root.cnt = 1;
        for (int i = N - 2; i >= 0 ; i++) {

            res.addFirst(insert(root, nums[i]));
        }
        return res;
    }
    public int insert(TreeNode root, int num){
        int smallerCnt = 0;
        while(root.val != num){
            if(num < root.val){
                root.smallerCnt ++;
                if(root.left == null) root.left = new TreeNode(num);
                root = root.left;
            }else{
                smallerCnt += root.smallerCnt + root.cnt;
                if(root.right == null) root.right = new TreeNode(num);
                root = root.right;
            }
        }
        root.cnt ++;
        return smallerCnt + root.smallerCnt;
    }

    // 牛客 腾讯 2018 校招 技术类
    /*
    * 2m个一组，和为m^2
    * 共 n / 2m 组
     */
    public long reverseArr(long n, long m){
        if(n % (2 * m) != 0) return -1;
        return n * m / 2;
    }

    public int minMax(int n, int[] A){
        int ans = 0;
        Arrays.sort(A);
        for (int i = n - 1; i >= 0; i --) {
            System.out.println(A[i]);
            if((n - i) % 2 == 1) ans += A[i];
            else ans -= A[i];
        }
        return ans;
    }

    /*
    * 1. 需要想到用求出所有解再用二分
    * 2. 不超过 --》 右边界的二分，等于得结果
    * 3. 复杂度过高： 每次不符合 l + 1, r - 1
     */

    public int chocolate(int N, int M){
        if(N == 1) return M;
        if(N == M) return 1;
        int l = 1, r = M;
        while(l <= r){
            int mid = l + (r - l) / 2;
            if(sum(mid, N) == M) return mid;
            else if(sum(mid, N) < M) l = mid + 1;
            else r = mid - 1;
        }
        return r;
    }
    public int sum(int chocoDay1, int N){
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += chocoDay1;
            chocoDay1 = (chocoDay1 + 1) / 2; // 向上取整
        }
        return sum;
    }

    // 小Q的歌单： 排列组合 long和加法解决了溢出
    // TODO: 01背包和空间优化 https://blog.nowcoder.net/n/26ae19e985694e22ba80ba70e9c11c42
    public long song(int K, int A, int X, int B, int Y){
        int MOD = 1000000007;
//        long[] fac = new long[101];
//        Arrays.fill(fac, 1);
//        for (int i = 1; i < 101; i++) {
//            fac[i] = fac[i - 1] * i;
//            fac[i] %= MOD;
//            System.out.printf("%d\t", fac[i]);
//            System.out.println();
//        }
        /*
        求组合数 C(x, i)
         */
        long[][] c = new long[101][101];
        c[0][0] = 1;
        for (int i = 1; i < 101; i++) {
            c[i][0] = 1;
            for (int j = 1; j < 101; j++) {
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % MOD;
            }
        }
        long ans = 0;
        for(int i = K / A;i >= 0;i --){
            int b = (K - A * i) / B;
            if((K - A * i) % B == 0 && i <= X && b <= Y) {
                System.out.println(i);
                System.out.println(b);
//                ans += (fac[X] / (fac[i] * fac[X - i]))  * (fac[Y] / (fac[b] * fac[Y - b])); // 这个算法会出现越界
                ans += c[X][i] * c[Y][b];
            }
            ans %= MOD;
        }
        return ans;
    }

    // https://blog.csdn.net/snowy19130140/article/details/86063267
    public void computeMaxEarning(Node[] machines, Node[] tasks){
        int cnt = 0, earning = 0;
        int MAX_GRADE = 101;
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.time == o2.time) return o2.grade - o1.grade;
                return o2.time - o1.time; //时间降序
            }
        };
        Arrays.sort(machines, cmp);
        Arrays.sort(tasks, cmp);

        int[] count = new int[MAX_GRADE]; // count[i]:等级为i的任务数量
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < machines.length && machines[j].time >= tasks[i].time; j++) {
                count[machines[j].grade] ++;
            }

            for (int k = tasks[i].grade; k < MAX_GRADE; k ++) { // ??
                if(count[k] <= 0) continue;
                cnt ++;
                count[i] --;
                earning += 200 * tasks[i].time + 3 * tasks[i].grade;
            }
        }
        System.out.printf("%d %d", cnt, earning);
    }

    // paypal 2018上交场
    public void ip(String hexIp){
        String res = "";
        String[] ips = hexIp.split(":");
        for(int i = 0;i < 8;i += 2){
            long ip = 0;
//            String subIp = String.format("%04d",Integer.valueOf(ips[i])) + String.format("%04d",Integer.valueOf(ips[i + 1])); // 不足4位长度的在前面补0（没有字母的时候可以用）
            String subIp = "";

            // 要在每一个不足4位的子串前面补0
            for (int j = 0; j < 2; j++) {
                while(ips[i + j].length() < 4) ips[i + j] = '0' + ips[i + j];
                subIp += ips[i + j];
            }

            char[] subIpArr = subIp.toCharArray();

            for(int j = subIpArr.length - 1;j >= 0;j --){
                char c = subIpArr[j];
                if(c >= '0' && c <= '9'){
                    ip += (c - '0') * Math.pow(16, subIpArr.length - 1 - j);
                }else{
                    ip += (c - 'A' + 10) * Math.pow(16, subIpArr.length - 1 - j);
                }
            }
//            ip = Integer.parseInt(subIp, 16); // 可以调函数
//            System.out.println(Arrays.toString(subIpArr));
            // 8位16进制 补码表示法 负数值的获得
            if(subIpArr[0] == '8' || subIpArr[0] == '9' || subIpArr[0] >= 'A' && subIpArr[0] <= 'F'){
                ip -= Math.pow(2, 32);
            }
            res += ip;
            if(i < 6) res += " ";
        }
        System.out.printf(res);
//        System.out.printf("E");
    }

    // google kick start 2020 round G
    public void kickStarts(int idx, String[] str){

        int cnt = 0, cnt1 = 0, cnt2 = 0;
        for (int k = 0; k < str.length; k++) {
            for (int i = 0; i <= str[k].length(); i++) {
                for (int j = i; j <= str[k].length(); j++) {
                    String sub = str[k].substring(i, j);
                    if(sub.equals("KICK")) cnt1 ++;
                    if(sub.equals("START")) {
                        cnt2 ++;
                        cnt += cnt1;
                    }
                }
            }
            System.out.printf("Case #%d: %d", idx, cnt);
        }
    }

    public void maximumCoins(int T){
        Scanner sc = new Scanner(System.in);
        for (int t = 0; t < T; t++) {
            int N = sc.nextInt();
            int[][] matrix = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            int[][] dp = new int[N + 1][N + 1];
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N ; j++) {
                    if(i == N || j == N) dp[i][j] = dp[i - 1][j - 1] + matrix[i][j];

                    else dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i + 1][j + 1]) + matrix[i][j];
                }

            }
            System.out.printf("Case #%d: %d", t, dp[N][N]);
        }
    }



    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 获取输入值
//        int n = Integer.parseInt(scanner.nextLine());
        int n = scanner.nextInt();
//        String space = scanner.next();
//        int L = scanner.nextInt();
//        String input = scanner.nextLine();
//        String[] split = input.split(" ");
//        int[] heights = new int[n];
//        for(int i = 0;i < n;i ++) {
//            heights[i] = Integer.parseInt(split[i]);
//        }

//        String input1 = scanner.nextLine();
//        String input2 = scanner.nextLine();
//        String[] split1 = input1.split(" ");
//        String[] split2 = input2.split(" ");
//        int[] work = new int[n];
//        int[] keep = new int[n];
//        for (int i = 0; i < n; i++) {
//            work[i] = Integer.parseInt(split1[i]);
//            keep[i] = Integer.parseInt(split2[i]);
//        }

//        int[] nums = new int[L + 1];
//        int L = Integer.parseInt(scanner.nextLine());
//        for (int i = 0; i < n; i++) {
//            int a = scanner.nextInt();
//            int b = scanner.nextInt();
//            if(nums[a] < b) nums[a] = b;
//        }
//        int[] A = new int[n];
//        for (int i = 0; i < n; i++) {
//            A[i] = scanner.nextInt();
//        }

        Meituan s = new Meituan();

//        int res = s.numPalindrome(input);

        // 输出
//        out.println(res);
//        int[] stones = {3, 2, 4, 1};
//        out.println(s.mergeStones(stones, 2));


//        String[] strs = {"meituanapp","meituanwaimai","dianpingren","dianpingjieguo"};
//        s.split(strs);
//        String str = "HG[3|B[2|CA]]F";
//        String str = "BHCJSBCSCW[100|DASKDNKJWDNWCNQWCNOQCNQWOICNWQOINCWQOICNQWOIXWOISWIODAOWPQWDMQKOQZCDWF]WQJDWQUINCQQW[99|SDWQJCIQIUWCNQUCNWQIDNWQUIFNSALQP]DQOJOIXZALPPQQAAX";
//        s.decode(str);
//        int[] heights = {5, 3, 8, 3, 2, 5};
//        System.out.println("maing");
//        s.building(6, heights);
//        s.building(n, heights);

//        System.out.println(s.qWork(n, work, keep));

//        System.out.println(s.minEye(nums, L));

//        System.out.println(s.reverseArr(n, L));
//        System.out.println(s.minMax(n, A));
//        System.out.println(s.chocolate(n, L));

//        int K = scanner.nextInt();
//        int A = scanner.nextInt();
//        int X = scanner.nextInt();
//        int B = scanner.nextInt();
//        int Y = scanner.nextInt();
//        System.out.println(s.song(K, A, X, B, Y));

//        int N = scanner.nextInt();
//        int M = scanner.nextInt();
//        Node[] machines = new Node[N];
//        Node[] tasks = new Node[M];
//        for (int i = 0; i < N; i++) {
//            int time = scanner.nextInt();
//            int grade = scanner.nextInt();
//            machines[i] = new Node(time, grade);
//        }
//        for (int i = 0; i < M; i++) {
//            int time = scanner.nextInt();
//            int grade = scanner.nextInt();
//            tasks[i] = new Node(time, grade);
//        }
//        s.computeMaxEarning(machines, tasks);

//        s.ip("0:001:000:02:0000:A:FFFF:FFFF");
//        s.ip("2001:DA8:0:0:0:0:0:0");

//        scanner.nextLine(); //需要加上这一行吸收上一行nextInt的回车
//        String[] input = new String[];
//        for (int i = 0; i < n; i++) {
//            input[i] = scanner.nextLine();
//            s.kickStarts(i, input);
//        }
//
        s.maximumCoins(n);
        //刷新缓冲区
//        out.flush();
    }
    class TreeNode{
        int val;
        int smallerCnt;
        int cnt;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
            this.smallerCnt = 0;
            this.cnt = 0;
        }
    }
    static class Node{ // 在static main方法中调用
        int time;
        int grade;

        public Node(int time, int grade){
            this.time = time;
            this.grade = grade;
        }
    }
}

