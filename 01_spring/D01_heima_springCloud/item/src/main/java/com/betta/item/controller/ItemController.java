package com.betta.item.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.betta.api.domain.ItemVo;
import com.betta.common.utils.UserContext;
import com.betta.item.config.ItemProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api("商品模块")
@RestController
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemProperties itemProperties;

    @ApiOperation("获取商品信息")
    @GetMapping("/{ids}")
    @ApiImplicitParam(name = "ids", required = true, defaultValue = "1,3")
    public List<ItemVo> getItems(@PathVariable Long[] ids) {

        log.info("***** 接收到的ID：{}", CollUtil.join(Arrays.asList(ids), ","));

        log.info("***** 接收到的用户：{}", UserContext.getUser());

        int rest = itemProperties.getRest();

        log.info("***** 商品库存：{}", rest);

        List<ItemVo> list = new ArrayList<>();

        if (ArrayUtil.contains(ids, 3L)) {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(3);
            itemVo.setName("面条");
            itemVo.setPrice(18);
            itemVo.setRest(rest);
            list.add(itemVo);
        }
        if (ArrayUtil.contains(ids, 1L)) {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(1);
            itemVo.setName("鸡蛋");
            itemVo.setPrice(121);
            itemVo.setRest(rest);
            list.add(itemVo);
        }


        return list;
    }


}
