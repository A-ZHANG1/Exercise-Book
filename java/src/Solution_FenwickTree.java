/**
 * Created by Wayne.A.Z on 2020-10-05.
 * LC 307: 树状数组解 http://zxi.mytechroad.com/blog/data-structure/307-range-sum-query-mutable/
 */
public class Solution_FenwickTree {
    FenwickTree tree;
    int[] nums;
    public Solution_FenwickTree(int[] nums){ // 初始化，建树
        this.nums = nums;
        this.tree = new FenwickTree(nums.length);
        for (int i = 0; i < nums.length; i++) {
            tree.update(i + 1, nums[i]);
        }
    }
    public void update(int i, int val){
        tree.update(i + 1, val - nums[i]);
        nums[i] = val; //
    }

    public int sumRange(int i, int j){
        return tree.query(j + 1) - tree.query(i); // nums下标0 .. N - 1,sums数组下标1..N
    }

    public static void main(String[] args){
        int[] nums = {-1};
        Solution_FenwickTree s = new Solution_FenwickTree(nums);
        System.out.println(s.sumRange(0, 0));
        s.update(0, 1);
        System.out.println(s.sumRange(0, 0));

    }
}
class FenwickTree{
    int[] sums;
    public FenwickTree(int n) {
        sums = new int[n + 1];
    }

    public void update(int idx, int delta){
        while(idx < sums.length){
            sums[idx] += delta;
            idx += idx & -idx;
        }
    }

    public int query(int idx){ // ?
        int sum = 0;
        while(idx > 0){
            sum += sums[idx];
            idx -= idx & -idx;
        }
        return sum;
    }

}

