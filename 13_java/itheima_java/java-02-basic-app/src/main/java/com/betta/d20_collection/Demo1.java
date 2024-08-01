package com.betta.d20_collection;

import java.util.LinkedList;

public class Demo1 {
    public static void main(String[] args) {
        //LinkedList 制作队列
        LinkedList<String> queue = new LinkedList<>();
        queue.addLast("1号");
        queue.addLast("2号");
        queue.addLast("3号");
        queue.addLast("4号");
        System.out.println(queue);
        String turn = queue.pop();//叫号
        System.out.println(turn);
        turn = queue.pop();//叫号
        System.out.println(turn);
        System.out.println(queue);//现队列

        System.out.println("-----------------------------");

        //栈
        LinkedList<String> stack = new LinkedList<>();
        //压栈
        stack.push("1号");
        stack.push("2号");
        stack.push("3号");
        stack.push("4号");
        System.out.println(stack);
        turn = stack.pop();//出栈
        System.out.println(turn);
        turn = stack.pop();//出栈
        System.out.println(turn);
        System.out.println(stack);//现队列
    }
}
