package com.betta.api.domain;

import lombok.Data;

@Data
public class ItemVo {
    private long id;
    private String name;
    private float price;
    private int rest;
}
