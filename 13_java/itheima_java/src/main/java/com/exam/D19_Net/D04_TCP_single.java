package com.exam.D19_Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 完成TCP通信，实现1发1收
 * 先启动接收端 再启动发送端
 */
public class D04_TCP_single {
}

class Server3{
    public static void main(String[] args) throws IOException {

        System.out.println("服务端启动成功");
        //创建服务端
        ServerSocket serverSocket = new ServerSocket(8888);

        //等待客户端连接请求
        Socket socket = serverSocket.accept();

        //从socket通信管理中等到一个字节输入流
        DataInputStream is = new DataInputStream(socket.getInputStream());

        //读取数据
        String msg = is.readUTF();
        System.out.println(msg);
        System.out.println(socket.getRemoteSocketAddress());

        //释放资源
        is.close();
        socket.close();
        serverSocket.close();

    }
}

class Client3{
    public static void main(String[] args) throws IOException {
        //创建socket对象，同时请求与服务端程序的连接
        Socket socket = new Socket("127.0.0.1", 8888);

        //从socket通信管理中得到一个字节输出流，用来发数据给服务端
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        os.writeUTF("你好");

        os.close();

        socket.close();//释放资源

    }
}