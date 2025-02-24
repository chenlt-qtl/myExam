package com.example.feign.annotation;

import com.example.feign.client.FeignClientFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MyClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        //读取配置的EnableMyClient中配置的包路径
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableMyClient.class.getCanonicalName());
        String[] values = (String[]) attributes.get("value");//取出所有路径
        String[] basePackages = (String[]) attributes.get("basePackages");//取出所有路径
        Set<String> basePackageSet = new HashSet<>();//去重
        for (String value : values) {
            basePackageSet.add(value);
        }
        for (String basePackage : basePackages) {
            basePackageSet.add(basePackage);
        }

        //扫描出所有的feign
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet();//存放结果
        ClassPathScanningCandidateComponentProvider scanner = getScanner(registry);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyClient.class));
        basePackageSet.forEach(bp -> candidateComponents.addAll(scanner.findCandidateComponents(bp)));

        //注册bean
        candidateComponents.forEach(candidateComponent -> this.registerFeignClient(registry, candidateComponent));

    }

    /**
     * 注册Client到容器中，使用代理
     * @param registry
     * @param candidateComponent
     */
    private void registerFeignClient(BeanDefinitionRegistry registry, BeanDefinition candidateComponent) {
        if (candidateComponent instanceof AnnotatedBeanDefinition) {
            AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
            AnnotationMetadata metadata = beanDefinition.getMetadata();

            //取出注释的内容
            Map<String, Object> attributes = metadata.getAnnotationAttributes(MyClient.class.getCanonicalName());
            String name = (String) attributes.get("name");
            String type = "";
            Object typeObj = attributes.get("type");
            if (typeObj != null) {
                type = (String) typeObj;
            }

            //注册beanFactory到容器中
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
            builder.addPropertyValue("name",name);
            builder.addPropertyValue("type",type);
            builder.addPropertyValue("aClass",metadata.getClassName());//设置的时候用className即可
            AbstractBeanDefinition newBeanDefinition = builder.getBeanDefinition();
            newBeanDefinition.setAutowireMode(2);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(newBeanDefinition,metadata.getClassName());
            BeanDefinitionReaderUtils.registerBeanDefinition(holder,registry);


        }
    }

    private ClassPathScanningCandidateComponentProvider getScanner(BeanDefinitionRegistry registry) {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface();
            }
        };
    }
}
