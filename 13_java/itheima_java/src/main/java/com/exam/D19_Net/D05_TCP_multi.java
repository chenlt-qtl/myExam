package com.exam.D19_Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 完成TCP通信，实现多发多收
 * 先启动接收端 再启动发送端
 */
public class D05_TCP_multi {
}

class Server4 {
    public static void main(String[] args) throws IOException {

        System.out.println("服务端启动成功");
        //创建服务端
        ServerSocket serverSocket = new ServerSocket(8888);

        //等待客户端连接请求
        Socket socket = serverSocket.accept();

        //从socket通信管理中等到一个字节输入流
        DataInputStream is = new DataInputStream(socket.getInputStream());

        while (true) {
            //读取数据
            try {
                String msg = is.readUTF();
                System.out.println(msg);
                System.out.println("---------------------");
            } catch (IOException e) {
                System.out.println(socket.getRemoteSocketAddress() + "离线了");
                is.close();
                socket.close();
                break;
            }
        }

    }
}

class Client4 {
    public static void main(String[] args) throws IOException {
        //创建socket对象，同时请求与服务端程序的连接
        Socket socket = new Socket("127.0.0.1", 8888);

        //从socket通信管理中得到一个字节输出流，用来发数据给服务端
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入消息(exit退出):");
            String s = scanner.nextLine();
            if ("exit".equalsIgnoreCase(s)) {
                System.out.println("退出成功");
                break;
            }
            os.writeUTF(s);
            os.flush();//刷新消息
        }

        os.close();
        socket.close();//释放资源

    }
}