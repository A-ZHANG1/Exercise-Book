import java.util.LinkedList; // 注意调这个包
import java.util.*;

/**
 * Created by Wayne.A.Z on 2020-10-13.
 *
 * https://leetcode-cn.com/problems/sort-an-array/solution/fu-xi-ji-chu-pai-xu-suan-fa-java-by-liweiwei1419/
 */
public class Solutionn_sort {
    // 315 插入排序
    public List<Integer> countSmallerInsertionSort(int[] nums) {
        LinkedList<Integer> res = new LinkedList<>();
        int N = nums.length;
        for (int i = N - 1; i >= 0 ; i--) {
            int j = i + 1, tmp = nums[i];
            while(j < N && nums[j] >= tmp){
                nums[j - 1] = nums[j];
                j ++;
            }
            nums[j - 1] = tmp;
            res.addFirst(N - j); // 计数的个数 N - j
//            System.out.println(Arrays.toString(nums));
        }
        return res;
    }

    // 912(jianzhi 51)数组中的逆序对 + 下标对应问题
    // 315 归并排序, 含左含右
    Integer[] res315;
    public List<Integer> countSmallerMerge(int[] nums) {

        List<Integer> res = new ArrayList<>();
        int N = nums.length;
        if(N == 0) return res;

        res315 = new Integer[N];
        Arrays.fill(res315, 0);
        int[] tmp = new int[N];
        int[] indexes = new int[N]; // 每次归并在索引数组中将数组排序，原数组元素顺序不变
        for (int i = 0; i < N; i++) {
            indexes[i] = i;
        }

        mergeAndCount(nums, 0, N - 1, tmp, indexes);
        return Arrays.asList(res315);
    }
    public void mergeAndCount(int[] nums, int left, int right, int[] tmp, int[] indexes){
        if(left == right) return; // 递归终止条件
        int mid = left + (right - left) / 2;
        mergeAndCount(nums, left, mid, tmp, indexes);
        mergeAndCount(nums, mid + 1, right, tmp, indexes);

        merge2SortedArrays(nums, left, mid, right, tmp, indexes);
    }
    // [left, mid] 是排好序的，[mid + 1, right] 是排好序的
    public void merge2SortedArrays(int[] nums, int left, int mid, int right, int[] tmp, int indexes[]){
        for (int i = left; i <= right; i++) {
            tmp[i] = indexes[i];
        }
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if(i == mid + 1){
                indexes[k] = tmp[j ++];
            }else if(j == right + 1){
                indexes[k] = tmp[i ++];
                res315[indexes[k]] += right - mid ;
            }else{
                if(nums[tmp[i]] <= nums[tmp[j]]){ // 排序前后两个相等的数相对位置不变，则算法稳定。
                    indexes[k] = tmp[i ++];
                    res315[indexes[k]] += j - mid - 1 ;
                }else{
                    indexes[k] = tmp[j ++];
                }
            }
        }
    }


    public static void main(String[] args){
        Solutionn_sort s = new Solutionn_sort();
        int[] nums = {5, 2, 6, 1};
//        System.out.println(s.countSmallerInsertionSort(nums));
        System.out.println(s.countSmallerMerge(nums));
    }
}


