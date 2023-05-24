package factory.simpleFactory.course;

public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("Java course");
    }
}
