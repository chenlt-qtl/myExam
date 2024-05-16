package com.seed.service.controller;


import com.seed.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("{id}")
    public String order(@PathVariable String id){
        orderService.order(id);
        return "ok";
    }
}
