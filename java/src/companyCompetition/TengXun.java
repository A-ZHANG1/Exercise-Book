package companyCompetition;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2020-11-22.
 */
public class TengXun {
//    public String longestCommonPrefix(String[] strs) {
//        String res = "";
//        for(int i = 0;i < strs.length;i ++){
//            res = commonPrefix(res, strs[i]);
//        }
//        return res;
//    }
//    public String commonPrefix(String s1, String s2){
//        int i = 0;
//        System.out.println(s1);
//        System.out.println(s2);
//        for(i = 0 ;i < Math.min(s1.length(), s2.length());i ++){
//            if(s1.charAt(i) != s2.charAt(i)) break;
//        }
//        System.out.println(i);
//        return s1.substring(0, i + 1);
//    }

    public boolean isValid(String s) {
        Deque<Character> stack1 = new ArrayDeque<>();
//        Deque<Character> stack2 = new ArrayDeque<>();
//        Deque<Character> stack3 = new ArrayDeque<>();
        for(char c: s.toCharArray()){
            switch(c){
                case '(':stack1.addLast(c); break;
                case ')':
                    if(stack1.size() != 0 && stack1.getLast() == '('){
                        stack1.removeLast(); break;
                    }else
                        return false;
                case '[':stack1.addLast(c); break;
                case ']':
                    if(stack1.size() != 0 && stack1.getLast() == '['){
                        stack1.removeLast(); break;
                    }else
                        return false;
                case '{':stack1.addLast(c); break;
                case '}':
                    if(stack1.size() != 0 && stack1.getLast() == '{'){
                        stack1.removeLast(); break;
                    }else
                        return false;
            }
        }
        return true;
    }


    public static void main(String[] args){
        TengXun tx = new TengXun();
//        String[] strs = {"flower","flow","flight"};
//        tx.longestCommonPrefix(strs);
//        int[] nums = {-1,0,1,2,-1,-4};
//        tx.threeSum(nums);
        System.out.println(tx.isValid("([)]"));
    }
}
