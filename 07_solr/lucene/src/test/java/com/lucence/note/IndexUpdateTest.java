package com.lucence.note;

import com.lucence.Utils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

public class IndexUpdateTest {


    @Test
    public void updateIndex() throws IOException {

        //1。需要变更的内容
        Document doc = new Document();
        doc.add(new StringField("id","9a6cf623fb50ab7a9fb8b52e2354fc2d",Field.Store.YES));
        doc.add(new TextField("name","工作清单111 ",Field.Store.YES));

        //5.创建输入流对象
        IndexWriter indexWriter = Utils.getIndexWriter(Utils.noteDir,new IKAnalyzer());
        //6。执行修改
        indexWriter.updateDocument(new Term("id","9a6cf623fb50ab7a9fb8b52e2354fc2d"),doc);
        //7。释放资源
        indexWriter.close();
    }


}
