package com.lucence.sentence;

import com.lucence.Utils;
import com.lucence.bean.Sentence;
import com.lucence.dao.SentenceDao;
import com.lucence.dao.impl.SentenceDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IndexCreateTest {

    @Test
    public void createSentenceIndex() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //1。采集数据
        SentenceDao sentenceDao = new SentenceDaoImpl();
        List<Sentence> sentenceList = sentenceDao.getList();

        //2。创建文档集合对象
        List<Document> docList = new ArrayList<>();

        //3。创建文档对象
        sentenceList.forEach(note -> {
            if(StringUtils.isNotBlank(note.getContent())) {
                Document document = new Document();
                document.add(new StringField("id", note.getId(), Field.Store.YES));
                document.add(new TextField("content", note.getContent(), Field.Store.YES));
                //处理需要查询的数字
                document.add(new IntPoint("idx", note.getIdx()));
                document.add(new StoredField("idx", note.getIdx()));
                //处理需要查询的日期
                document.add(new LongPoint("createTime", note.getCreateTime().getTime()));
                document.add(new StoredField("createTime", sdf.format(note.getCreateTime())));

                docList.add(document);
            }
        });

        //7。创建输出流
        IndexWriter writer = Utils.getIndexWriter(Utils.sentenceDir,new EnglishAnalyzer());
        //8。写入索引库
        for (Document document : docList) {
            writer.addDocument(document);
        }
        //9。释放资源
        writer.close();
    }

}
