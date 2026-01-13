package com.exam.D22_exam.d05_login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        System.out.println("连接服务器成功");
        Scanner scanner = new Scanner(System.in);
        try (
                DataOutputStream bos = new DataOutputStream(socket.getOutputStream());
                DataInputStream bis = new DataInputStream(socket.getInputStream());
        ) {
            while (true) {
                System.out.println("输入1进行注册，输入2登录系统，输入exit退出");
                System.out.println("请输入：");
                String str = scanner.nextLine();
                if ("exit".equals(str)) {
                    System.out.println("谢谢使用，再见");
                    socket.close();
                    break;
                } else if ("1".equals(str) || "2".equals(str)) {

                    if ("1".equals(str)) {
                        System.out.println("请输入要注册的帐号");
                        bos.writeUTF("register:");

                    } else if ("2".equals(str)) {
                        System.out.println("请输入要登录的帐号");
                        bos.writeUTF("login:");
                    }
                    String userName = scanner.nextLine();
                    System.out.println("请输入密码");
                    String password = scanner.nextLine();
                    bos.writeUTF(userName);
                    bos.writeUTF(password);
                    String s = bis.readUTF();
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
