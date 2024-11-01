package com.exam.D19_Net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class D09_BS_ThreadPool {
}

class Server8{
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 8, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("服务器已启动");


        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("收到客户端连接，来自："+socket.getRemoteSocketAddress());
            pool.execute(new ServerRunnable8(socket));
        }

    }
}

class ServerRunnable8 implements Runnable{
    private Socket socket;

    public ServerRunnable8(Socket socket) {
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
            System.out.println("使用"+Thread.currentThread().getName());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
