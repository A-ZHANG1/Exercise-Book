/**
 * Created by Wayne.A.Z on 2019-07-08.
 *  2019实习招聘一题，6个给定数字能够排列出的最大时间 如12：34：57
 *  归结到递归解全排列问题
 */
//输出最大时间

class Perm{
    int[]  result=new int[6];
    int max;

    public  int perm(int A[],int k,int n) {
        int i;
        if (k == n) {           //k表示已排好前k个元素
            for (i = 0; i < n; i++) {

                result[i] = A[i];
            }
            int hh = result[0]*10+result[1];
            int mm = result[2]*10+result[3];
            int ss = result[4]*10+result[5];
            int curr = hh*10000+mm*100+ss;
            max = 0;

            if(hh <24 && mm <60 && ss<60 && curr > max){
                max = curr;
//                System.out.println(curr);
                System.out.println("true" + max);

            }
        } else {
            for (i = k; i < n; i++) {
                int temp;
                temp = A[i];    //置第i个位置的元素为排头元素
                A[i] = A[k];
                A[k] = temp;
                perm(A, k + 1, n);   //递归求排头元素外的n-k+1个元素的全排列
                temp = A[i];       //将排头回归第i个位置
                A[i] = A[k];
                A[k] = temp;
            }
        }
        return max;
    }

}