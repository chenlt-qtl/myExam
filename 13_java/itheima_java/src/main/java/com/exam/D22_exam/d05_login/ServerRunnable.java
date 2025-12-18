package com.exam.D22_exam.d05_login;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ServerRunnable implements Runnable {
    private Socket socket;

    private static final String PATH = "file\\05_login.properties";

    public ServerRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Properties properties = loadFile();
        try (
                DataOutputStream bos = new DataOutputStream(socket.getOutputStream());
                DataInputStream bis = new DataInputStream(socket.getInputStream());
        ) {
            while (true) {
                System.out.println("准备接收信息");
                String s = bis.readUTF();
                String userName = bis.readUTF();
                String password = bis.readUTF();

                //登录
                if ("login:".equals(s)) {
                    if (!properties.containsKey(userName)) {
                        bos.writeUTF("用户名不存在");
                    } else if (!properties.get(userName).equals(password)) {
                        bos.writeUTF("密码不正确");
                    } else {
                        bos.writeUTF("登录成功");
                    }
                } else if ("register:".equals(s)) {//注册
                    if (properties.containsKey(userName)) {
                        bos.writeUTF("用户名已存在");
                    }else {
                        properties.put(userName,password);
                        properties.store(new FileWriter(PATH),"register");
                        bos.writeUTF("注册成功");
                    }
                }
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的用户信息
     *
     * @return
     */
    private Properties loadFile() {
        Properties properties = new Properties();
        File file = new File(PATH);
        if(!file.exists()){
            return properties;
        }

        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
