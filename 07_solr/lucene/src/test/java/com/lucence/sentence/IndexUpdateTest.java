package com.lucence.sentence;

import com.lucence.Utils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.junit.Test;

import java.io.IOException;

public class IndexUpdateTest {


    @Test
    public void updateIndex() throws IOException {

        //1。需要变更的内容
        Document doc = new Document();
        doc.add(new StringField("id","9c3b23526a1877a586e16619413ff510",Field.Store.YES));
        doc.add(new TextField("content","Damu, so  what  do  you  do  over  there ?",Field.Store.YES));

        //5.创建输入流对象
        IndexWriter indexWriter = Utils.getIndexWriter(Utils.sentenceDir,new EnglishAnalyzer());
        //6。执行修改
        indexWriter.updateDocument(new Term("id","9c3b23526a1877a586e16619413ff510"),doc);
        //7。释放资源
        indexWriter.close();
    }


}
