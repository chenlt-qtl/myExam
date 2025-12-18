package com.betta.cart.controller;

import com.betta.api.domain.CartVo;
import com.betta.cart.service.ICartService;
import com.betta.common.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api("购物车模块")
@RestController
@RequestMapping(("/cart"))
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @ApiOperation("获取购物车信息")
    @GetMapping
    public List<CartVo> getCart(@RequestHeader(required = false) String saySomeThing,@RequestHeader(required = false) String whatTheHell, @RequestHeader(required = false, name = "user-info") String userInfo){
        System.out.println("用户ID：" + UserContext.getUser());
        System.out.println(saySomeThing);
        System.out.println(whatTheHell);
        return cartService.getCart();
    }
}
