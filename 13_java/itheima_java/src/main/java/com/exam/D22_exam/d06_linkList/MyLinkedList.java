package com.exam.D22_exam.d06_linkList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyLinkedList<E extends Comparable> {
    Node<E> head = null;

    /*大
     *定义了一个私有的内部类，作为链表的结点。*/
    public static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    public Node<E> add(E e) {
        if (head == null) {
            head = new Node(e, null);
        } else {
            //往后面插入结点。《尾插法)
            Node<E> temp = head;
            //让temp走到尾部结点
            while (temp.next != null) {
                temp = temp.next;
            }
            // 把当前结点创建出来，加入到尾部结点
            temp.next = new Node(e, null);

        }
        return head;
    }

    //功能1:在MuLinkedList类中，开发一个sort方法，对两个链表分别排序，并分别遍历输出
    public Node<E> sort() {
        List<E> list = new ArrayList<>();
        if (head != null) {
            //对data进行排序
            Node<E> temp = head;
            while (temp != null) {
                list.add(temp.data);
                temp = temp.next;
            }

            Collections.sort(list);
            //组装新的链表
            temp = head;
            for (E e : list) {
                temp.data = e;
                temp = temp.next;
            }
        }
        return head;
    }

    //功能2:在MyLinkedList类中，开发一个mergesort方法，支持将这两个升序排序的链表，合并成一个新链表，要求新链表中的节点仍然是递增
    public Node<E> mergeSort(MyLinkedList<E> list2) {
        Node<E> head2 = list2.head;
        if (head == null && head2 == null) {
            return null;
        } else {
            Node<E> temp = new Node<>(null,null);
            Node<E> newHead = temp;
            while (head != null && head2 != null) {
                if (head.data.compareTo(head2.data) == -1) {//比较大小， 小的放到temp的下一个
                    temp.next = head;
                    head = head.next;
                } else {
                    temp.next = head2;
                    head2 = head2.next;
                }
                temp = temp.next;
            }
            //把剩下的接到尾部
            if (head != null) {
                temp.next = head;
            } else {
                temp.next = head2;
            }
            this.head = newHead.next;
            return this.head;
        }
    }

    public void printLink() {
        Node<E> temp = head;
        //让temp走到尾部结点
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
