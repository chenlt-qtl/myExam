package com.betta.d21_huangPu;

import java.util.*;
import java.util.stream.Stream;

/**
 * **需求**
 * <p>
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，并返回 反转后的链表 。
 * <p>
 * **示例 1：**
 * <p>
 * ![image-20230509111922740](image-20230509111922740.png)
 * <p>
 * ```
 * 比如 head 指向的链表内容大致是 1,2,3,4,5 , left = 2, right = 4
 * 反转后的链表就是 1,4,3,2,5
 * <p>
 * 如果链表只有一个节点：head指向的是 5  ，left = 1, right = 1
 * 反转后的链表就还是 5
 * ```
 * <p>
 * **具体功能点的要求如下**
 * <p>
 * 1、设计一个Node泛型类，用于代表链表的结点。每个结点包含（数据data，和下一个结点的地址值next） 3
 * 2、开发一个类叫MyLinkedList，提供一个add方法可以让用户添加链表的结点，直到用户输入exit，则返回链表（返回链表实际上是返回链表的头结点） 5
 * <p>
 * 3、提供一个reverse方法，接收头指针 head 和两个整数 left 和 right ，其中 left <= right，按上面的要求进行反转。反转后，返回新的链表 9
 * <p>
 * 4、提供一个forEach方法，接收新链表，并对其进行遍历输出。
 */
public class Question6 {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        MyLinkedList.Node<Integer> node = myLinkedList.add(1, 2, 3, 4, 5, 6, 7, 8);
        myLinkedList.forEach(node);

        myLinkedList.reverse(node, 2, 4);
        System.out.println("left=2,right=4");
        myLinkedList.forEach(node);


        node = myLinkedList.add(1, 2, 3, 4, 5, 6, 7, 8);
        myLinkedList.reverse(node, 3, 3);
        System.out.println("left=3,right=3");
        myLinkedList.forEach(node);


        node = myLinkedList.add(5);
        myLinkedList.reverse(node, 1, 1);
        System.out.println("left=1,right=1");
        myLinkedList.forEach(node);

        node = myLinkedList.add(1, 2, 3, 4, 5, 6, 7, 8);
        myLinkedList.reverse(node, 3, 7);
        System.out.println("left=3,right=7");
        myLinkedList.forEach(node);
    }

}


//设计一个Node泛型类，用于代表链表的结点。每个结点包含（数据data，和下一个结点的地址值next）
//开发一个类叫MyLinkedList，提供一个add方法可以让用户添加链表的结点，直到用户输入exit，则返回链表（返回链表实际上是返回链表的头结点）
//提供一个reverse方法，接收头指针 head 和两个整数 left 和 right ，其中 left <= right，按上面的要求进行反转。反转后，返回新的链表
//提供一个forEach方法，接收新链表，并对其进行遍历输出。


class MyLinkedList<T> {

    static class Node<T> {
        private T data;
        private int size = 0;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public Node<T> add(T... obj) {
        Node<T> lastNode = null;
        Node<T> head = null;

        for (int i = 0; i < obj.length; i++) {
            Node<T> node = new Node(obj[i]);
            if (lastNode != null) {
                lastNode.next = node;
                head.size++;
            } else {
                head = node;
                head.size = 1;
            }
            lastNode = node;

        }
        return head;
    }

    public Node<T> reverse(Node<T> head, int left, int right) {
        if (head == null || left < 1 || right > head.size || left >= right) {
            return head;
        }

        List<T> temp = new ArrayList<>();
        Node<T> leftNode = null;
        Node<T> currentNode = head;
        int index = 1;
        while (true) {
            if (index == left) {
                leftNode = currentNode;
            }
            if (index >= left && index <= right) {
                temp.add(currentNode.data);
            }
            if (index > right) {
                break;
            }
            currentNode = currentNode.next;
            index++;
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            leftNode.data = temp.get(i);
            leftNode = leftNode.next;
        }
        return head;
    }

    public void forEach(Node<T> node) {
        Node<T> current = node;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}