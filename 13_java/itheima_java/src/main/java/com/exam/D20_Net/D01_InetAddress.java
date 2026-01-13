package com.exam.D20_Net;

import java.io.IOException;
import java.net.InetAddress;

public class D01_InetAddress {
    public static void main(String[] args) throws IOException {
        //获取本机IP地址
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("HostName: " + ip.getHostName());
        System.out.println("HostAddress: " + ip.getHostAddress());

        //获取指定域名或者IP的地址对象
        InetAddress baidu = InetAddress.getByName("www.baidu.com");
        System.out.println("HostName: " + baidu.getHostName());
        System.out.println("HostAddress: " + baidu.getHostAddress());

        InetAddress ipAddress = InetAddress.getByName("10.22.1.33");

        //本机与百度6秒内是否能连接上
        System.out.println(baidu.isReachable(6000));
    }
}
