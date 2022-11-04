package com.lucence.dao.impl;

import com.lucence.bean.Note;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexManageTest {

    String indexDir = "E:\\luceneIndex";

    @Test
    public void createIndex() throws Exception {

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
        //4。创建分词器
        Analyzer analyzer=new IKAnalyzer();
        //5。创建Directory目录对象，指定索引库位置
        //FSDirectory : file system directory 写到硬盘里
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        //6。指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //7。创建输出流
        IndexWriter writer = new IndexWriter(directory,config);
        //8。写入索引库
        for (Document document : docList) {
            writer.addDocument(document);
        }
        //9。释放资源
        writer.close();
    }

    @Test
    public void searchIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //2。创建查询对象
        QueryParser queryParser = new QueryParser("name",analyzer);
        //3。设置搜索关键词
        Query query = queryParser.parse("name:工作清单");
        //4。指定索引库位置
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        //5。创建输入流
        IndexReader reader = DirectoryReader.open(directory);
        //6。创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //7.搜索并返回结果
        TopDocs topDocs = indexSearcher.search(query,10);
        //8。获取结果集
        System.out.println("=========总记录数========"+topDocs.totalHits);
        //9。遍历结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if(scoreDocs!=null){
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docID = scoreDoc.doc;
                Document doc = indexSearcher.doc(docID);
                System.out.println("=========="+doc.get("id"));
                System.out.println("=========="+doc.get("name"));
            }
        }
        reader.close();
    }

    @Test
    public void updateIndex() throws IOException {

        //1。需要变更的内容
        Document doc = new Document();
        doc.add(new StringField("id","111",Field.Store.YES));
        doc.add(new TextField("name","测试修改",Field.Store.YES));
        //2。创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //3。指定索引库位置
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        //4
        //5
        //6
        //7
        //8
        //9
    }

    @Test
    public void deleteAllIndex(){

    }

    @Test
    public void deleteIndex(){

    }
}
