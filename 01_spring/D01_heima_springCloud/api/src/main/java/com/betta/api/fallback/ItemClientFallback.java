package com.betta.api.fallback;

import cn.hutool.core.collection.CollUtil;
import com.betta.api.client.ItemClient;
import com.betta.api.domain.ItemVo;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.ArrayList;
import java.util.List;

public class ItemClientFallback implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemVo> getItems(Long[] ids) {
                System.out.println("------------访问item失败----------");
                return new ArrayList<>();
            }
        };
    }
}
