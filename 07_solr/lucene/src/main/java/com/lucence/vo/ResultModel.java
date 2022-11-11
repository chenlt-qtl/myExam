package com.lucence.vo;

import com.lucence.bean.Sentence;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultModel {

    private int currentPage;
    private long totalPage;
    private long totalRow;
    private List<Sentence> sentences = new ArrayList<>();

}
