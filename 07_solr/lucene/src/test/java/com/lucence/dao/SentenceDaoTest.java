package com.lucence.dao;

import com.lucence.bean.Sentence;
import com.lucence.dao.impl.SentenceDaoImpl;
import org.junit.Test;

import java.util.List;

public class SentenceDaoTest {

    @Test
    public void getList() {

        SentenceDao sentenceDao = new SentenceDaoImpl();
        List<Sentence> list = sentenceDao.getList();
        System.out.println(list);

    }
}