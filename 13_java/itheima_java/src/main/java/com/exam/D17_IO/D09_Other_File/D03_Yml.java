package com.exam.D17_IO.D09_Other_File;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class D03_Yml {
    public static void main(String[] args) {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("file\\D17_09_03.yml"));) {
            Yaml yaml = new Yaml();
            Map map = yaml.load(inputStream);//加载数据  使用loadAs可加载成对象
            List<Map> list = (List<Map>) map.get("children");
            System.out.println(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
