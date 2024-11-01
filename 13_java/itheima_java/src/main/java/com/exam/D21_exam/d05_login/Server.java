package com.exam.D21_exam.d05_login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 8, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("服务器已启动");


        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("收到客户端连接，来自：" + socket.getRemoteSocketAddress());
            pool.execute(new ServerRunnable(socket));
        }
    }

}
