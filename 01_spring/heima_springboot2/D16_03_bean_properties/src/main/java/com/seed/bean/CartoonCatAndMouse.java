package com.seed.bean;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

@Data
@ConditionalOnClass(name = "com.seed.bean.Cat")
@EnableConfigurationProperties(CartoonProperties.class)
public class CartoonCatAndMouse implements ApplicationContextAware {

    private Cat cat;
    private Mouse mouse;

    public CartoonCatAndMouse(CartoonProperties properties) {
        cat = new Cat();
        Cat catProp = properties.getCat();
        cat.setAge(3);
        cat.setName("tom");

        if (catProp != null) {
            if (StringUtils.hasText(catProp.getName())) {
                cat.setName(catProp.getName());
            }
            if (catProp.getAge() != null) {
                cat.setAge(catProp.getAge());
            }
        }


        mouse = new Mouse();
        Mouse mouseProp = properties.getMouse();
        mouse.setName("jerry");
        mouse.setAge(4);

        if (mouseProp != null) {
            if (StringUtils.hasText(mouseProp.getName())) {
                mouse.setName(mouseProp.getName());
            }
            if (mouseProp.getAge() != null) {
                mouse.setAge(mouseProp.getAge());
            }
        }

    }

    public void play() {
        System.out.println("------------------------------------------");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
//            System.out.println(name);
        }
        System.out.println(cat.getAge() + "岁的" + cat.getName() + "和" + mouse.getAge() + "岁的" + mouse.getName() + "打起来了");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
