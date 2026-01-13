package factory.simpleFactory.course;

public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("python course");
    }
}
