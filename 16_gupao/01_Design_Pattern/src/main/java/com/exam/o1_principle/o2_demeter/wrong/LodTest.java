package com.exam.o1_principle.o2_demeter.wrong;

public class LodTest {
    public static void main(String[] args) {
        TeamLeader teamLeader = new TeamLeader();
        Employee employee = new Employee();
        teamLeader.commandCheckNum(employee);
    }
}
