package com.betta.cart.service.impl;

import com.betta.api.client.ItemClient;
import com.betta.api.domain.CartVo;
import com.betta.api.domain.ItemVo;
import com.betta.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ItemClient itemClient;

    /**
     * 获取购物车里的信息
     * @return
     */
    @Override
    public List<CartVo> getCart() {
        List<CartVo> result = new ArrayList<>();
        //1.获取购物车里的商品
        CartVo cartVo = new CartVo();
        cartVo.setItemId(3);
        cartVo.setId(1);
        cartVo.setName("面条");
        cartVo.setPrice(33.4);

        //2.获取商品的当前价格
        List<ItemVo> items = itemClient.getItems(new Long[]{3L});

        if(!items.isEmpty()) {
            ItemVo itemVo = items.get(0);
            cartVo.setCurrentPrice(itemVo.getPrice());
            cartVo.setRest(itemVo.getRest());
        }
        //3.处理返回数据
        result.add(cartVo);
        return result;
    }
}
