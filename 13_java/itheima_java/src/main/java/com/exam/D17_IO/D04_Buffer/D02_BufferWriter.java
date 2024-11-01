package com.exam.D17_IO.D04_Buffer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class D02_BufferWriter {
    public static void main(String[] args) {
        try(
                Writer wt = new FileWriter("bufferWriter.txt",false);
                BufferedWriter bw = new BufferedWriter(wt);
                ){
            bw.write(97);
            bw.write("你");
            bw.newLine();
            bw.write(new char[]{97});

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
