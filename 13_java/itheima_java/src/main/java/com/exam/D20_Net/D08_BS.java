package com.exam.D20_Net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 要求从浏览器中访问服务器，展示网页内容
 */
public class D08_BS {
    public static void main(String[] args) {

    }
}

class Server7{
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器已启动");

        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("收到客户端连接，来自："+socket.getRemoteSocketAddress());
            new ServerThread7(socket).start();
        }

    }
}

class ServerThread7 extends Thread{
    private Socket socket;

    public ServerThread7(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //响应一个网页内容给浏览器展示
        try (PrintStream os = new PrintStream(socket.getOutputStream());){
            //格式
            os.println("HTTP/1.1 200 OK");
            os.println("Content-Type:text/html;charset=UTF-8");
            os.println();//必须有一个空行
            os.println("欢迎光临!666");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}