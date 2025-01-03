package com.exam.utils.FileUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * 打印目录下面所有MP4 完成时间 名称和时长
 */
public class App {


    private static int minHour = 7;
    private static LocalDate startDate = LocalDate.of(2025, 1, 3);

    public static void main(String[] args) throws Exception {
//        ListFile.listFile("E:\\学习\\架构师\\视频\\12 第十二章 企业级解决方案");
        List<Media> medias = ReadFile.readFile(startDate,minHour);

        LocalDate temp = LocalDate.of(2000, 1, 1);;
        for (Media media : medias) {
            if(!temp.equals(media.getFinishDate())){
                temp = media.getFinishDate();
                System.out.println(String.format("\n*************** %s **************",temp));
            }
            System.out.println(media);
        }


    }


}

