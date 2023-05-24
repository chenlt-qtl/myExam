package factory.simpleFactory.course;

public class Test {
    public static void main(String[] args) {
        ICourse course = CourseFactory.create(JavaCourse.class);
        course.record();
    }
}
