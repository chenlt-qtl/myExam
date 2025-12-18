package com.betta.api.domain;

import lombok.Data;

@Data
public class CartVo {

    private long id;
    private long itemId;
    private String name;
    /**
     * 当前价格
     */
    private float currentPrice;

    /**
     * 放到购物车时的价格
     */
    private double price;

    /**
     * 商品库存
     */
    private int rest;
}
