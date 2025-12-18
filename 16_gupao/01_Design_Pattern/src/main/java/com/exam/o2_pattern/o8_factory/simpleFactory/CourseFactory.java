package com.exam.o2_pattern.o8_factory.simpleFactory;

public class CourseFactory {
    public ICourse create(String className){
//        if("java".equalsIgnoreCase(name)){
//            return new JavaCourse();
//        }else if("python".equalsIgnoreCase(name)){
//            return new PythonCourse();
//        }else{
//            return null;
//        }
        if(null != className&&!"".equals(className)){
            try {
                return (ICourse)Class.forName(className).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ICourse create(Class<?extends ICourse> clazz){
        if(null != clazz){
            try {
                return (ICourse)clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


}
