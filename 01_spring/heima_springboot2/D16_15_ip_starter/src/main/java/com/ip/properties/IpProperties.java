package com.ip.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("ipProperties")
@ConfigurationProperties(prefix = "tools.ip")
public class IpProperties {

    /**
     * 日志显示周期
     */
    private Long cycle = 5L;

    /**
     * 是否周期内重置数据
     */
    private Boolean cycleReset = false;

    /**
     * 日志输出格式 detail：详细模式  simple：极简模式
     */

    private String modal = LogModal.DETAIL.name;

    public enum LogModal{
        DETAIL("detail"),SIMPLE("simple");
        private String name;
        LogModal(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
