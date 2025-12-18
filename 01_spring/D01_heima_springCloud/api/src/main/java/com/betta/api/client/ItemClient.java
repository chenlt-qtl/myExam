package com.betta.api.client;


import com.betta.api.config.DefaultFeignConfig;
import com.betta.api.domain.ItemVo;
import com.betta.api.fallback.ItemClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "item-service",configuration = DefaultFeignConfig.class,fallbackFactory = ItemClientFallback.class)
public interface ItemClient {

    @GetMapping("/items/{ids}")
    public List<ItemVo> getItems(@PathVariable("ids") Long[] ids) ;
}
