package com.exam.utils.FileUtils;

import org.mp4parser.IsoFile;

import java.io.File;


public class ListFile {

    //解析所有子文件的时间和名称
    public static void listFile(String dir){
        File file = new File(dir);
        try {
            getChild(file.listFiles());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void getChild(File[] files) throws Exception {
        for (File file : files) {
            //如果是文件，取出文件名称和时长
            if (!file.isDirectory()) {
                String name = file.getName();
                if(name.substring(name.lastIndexOf(".")).equalsIgnoreCase(".mp4")) {
                    IsoFile isoFile = new IsoFile(file);
                    long duration = isoFile.getMovieBox().getMovieHeaderBox().getDuration();
                    long timescale = isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
                    System.out.println(duration/timescale+"%%"+file.getName());
                }
            } else {
                getChild(file.listFiles());
            }
        }

    }
}
