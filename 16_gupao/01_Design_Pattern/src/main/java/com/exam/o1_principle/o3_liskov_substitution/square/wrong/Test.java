package com.exam.o1_principle.o3_liskov_substitution.square.wrong;

public class Test {

    public static void resize(Rectangle rectangle){
        while (rectangle.getWidth()>=rectangle.getHeight()){
            rectangle.setHeight(rectangle.getHeight()+1);
            System.out.println("Width: "+rectangle.getWidth()+", Height: "+rectangle.getHeight());
        }
        System.out.println("after Width: "+rectangle.getWidth()+", Height: "+rectangle.getHeight());
    }
    public static void main(String[] args) {
        //长方形结果正常
//        Rectangle rectangle = new Rectangle();
//        rectangle.setWidth(20);
//        rectangle.setHeight(10);
//        resize(rectangle);

        //正方形会死循环 不符合里氏替换原则
        Square square = new Square();
        square.setLength(10);
        resize(square);
    }
}
