package com.betta.cart.service;

import com.betta.api.domain.CartVo;

import java.util.List;


public interface ICartService {

    List<CartVo> getCart();
}
