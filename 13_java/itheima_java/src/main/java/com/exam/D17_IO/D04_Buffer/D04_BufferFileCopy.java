package com.exam.D17_IO.D04_Buffer;

import java.io.*;

public class D04_BufferFileCopy {
    public static void main(String[] args) throws IOException {
        try (
                //把 123.jpg复制到 456.jpg  缓冲池大小16KB
                InputStream is = new BufferedInputStream(new FileInputStream("123.jpg"),1024*16);

                OutputStream os = new BufferedOutputStream(new FileOutputStream("456.jpg"),1024*16);
        ) {
            byte[] buffer = new byte[10];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }

    }
}
