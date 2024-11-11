package com.seed.actuator;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "pay", enableByDefault = true)
public class PayEndpoint {

    @ReadOperation
    public Object getPay(){
        return "支付宝到账100元";
    }
}
