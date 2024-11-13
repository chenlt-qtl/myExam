package com.ip;

import com.ip.properties.IpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class IpCountService {

    private Map<String, Integer> ipCountMap = new HashMap();

    //当前的Request对象的注入工作山使用当前starter的工程提供自动装配
    @Autowired
    private HttpServletRequest request;

    public void count() {
        System.out.println("-----------------------------");
        //每次调用当前操作,记录访问的IP，并增加访问次数
        //1. 获取当前操作的IP地址
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        //2. 根据IP地址从MAP中获取数据，并递增
        if (ipCountMap.containsKey(ip)) {
            ipCountMap.put(ip, ipCountMap.get(ip) + 1);
        } else {
            ipCountMap.put(ip, 1);
        }
    }

    @Autowired
    private IpProperties ipProperties;

    //设置默认值为5
    //@Scheduled(cron = "0/${tools.ip.cycle:5} * * * * ?")
    @Scheduled(cron = "0/#{ipProperties.cycle} * * * * ?")
    public void print() {

        if (IpProperties.LogModal.DETAIL.getName().equals(ipProperties.getModal())) {

            System.out.println("        IP访问监控");
            System.out.println("+-----IP ADDRESS-----+----NUM----+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                System.out.println(String.format("|%18s  |%9d  |", entry.getKey(), entry.getValue()));
            }
            System.out.println("+--------------------+-----------+");
        } else if (IpProperties.LogModal.SIMPLE.getName().equals(ipProperties.getModal())) {
            System.out.println("     IP访问监控");
            System.out.println("+-----IP ADDRESS-----+");
            for (String key : ipCountMap.keySet()) {
                System.out.println(String.format("|%18s  |", key));
            }
            System.out.println("+--------------------+");
        }

        if (ipProperties.getCycleReset()) {
            ipCountMap.clear();
        }
    }

    public static void main(String[] args) {
        new IpCountService().print();
    }
}
