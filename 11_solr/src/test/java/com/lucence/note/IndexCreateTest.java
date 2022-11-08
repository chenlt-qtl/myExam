package com.lucence.note;

import com.lucence.Utils;
import com.lucence.bean.Note;
import com.lucence.dao.impl.NoteDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class IndexCreateTest {

    @Test
    public void createNoteIndex() throws Exception {

        //1。采集数据
        NoteDaoImpl noteDao = new NoteDaoImpl();
        List<Note> noteList = noteDao.getNoteList();

        //2。创建文档集合对象
        List<Document> docList = new ArrayList<>();

        //3。创建文档对象
        noteList.forEach(note -> {
            if(StringUtils.isNotBlank(note.getName())) {
                Document document = new Document();
                document.add(new StringField("id", note.getId(), Field.Store.YES));
                document.add(new TextField("name", note.getName(), Field.Store.YES));
                docList.add(document);
            }
        });

        //7。创建输出流
        IndexWriter writer = Utils.getIndexWriter(Utils.noteDir,new IKAnalyzer());
        //8。写入索引库
        for (Document document : docList) {
            writer.addDocument(document);
        }
        //9。释放资源
        writer.close();
    }

}
