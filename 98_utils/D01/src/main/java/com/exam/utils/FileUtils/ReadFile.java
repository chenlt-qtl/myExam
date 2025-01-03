package com.exam.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 解析文件，取出 完成日期 时间和名称
 */
public class ReadFile {


    public static List<Media> readFile(LocalDate date,int minHour){
        List<Media> medias = new ArrayList<>();
        try (
                Reader rd = new FileReader("file/FileList.txt");
                BufferedReader bis = new BufferedReader(rd);
        ) {

            String str;
            while ((str = bis.readLine()) != null) {
                String[] split = str.split("%%");
                Media media = new Media();
                media.setSecond(Long.valueOf(split[0]));
                media.setName(split[1]);
                medias.add(media);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        long totalTime = 0;
        Iterator<Media> iterator = medias.iterator();
        while (iterator.hasNext()){
            Media media = iterator.next();
            //时间已足够 重置totalTime data加一天（如果是周末 跳过）
            if (totalTime > minHour * 60 * 60) {
                totalTime = 0;
                date = date.plusDays(1);
                while (DayOfWeek.SATURDAY.equals(date.getDayOfWeek())||DayOfWeek.SUNDAY.equals(date.getDayOfWeek())){
                    date = date.plusDays(1);
                }
            }
            totalTime += media.getSecond();
            media.setFinishDate(date);
        }
        return medias;
    }
}
