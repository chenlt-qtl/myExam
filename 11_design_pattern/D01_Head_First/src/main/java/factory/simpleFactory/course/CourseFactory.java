package factory.simpleFactory.course;

public class CourseFactory {
    public static ICourse create(Class clazz){

        if(clazz!=null){
            try {
                return (ICourse) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
