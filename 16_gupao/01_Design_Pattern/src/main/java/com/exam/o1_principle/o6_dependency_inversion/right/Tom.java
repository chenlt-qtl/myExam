package com.exam.o1_principle.o6_dependency_inversion.right;

public class Tom {
    private ICourse course;

    public Tom(ICourse course) {
        this.course = course;
    }

    public void study(ICourse course){
        course.study();
    }

    public void study(){
        course.study();
    }

    public Tom() {
    }


    public void setCourse(ICourse course) {
        this.course = course;
    }
}
