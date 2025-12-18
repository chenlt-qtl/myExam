package com.exam.D20_Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 综合案例  群聊
 */
public class D07_GroupChat {
}

class Server6 {

    public static List<Socket> onlineSocket = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        System.out.println("服务端启动成功");
        //创建服务端
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            //等待客户端连接请求
            Socket socket = serverSocket.accept();
            System.out.println("有人上线了" + socket.getRemoteSocketAddress());
            onlineSocket.add(socket);
            new ServerReaderThread1(socket).start();
        }

    }
}

class ServerReaderThread1 extends Thread {

    private Socket socket;

    public ServerReaderThread1(Socket socket) {
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
                    sendMsgToAll(msg);
                } catch (IOException e) {
                    System.out.println(socket.getRemoteSocketAddress() + "离线了");
                    Server6.onlineSocket.remove(socket);
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 给所有客户发送消息
     *
     * @param msg
     */
    private void sendMsgToAll(String msg) throws Exception {

        for (Socket socket : Server6.onlineSocket) {
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.writeUTF(msg);
            os.flush();//刷新消息
        }
    }
}

class Client6 {
    public static void main(String[] args) throws IOException {
        //创建socket对象，同时请求与服务端程序的连接
        Socket socket = new Socket("127.0.0.1", 8888);

        //创建一个独立的线程，负责从服务端收消息
        new ClientReader6(socket).start();

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

class ClientReader6 extends Thread {
    private Socket socket;

    public ClientReader6(Socket socket) {
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
                    System.out.println("离线了");
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}