package StacksAndQueues;

import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2019-07-21.
 * 1003 不使用多余数据结构翻转栈（递归）
 * 见p8 递归跟踪
 * https://github.com/LyricYang/Internet-Recruiting-Algorithm-Problems/blob/master/CodeInterviewGuide/Chapter01_Stack%26Queue/Q1003.md
 */
public class P_1003_ReverseStackUsingRecursive {

    /**
     *  若使用单一函数递归，不能暂存栈底元素
     * 每次调用递归函数1，从原栈中移除栈底元素，其他元素栈底到栈顶位置不变
     */

    public static int getAndRemoveBottomElem(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) return result;
        else{
            int bottom = getAndRemoveBottomElem(stack);
            stack.push(result);
            return bottom;
        }
    }

    public static void reverse(Stack stack) {
        if (stack.isEmpty()) return;
        int i = getAndRemoveBottomElem(stack);
        reverse(stack);
        stack.push(i);
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }
    }
}
