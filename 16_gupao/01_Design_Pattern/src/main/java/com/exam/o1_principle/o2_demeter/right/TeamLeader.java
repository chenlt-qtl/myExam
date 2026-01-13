package com.exam.o1_principle.o2_demeter.right;

public class TeamLeader {
    public void commandCheckNum(Employee employee) {
        employee.checkNumOfCourse();
    }
}
