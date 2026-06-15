package StacksAndQueues;

import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2019-07-22.
 * 用一个栈实现另一个栈从栈顶到栈底从大到小排序
 * https://github.com/LyricYang/Internet-Recruiting-Algorithm-Problems/blob/master/CodeInterviewGuide/Chapter01_Stack%26Queue/Q1004.md
 */
public class P_1005_sortStackByStack {

    public static void sortStackByStack(Stack<Integer> stack){
        Stack<Integer> helper = new Stack<>();
        while(!stack.isEmpty()){
            //用cur暂存当前需要push入helper的值，从而helper中需要出栈的值可以放入stack
            int cur = stack.pop();
            while(!helper.isEmpty() && helper.peek() < cur){
                stack.push(helper.pop());
            }
            helper.push(cur);
        }
        while(!helper.isEmpty()){
            stack.push(helper.pop());
        }
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(1);
        stack.push(6);
        stack.push(2);
        stack.push(5);
        stack.push(4);
        sortStackByStack(stack);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
