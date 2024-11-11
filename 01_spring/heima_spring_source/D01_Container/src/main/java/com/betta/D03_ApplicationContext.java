package com.betta;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;


/**
 * 常见ApplicationContext实现
 * 1. 常见的ApplicationContext容器实现
 * 2. 内嵌容器、DispatcherServlet的创建方法，作用
 */
public class D03_ApplicationContext {
    public static void main(String[] args) {
        //---------------------基于XML文件--------------------------
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
        //工作原理
        /**
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("加载之前");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        reader.loadBeanDefinitions(new ClassPathResource("D03.xml"));
        reader.loadBeanDefinitions(new FileSystemResource("src\\main\\resources\\D03.xml"));

        System.out.println("加载之后");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
         */

        //---------------------基于配置文件--------------------------
        //testAnnotationConfigApplicationContext();

        //web环境
        testAnnotationConfigServletWebServerApplicationContext();
    }

    //较为经典的容器，基于classpath下XML格式的配置文件来创建
    private static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("D03.xml");
        printBean(context);
    }

    //基于磁盘路径下xml格式的配置文件来创建
    private static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\D03.xml");
        printBean(context);
    }

    //较为经典的容器，基于java配置类来创建
    private static void testAnnotationConfigApplicationContext() {
        System.out.println("------testAnnotationConfigApplicationContext-----");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        printBean(context);
    }

    //较为经典的容器，基于JAVA配置类来创建，用于WEB环境
    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    private static void printBean(ApplicationContext context) {
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    @Configuration
    static class WebConfig {

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServletPath() {
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller controller1() {
            return (request, response) -> {
                response.getWriter().write("hello,Unag!.");
                return null;
            };
        }
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {

            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1 {

    }

    static class Bean2 {


        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }


}
