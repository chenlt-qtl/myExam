package com.exam.o1_principle.o1_interface_segregation.wrong;

import com.exam.o1_principle.o1_interface_segregation.right.IEatAnimal;
import com.exam.o1_principle.o1_interface_segregation.right.ISwimAnimal;

public class Dog implements IAnimal {
    @Override
    public void eat() {

    }

    @Override
    public void swim() {

    }

    @Override
    public void fly() {

    }
}
