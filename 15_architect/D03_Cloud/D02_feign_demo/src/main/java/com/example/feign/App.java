package com.example.feign;


import com.example.feign.annotation.EnableMyClient;
import com.example.feign.client.MyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


@EnableMyClient("com.example.feign.client")
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        //模拟测试controller
        TestClient bean = context.getBean(TestClient.class);
        bean.test();
    }

}




//模拟controller,在controller中调用接口的方法，实际是调用接口的代理
@Component
class TestClient{

    @Autowired
    private MyFeignClient myFeignClient;

    public void test(){

        System.out.println(myFeignClient.feign());
    }
}
