package StacksAndQueues;

/**
 * Created by Wayne.A.Z on 2019-08-27.
 */
public class Solution_1122 {

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = arr2[0],min = arr2[0];

        //改变基础的计数排序：arr取值不一定是小范围的连续数字
        for(int i:arr1){
            if(i > max){
                max = i;
            }else if(i < min){
                min = i;
            }
        }

        int[] tmp = new int[max - min + 1]; //保存出现次数
        int[] res = new int[arr1.length];


        //先统计出现次数，然后分两次循环分别处理在/不在arr2中的元素.明确且简化的逻辑是这样
        for(int i : arr1){
            tmp[i - min] ++ ;
        }

        int idx = 0;

        //tmp (idx,val) -> (min + idx = arr1.val; val = arr1.val.cnt)
        //arr2(idx, val) -> (tmp[val-min] = arr1.val.cnt)

        //虽然也可以考虑从tmp出发，但是从arr2出发可能目标更明确、不用调用indexOf.
        for(int i = 0; i < arr2.length; i++){
            while(tmp[arr2[i] - min] -- > 0){
                res[idx ++] = arr2[i];
            }
            tmp[arr2[i] - min] = 0;
        }

        int cnt;
        for(int i = 0;i < tmp.length;i++){
            cnt = tmp[i];

            while (cnt-- > 0) {
                res[idx++] = min + i;
            }
        }
        return res;
    }

    public static void main(String args[]){
        int[] arr1 = {2,3,1,3,2,4,6,7,9,2,19};
        int[] arr2 = {2,1,4,3,9,6};
        Solution_1122 s = new Solution_1122();
        int[] res = s.relativeSortArray(arr1, arr2);
        for(int i : res){
            System.out.println(i);
        }
    }
}

