package com.exam.o1_principle.o2_demeter.wrong;

import java.util.List;

public class Employee {
    public void checkNumOfCourse(List<Course> courses) {
        System.out.println("目前已发布的课程数量为"+courses.size());
    };
}
