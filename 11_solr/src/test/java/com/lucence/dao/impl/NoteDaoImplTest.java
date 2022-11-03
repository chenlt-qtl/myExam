package com.lucence.dao.impl;

import com.lucence.bean.Note;
import org.junit.Test;

import java.util.List;

public class NoteDaoImplTest {

    @Test
    public void getNoteList() {
        NoteDaoImpl noteDao = new NoteDaoImpl();
        List<Note> noteList = noteDao.getNoteList();
        System.out.println(noteList);
    }
}