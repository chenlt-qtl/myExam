package com.exam.o1_principle.o2_demeter.right;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    public void checkNumOfCourse() {
        List<Course> list = new ArrayList<Course>();
        for(int i=0;i<20;i++){
            list.add(new Course());
        }
        System.out.println("目前已发布的课程数量为"+list.size());
    };
}
