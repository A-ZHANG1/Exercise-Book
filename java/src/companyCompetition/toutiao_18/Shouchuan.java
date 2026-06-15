package companyCompetition.toutiao_18;

/**
 * Created by Wayne.A.Z on 2020-12-13.
 */
/**
 *链接：https://www.nowcoder.com/questionTerminal/429c2c5a984540d5ab7b6fa6f0aaa8b5
 * 来源：牛客网
 *输入：
 * 第一行输入n，m，c三个数，用空格隔开。(1 <= n <= 10000, 1 <= m <= 1000, 1 <= c <= 50)
 * 接下来n行每行的第一个数num_i(0 <= num_i <= c)表示第i颗珠子有多少种颜色。接下来依次读入num_i个数字，每个数字x表示第i颗柱子上包含第x种颜色(1 <= x <= c)
 *输出：
 *一个非负整数，表示该手链上有多少种颜色不符需求。
 *
 * fail 用例：
 * 100 10 50
 * 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 1 49 0 0 0 0 0 0 0 0 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 1 48 0 0 0 0 1 42 0 0 1 28 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 1 39 0 0 1 47 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 1 45 0 0 1 34 0 0 0 0 0 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 0 0 0 1 26 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 0 0 0 0 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 1 25 0 0 1 33 1 1 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 0 0 0 0 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 1 27 0 1 41 0 1 13 0 1 29 0 0 50 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 0 0 0 0 0 2 5 11 0 1 50 0
 *答案 应该为19
 * 思路： 1 << color位作为当前串的表示，问题：c输入规模为50时会出现长整型溢出，解：用set保存color
 */

import java.util.*;
import java.io.*;
public class Shouchuan{
    public static void main(String[] args){
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        List<List<Integer>> colors = new ArrayList<>();
        int N = sc.nextInt();
        int m = sc.nextInt();
        int c = sc.nextInt();
        for(int i = 0;i < N;i ++){
            int num_i = sc.nextInt();
            colors.add(new ArrayList<>());
            for(int j = 0;j < num_i;j ++){
                int cur = sc.nextInt();
                colors.get(i).add(cur);
            }
        }

        for(int i = N;i < N + m - 1;i ++){
            colors.add(colors.get(i - N));
        }

//        for (int i = 0; i < N + m - 1; i++) {
//            System.out.println(colors.get(i).toString());
//        }
        Set<Integer> res = new HashSet<>();
        for(int i = 0;i < N;i ++){
            Set<Integer> record = new HashSet<>();
            for(int j = 0;j < m;j ++){
                for(int color: colors.get(i + j)){
                    if(record.contains(color)) res.add(color);
                    else record.add(color);
                }
            }
        }
        System.out.println(res.size());
    }
}
