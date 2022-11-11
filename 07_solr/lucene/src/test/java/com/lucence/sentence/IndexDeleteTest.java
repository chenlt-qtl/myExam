package com.lucence.sentence;

import com.lucence.Utils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

public class IndexDeleteTest {


    @Test
    public void deleteAllSenIndex() throws IOException {
        //1。指定索引库位置
        Directory directory = FSDirectory.open(Paths.get(Utils.sentenceDir));
        //2。创建输出流对象
        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig());
        //3。执行删除
        indexWriter.deleteAll();
        //4。释放资源
        indexWriter.close();
    }

    @Test
    public void deleteIndex() throws IOException {
        //1。指定索引库位置
        Directory directory = FSDirectory.open(Paths.get(Utils.sentenceDir));
        //2。创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //3。创建Config初始化对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4。创建输出流对象
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //5。执行删除
        indexWriter.deleteDocuments(new Term("id","9a6cf623fb50ab7a9fb8b52e2354fc2d"));
        //6。释放资源
        indexWriter.close();
    }
}
