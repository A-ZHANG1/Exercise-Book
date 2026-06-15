package companyCompetition;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2020-08-16.
 */
public class Weiruan {
    // zhousai 198 easy
    public int numWaterBottles(int numBottles, int numExchange) {
        int res = numBottles;
        int nEmpty = numBottles;
        while(nEmpty != 0 && nEmpty >=numExchange){
            int nCur = nEmpty / numExchange;
            res += nCur;
            nEmpty = nEmpty % numExchange + nCur;
        }
        return res;
    }

    /*
    1519 子树中标签相同的节点数
     */
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        // 用arraylist的数组做邻接链表
        List<Integer>[] points = new List[n];
        for(int i = 0;i < n;i ++) points[i] = new ArrayList<>();
        for (int[] point : edges) {
            points[point[0]].add(point[1]);
            points[point[1]].add(point[0]); // 无向图
        }

        //
        int[] lbs = new int[n];
        int idx = 0;
        for(char c: labels.toCharArray()){
            lbs[idx ++] = c - 'a';
        }

        res = new int[n];
        visited = new boolean[n];
        visited[0] = true;
        dfs(0, points, lbs);
        return res;
    }
    int[] res;
    boolean[] visited;
    public int[] dfs(int i, List<Integer>[] points,int[] lbs){
        int[] curLbs = new int[26];
        curLbs[lbs[i]] ++;
        for(int child: points[i]){
            // 对无向图，需要判断遍历过的跳过
            if(visited[child]) continue;
            visited[child]= true;
            int[] childLbs = dfs(child, points, lbs);
            for(int j = 0;j < 26;j ++){ // 当前节点在内的子树中每个孩子节点label的记录
                curLbs[j] += childLbs[j];
            }
        }
        res[i] = curLbs[lbs[i]];
        return curLbs;
    }

    public List<String> maxNumOfSubstrings(String s){
        List<String> res = new ArrayList<>();

        int N = s.length();
        char[] sArr = s.toCharArray();
        // 26个字母出现的第一、最后一个位置
        int[][] arr = new int[26][2]; // 或hashmap或类
        for (int i = 0; i < 26; i++) {
            arr[i][0] = Integer.MAX_VALUE; // 左
            arr[i][1] = Integer.MIN_VALUE; // 右
        }

        for (int i = 0; i < N; i++) {
            arr[sArr[i] - 'a'][0] = Math.min(arr[sArr[i] - 'a'][0], i);
            arr[sArr[i] - 'a'][1] = Math.max(arr[sArr[i] - 'a'][1], i);
        }

//        for (int i = 0; i < 26; i++) {
//            System.out.println(Arrays.toString(arr[i]));
//        }
        // 合并包含在首末位置间的字符的区间
        for (int i = 0; i < 26; i++) {
            if(arr[i][0] == Integer.MAX_VALUE) continue;
            for (int j = arr[i][0] + 1; j < arr[i][1]; j++) {
                int cur = sArr[j] - 'a';
                if((char)(i + 'a') != s.charAt(j) && (arr[cur][0] < arr[i][0] || arr[cur][1] > arr[i][1])) {
                    arr[i][0] = Math.min(arr[i][0], arr[cur][0]);
                    arr[i][1] = Math.max(arr[i][1], arr[cur][1]);
                    arr[cur] = arr[i];
                }
            }
        }
//        for (int i = 0; i < 26; i++) {
//            System.out.println(Arrays.toString(arr[i]));
//        }
        // 贪心，从短的区间开始选

        // 去重后排序
        List<List<Integer>> list = new ArrayList<>();
        Set<String> substrings = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            if(arr[i][0] == Integer.MAX_VALUE) continue;
            String sub = s.substring(arr[i][0], arr[i][1] + 1);
            if(substrings.contains(sub)) continue;
            list.add(Arrays.asList(arr[i][0], arr[i][1])); //方便后续排序
            substrings.add(sub);
        }

        Collections.sort(list, ((o1, o2)->((o1.get(1) - o1.get(0)) - (o2.get(1) - o2.get(0)))));

        System.out.println(list);
        // 若子区间已被选择过，跳过
        List<List<Integer>> pick = new ArrayList<>();
        for(List<Integer> tmp : list){
            boolean flag = true;
            for(List<Integer> picked: pick){

                if(tmp.get(0) < picked.get(0) && tmp.get(1) > picked.get(1)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                pick.add(tmp);
                res.add(s.substring(tmp.get(0), tmp.get(1) + 1));
            }
        }
    return res;

    }
    // 同 898 O(n log C),  C为数组最大范围
    public int closestToTarget(int[] arr, int target) {
        int ans = Integer.MAX_VALUE;
        Set<Integer> valid = new HashSet<>();
        for(int num: arr){
            Set<Integer> validPlusNum = new HashSet<>();
            validPlusNum.add(num);
            ans = Math.min(ans, Math.abs(num - target));
            for(int prev: valid){
                int cur = prev & num;
                validPlusNum.add(cur);
                ans = Math.min(ans, Math.abs(num - target));
            }
            valid = validPlusNum;
        }
        return ans;
    }

    // 209 周赛 网易
    public int specialArray(int[] nums) {
        int N = nums.length;

        int cnt = 0;
        for (int i = 0; i <= N; i++) {
            cnt  = 0;
            for (int j = 0; j < N; j++) {
                if(nums[j] >= i){
                    cnt ++;
                    if(cnt > i) break;
                }
            }
            if(cnt == i) return cnt;
        }
//        System.out.println(cnt);
        return -1;
    }

    /* Math.tan(Math.PI / 6) = Math.tan(Math.PI * 7 / 6)
    *  Math.atan(Math.PI * 7 / 6) 可区分二者
    *  Math.atan2(dy, dx) 定义： (0, 0) undefined,其余对应角度值在-PI 到PI之间的，返回值互不相同
     */
    // TODO: 挂在用例2上。问题是Math.tan(Math.PI/4) atan(45) atan2(1,1)的返回值都不一样

    public int visiblePoints2(List<List<Integer>> points, int angle, List<Integer> location) {
        int cnt, max = 0;
        for (int d = 0; d < 360; d++) {
            double lRange = Math.atan( Math.toRadians(d - angle / 2));
            double rRange = Math.atan( Math.toRadians(d + angle / 2));
            cnt = 0;
            for(List<Integer> point : points) {
                double dy = point.get(1) - location.get(1);
                double dx = point.get(0) - location.get(0);
                if(dy == 0 && dx == 0) {
                    System.out.printf("%d %d\n", point.get(0), point.get(1));
                    cnt ++;
                }else{
//                    double k = (point.get(1) - location.get(1))/(point.get(0) - location.get(0));
                    double k = Math.atan2(dy, dx);
                    if( k > lRange && k < rRange) {
                        System.out.printf("%d %d\n", point.get(0), point.get(1));
                        cnt ++;
                    }
                }
            }
            max = Math.max(cnt, max);
        }
        return max;
    }

    // 解2：
    // 对点的相对角度排序后用滑窗 AC
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int ans = 0, tmp = 0;

        double eps = 1e-8; // Double型相等

        PriorityQueue<Double> pq = new PriorityQueue<>();

        List<Double> angles = new ArrayList<>();
        for(List<Integer> point : points){
            double dy = point.get(1) - location.get(1);
            double dx = point.get(0) - location.get(0);
            if(dx == 0 && dy == 0) tmp += 1;
            double theta = Math.atan2(dy, dx);
//            pq.add(theta);
            angles.add(theta);
        }
        Collections.sort(angles);
        for (int i = 0, j = 0; i < angles.size(); i++) {
            while(j < pq.size() && Math.abs(angles.get(i) - angles.get(j)) < eps){
                j ++;
            }
            ans = Math.max(ans, j - i + tmp);
        }
        return ans;
    }

        // LC 858 另一个数学（角度）题
    public int mirrorReflection(int p, int q) {
        int m = p, n = q, r;
        while(n > 0){ // 最小公倍数
            r = m % n;
            m = n;
            n = r;
        }
        // m为最大公因数。 p/m奇偶性与LCM/p奇偶性相同
        if((p / m) % 2 == 0) return 2;
        else if((q / m) % 2 == 0) return 0;
        else return 1;
    }


    public static void main(String[] args) {
        Weiruan s = new Weiruan();
//        System.out.println(s.numWaterBottles(5, 5));

//        int[][] edges = {{0,1}, {1,2}, {0,3}};
//        System.out.println(Arrays.toString(s.countSubTrees(4, edges,"bbbb")));
//        System.out.println(s.maxNumOfSubstrings("adefaddaccc"));
//        System.out.println(s.maxNumOfSubstrings("cabcccbaa"));

//        int[] nums = {3, 5};
//        int[] nums = {0, 0};
//        int[] nums = {0,4,3,0,4};
//        System.out.println(s.specialArray(nums));
//        List<List<Integer>> points = Arrays.asList(Arrays.asList(2, 1),Arrays.asList(2, 2),Arrays.asList(3, 3));
//        List<Integer> location = Arrays.asList(1, 1);
//        List<List<Integer>> points = Arrays.asList(Arrays.asList(2, 1),Arrays.asList(2, 2),Arrays.asList(3, 4),Arrays.asList(1, 1));
//        List<Integer> location = Arrays.asList(1, 1);

//        List<List<Integer>> points = Arrays.asList(Arrays.asList(0, 1),Arrays.asList(2, 1));
//        List<Integer> location = Arrays.asList(1, 1);
//        System.out.println(s.visiblePoints(points, 13, location));

        List<List<Integer>> points = Arrays.asList(Arrays.asList(1, 1),Arrays.asList(2, 2),Arrays.asList(3, 3),Arrays.asList(4, 4),Arrays.asList(1, 2),Arrays.asList(2, 1));
        List<Integer> location = Arrays.asList(1, 1);
        System.out.println(s.visiblePoints(points, 0, location));

//        System.out.println(s.mirrorReflection(3, 6));

//        String s0 = "catsanddog";
//        List<String> wordDict = Arrays.asList("cat", "cats", "and", "sand", "dog");
//        System.out.println(s.wordBreak(s0, wordDict));

    }
}
