package com.exam.o1_principle.o2_demeter.wrong;

import java.util.ArrayList;
import java.util.List;

public class TeamLeader {
    public void commandCheckNum(Employee employee) {
        List<Course> list = new ArrayList<Course>();
        for(int i=0;i<20;i++){
            list.add(new Course());
        }
        employee.checkNumOfCourse(list);
    }
}
