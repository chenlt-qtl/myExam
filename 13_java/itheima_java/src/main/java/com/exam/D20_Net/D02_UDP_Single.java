package com.exam.D20_Net;

import java.net.*;

/**
 * 完成UDP通信，实现1发1收
 * 先启动接收端 再启动发送端
 */
public class D02_UDP_Single {

}

/**
 * 客户端（发送方）
 */
class Client {
    public static void main(String[] args) throws Exception {
        //创建发送端对象  系统随机生成一个端口
        DatagramSocket socket = new DatagramSocket();

        //创建数据包对象
        byte[] data = "你瞅啥".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8888);
        //发送数据
        socket.send(packet);
        System.out.println("数据发送出去了");
        socket.close();//释放资源
    }
}

/**
 * 服务端（接收方）
 */
class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("-----------服务端启动--------------");
        //创建接收端对象
        DatagramSocket socket = new DatagramSocket(8888);

        //一包数据不会超过64KB
        byte[] data = new byte[1024 * 64];
        //创建一个数据包来接收数据
        DatagramPacket packet = new DatagramPacket(data, data.length);

        //接收数据
        socket.receive(packet);
        //读取数据
        int length = packet.getLength();//接收到的数据长度
        System.out.println("接收到数据:"+new String(data,0,length));
        System.out.println("数据来自:"+packet.getAddress().getHostAddress()+":"+packet.getPort());

        //关闭资源
        socket.close();
    }
}