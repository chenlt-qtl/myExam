package com.exam;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

//@Component
@Data
@ConfigurationProperties(prefix = "my-datasource")
//3.开启对当前Bean的属性注入校验
@Validated
public class MyDataSource {
    private String driver;
    private String url;


    private String username;
    private String password;

    @NotEmpty
    @Max(value = 8888,message = "最大不能超过8888")
    private Integer port;


    @DurationUnit(ChronoUnit.HOURS)
    private Duration timeout;

    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize dataSize;

}
