package StacksAndQueues;

import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2019-07-21.
 * 程序员代码面试指南1001
 * 实现O(1)时间内push(),pop(),getMin()操作的栈
 * https://github.com/LyricYang/Internet-Recruiting-Algorithm-Problems/blob/master/CodeInterviewGuide/Chapter01_Stack%26Queue/Q1001.md
 */
public class MyStack1 {

    Stack<Integer> stackData;
    Stack<Integer> stackMin;

    public MyStack1() {
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    public void push(int value) {
        this.stackData.push(value);
        if(this.stackMin.isEmpty()) this.stackMin.push(new Integer(value));
        else if (value < this.stackMin.peek()) {
            this.stackMin.push(value);
        }
    }

    public int pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("The stack is empty.");
        }
        if (this.stackData.peek() == this.stackMin.peek()) {
            this.stackMin.pop();
        }
        return this.stackData.pop();
    }

    public int getMin() {
        if (this.stackMin.isEmpty()) throw new RuntimeException("The stack is empty.");

        return this.stackMin.peek();
    }

    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(3);
        System.out.println(stack1.getMin());
        stack1.push(4);
        System.out.println(stack1.getMin());
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());
    }
}
