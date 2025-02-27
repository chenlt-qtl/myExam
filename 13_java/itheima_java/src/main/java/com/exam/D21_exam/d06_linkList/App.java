package com.exam.D21_exam.d06_linkList;

public class App {
    public static void main(String[] args) {//第一个链表:
        MyLinkedList<Integer> list1 = new MyLinkedList<>();
        list1.add(2);
        list1.add(4);
        list1.add(1);
        // 第一个链表的头结点:head1
        MyLinkedList.Node<Integer> head1 = list1.head;
        // 第二个链表:
        MyLinkedList<Integer> list2 = new MyLinkedList<>();
        list2.add(4);
        list2.add(1);
        list2.add(3);
        list2.add(3);
        list2.add(7);
        list2.add(9);
        list2.add(8);
        //第二个链表的头结点:head1
        MyLinkedList.Node<Integer> head2 = list2.head;
        //功能1:在MuLinkedList类中，开发一个sort方法，对两个链表分别排序，并分别遍历输出
        list1.sort();
        list1.printLink();

        list2.sort();
        list2.printLink();
        //功能2:在MyLinkedList类中，开发一个mergesort方法，支持将这两个升序排序的链表，合并成一个新链表，要求新链表中的节点仍然是递增
        list1.mergeSort(list2);
        list1.printLink();
        // 然后对新链表遍历输出
    }
}
