package com.betta.D04;

import java.util.ArrayList;
import java.util.List;

public class D02_MethodTemplate {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addProcessor(object -> System.out.println("解析@Autowire"+object));
        beanFactory.addProcessor(object -> System.out.println("解析@Resource"+object));
        beanFactory.getBean();
    }

    //模板方法
    static class MyBeanFactory{
        List<BeanPostProcessor> processors = new ArrayList<>();
        //固定的代码写在方法里，变化的代码写成接口，方便扩展
        public Object getBean(){
            Object bean = new Object();
            System.out.println("构造"+bean);
            System.out.println("依赖注入"+bean);
            for (BeanPostProcessor processor : processors) {
                processor.inject(bean);
            }
            System.out.println("初始化"+bean);
            return bean;
        }

        public void addProcessor(BeanPostProcessor processor){
            processors.add(processor);
        }
    }

    static interface BeanPostProcessor{
        void inject(Object object);//对依赖注入阶段的扩展
    }
}
