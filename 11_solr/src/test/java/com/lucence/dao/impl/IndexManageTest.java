package com.lucence.dao.impl;

import com.lucence.bean.Note;
import com.lucence.bean.Sentence;
import com.lucence.dao.SentenceDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IndexManageTest {

    String noteDir = "E:\\luceneIndex\\note";
    String sentenceDir = "E:\\luceneIndex\\sentence";

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
        IndexWriter writer = Utils.getIndexWriter(noteDir);
        //8。写入索引库
        for (Document document : docList) {
            writer.addDocument(document);
        }
        //9。释放资源
        writer.close();
    }

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
        IndexWriter writer = Utils.getIndexWriter(sentenceDir);
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
        Query query = queryParser.parse("工作清单");

        //5。创建输入流
        IndexReader reader = Utils.getIndexReader(noteDir);
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
    public void searchManyIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //2。创建查询对象
        QueryParser queryParser = new QueryParser("name",analyzer);
        //3。设置搜索关键词
        Query query = queryParser.parse("name:工作清单");

        IndexReader reader = Utils.getIndexReader(noteDir);
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
    public void searchBootsIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new IKAnalyzer();

        //设置从多个域中进行查询
        //2。创建查询对象
        QueryParser queryParser = new QueryParser("name",analyzer);
        //3。设置搜索关键词
        Query query = queryParser.parse("name:工作清单");

        IndexReader reader = Utils.getIndexReader(noteDir);
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
        doc.add(new StringField("id","9a6cf623fb50ab7a9fb8b52e2354fc2d",Field.Store.YES));
        doc.add(new TextField("name","工作清单111 ",Field.Store.YES));

        //5.创建输入流对象
        IndexWriter indexWriter = Utils.getIndexWriter(noteDir);
        //6。执行修改
        indexWriter.updateDocument(new Term("id","9a6cf623fb50ab7a9fb8b52e2354fc2d"),doc);
        //7。释放资源
        indexWriter.close();
    }

    @Test
    public void deleteAllIndex() throws IOException {
        //1。指定索引库位置
        Directory directory = FSDirectory.open(Paths.get(noteDir));
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
        Directory directory = FSDirectory.open(Paths.get(noteDir));
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
