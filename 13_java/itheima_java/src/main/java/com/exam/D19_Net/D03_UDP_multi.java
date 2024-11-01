package com.exam.D19_Net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * 完成UDP通信，实现多发多收
 * 先启动接收端 再启动发送端
 */
public class D03_UDP_multi {

}

/**
 * 客户端（发送方）
 */
class Client2 {
    public static void main(String[] args) throws Exception {
        //创建发送端对象  系统随机生成一个端口
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入发送的内容:");
            String str = scanner.nextLine();
            if("exit".equals(str)){
                break;
            }
            byte[] data = str.getBytes();
            //创建数据包对象
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8888);
            //发送数据
            socket.send(packet);
        }
        System.out.println("退出成功");
        socket.close();
    }
}

/**
 * 服务端（接收方）
 */
class Server2 {
    public static void main(String[] args) throws Exception {
        System.out.println("-----------服务端启动--------------");
        //创建接收端对象
        DatagramSocket socket = new DatagramSocket(8888);

        //一包数据不会超过64KB
        byte[] data = new byte[1024 * 64];
        //创建一个数据包来接收数据
        DatagramPacket packet = new DatagramPacket(data, data.length);

        while (true) {
            //接收数据
            socket.receive(packet);
            //读取数据
            int length = packet.getLength();//接收到的数据长度
            System.out.println("接收到数据:"+new String(data,0,length));
            System.out.println("数据来自:"+packet.getAddress().getHostAddress()+":"+packet.getPort());
            System.out.println("-----------------------------------------");
        }
    }
}