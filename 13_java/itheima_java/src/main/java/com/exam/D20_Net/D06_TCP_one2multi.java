package com.exam.D20_Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 完成TCP通信，实现与多个客户端同时通信
 */
public class D06_TCP_one2multi {
}

class Server5 {
    public static void main(String[] args) throws IOException {

        System.out.println("服务端启动成功");
        //创建服务端
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            //等待客户端连接请求
            Socket socket = serverSocket.accept();
            System.out.println("有人上线了"+socket.getRemoteSocketAddress());
            new ServerReaderThread(socket).start();
        }

    }
}

class ServerReaderThread extends Thread {

    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream is = new DataInputStream(socket.getInputStream());

        ) {
            //读取数据
            while (true) {
                //读取数据
                try {
                    String msg = is.readUTF();
                    System.out.println(msg);
                    System.out.println("---------------------");
                } catch (IOException e) {
                    System.out.println(socket.getRemoteSocketAddress() + "离线了");
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

class Client5 {
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