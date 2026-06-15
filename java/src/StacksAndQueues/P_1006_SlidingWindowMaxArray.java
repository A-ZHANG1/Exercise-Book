package StacksAndQueues;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wayne.A.Z on 2019-08-17.
 * 1006 生成窗口最大数组
 * https://github.com/LyricYang/Internet-Recruiting-Algorithm-Problems/blob/master/CodeInterviewGuide/Chapter01_Stack%26Queue/Q1006.md
 */
public class P_1006_SlidingWindowMaxArray {

public static int[] getMaxWindow(int[] arr, int w){

    //java特性：可以由参数初始化数组长度
    int[] res = new int[arr.length - w +1];
    int[] temp = new int[w];

    for(int i  = 0; i < res.length; i ++){
        for(int j = 0; j < w; j++){
            temp[j] = arr[i + j];
        }
        //java 8 stream api => 数组中最大元素
        res[i] = Arrays.stream(temp).max().getAsInt();
    }
    return res;
}

///双端队列qmax
public static int[] getMaxWindowStack(int[] arr, int w){

    //!应该记得边界条件判断和异常处理
    if(arr == null || w < 1 || arr.length < w){
        return null;
    }
    LinkedList<Integer> qmax = new LinkedList<>();
    int[] res = new int[arr.length - w +1];
    for(int i = 0; i < arr.length; i++){
        //todo:即使看懂了算法思路也没想到怎么implement
        res[i] = qmax.pollFirst();
    }
    return res;
}

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = { 4, 3, 5, 4, 3, 3, 6, 7 };
        int w = 3;
        printArray(getMaxWindow(arr, w));
        printArray(getMaxWindowStack(arr, w));

    }
}
