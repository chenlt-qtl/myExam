package com.betta.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class DynamicRouterLoader {

    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter writer;
    private final String dataId = "gateway-routers.json";
    private final String group = "DEFAULT_GROUP";

    //保存已加载的路由的ID
    private List<String> routeIds = new ArrayList<>();

    @PostConstruct
    private void initRouterConfigListener() throws NacosException {
        //拉取配置并添加监听器
        String configInfo = nacosConfigManager.getConfigService().getConfigAndSignListener(dataId, group, 5000,
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String s) {
                        //配置数据有更新时更新路由
                        updateConfigInfo(s);
                    }
                });
        //第一次读取到数据也需要更新下路由
        updateConfigInfo(configInfo);
    }

    private void updateConfigInfo(String configInfo) {
        //1.解析配置信息，转为RouteDefinition
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo,RouteDefinition.class);

        //2.删除旧路由
        for (String routeId : routeIds) {
            writer.delete(Mono.just(routeId)).subscribe();
        }
        routeIds.clear();

        //3.更新路由表
        for (RouteDefinition routeDefinition : routeDefinitions) {
            //更新路由表
            writer.save(Mono.just(routeDefinition)).subscribe();

            //记录路由ID，用于下一次更新
            routeIds.add(routeDefinition.getId());
        }
    }

}
