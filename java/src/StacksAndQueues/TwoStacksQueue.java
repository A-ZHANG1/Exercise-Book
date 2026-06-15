package StacksAndQueues;

import java.util.Stack;

/**
 * Created by Wayne.A.Z on 2019-07-21.
 * 1002 两个栈组成的队列
 * https://github.com/LyricYang/Internet-Recruiting-Algorithm-Problems/blob/master/CodeInterviewGuide/Chapter01_Stack%26Queue/Q1002.md
 *
 */
public class TwoStacksQueue {
    Stack<Integer> stackPush;
    Stack<Integer> stackPop;

    public TwoStacksQueue() {
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    public void add(int value){
        this.stackPush.push(value);
    }

    public int poll(){
        //记得抛异常
        if(stackPush.isEmpty() && stackPop.isEmpty()) throw new RuntimeException("Queue is empty.");
        while(!this.stackPush.isEmpty()){
            this.stackPop.push(this.stackPush.pop());
        }
        return this.stackPop.pop();
    }

    public int peek(){
        //记得抛异常
        if(stackPush.isEmpty() && stackPop.isEmpty()) throw new RuntimeException("Queue is empty.");
        while(!this.stackPush.isEmpty()){
            this.stackPop.push(this.stackPush.pop());
        }
        return this.stackPop.peek();
    }

    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}
