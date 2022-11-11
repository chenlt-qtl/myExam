package com.lucence.note;

import com.lucence.Utils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexSearchTest {

    @Test
    public void searchIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //2。创建查询对象
        QueryParser queryParser = new QueryParser("name",analyzer);
        //3。设置搜索关键词
        Query query = queryParser.parse("工作清单");

        //5。创建输入流
        IndexReader reader = Utils.getIndexReader(Utils.noteDir);
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

        IndexReader reader = Utils.getIndexReader(Utils.noteDir);
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

        IndexReader reader = Utils.getIndexReader(Utils.noteDir);
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

}
