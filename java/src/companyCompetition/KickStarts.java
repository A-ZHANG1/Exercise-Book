package companyCompetition;

/**
 * Created by Wayne.A.Z on 2020-12-29.
 *
 * google kickstart 2020 roundG
 */
import java.util.*;

public class KickStarts { // public class Solution{
    /*
    https://codingcompetitions.withgoogle.com/kickstart/round/00000000001a0069/0000000000414bfb
    用例：
    3
    AKICKSTARTPROBLEMNAMEDKICKSTART
    Case #1: 3
    STARTUNLUCKYKICK
    Case #2: 0
    KICKXKICKXSTARTXKICKXSTART
    Case #3: 5
     */
    public void kickStarts(int idx, String str){

        int cnt = 0, cnt1 = 0, cnt2 = 0;
        for (int i = 0; i < str.length() - 4; i++) {
                String sub = str.substring(i, i + 4);
                if(str.substring(i, i + 4).equals("KICK")) cnt1 ++;
                if(str.substring(i, i + 5).equals("START")) {
                    cnt2 ++;
                    cnt += cnt1;
                }

        }
        System.out.printf("Case #%d: %d/n", idx + 1, cnt); // 一直WA是因为这里没有换行

        return;
    }

//    public static void main(String args[]) { // 也可以没有main函数和调用分开，节约时间
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        KickStarts s = new KickStarts();
//        for (int i = 0; i < n; i++) {
//            String input = scanner.nextLine();
//            s.kickStarts(i, input);
//        }
//    }
    /*
    同一条左上到右下的对角线上的元素满足i-j+N为定值。+N为了使值为正。
    TODO: N皇后的另一种checkValid思路
     */
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        sc.nextLine();
        for(int i = 0;i < n;i ++){
            int res = 0;
            int m = sc.nextInt();
            sc.nextLine();
            int[] cnt = new int[2*m];
            for(int j = 0; j < m;j ++){
                for(int k = 0;k < m;k ++){
                    cnt[j - k + m] += sc.nextInt();
                    res = Math.max(res, cnt[j - k + m]);
                }
            }
            System.out.println("Case #"+(i+ 1)+": "+ res);
        }
    }
}
