package com.lucence;

import com.lucence.vo.ResultModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Utils {

    public static final String noteDir = "E:\\luceneIndex\\note";
    public static final String sentenceDir = "E:\\luceneIndex\\sentence";


    public static IndexWriter getIndexWriter(String dir, Analyzer analyzer) throws IOException {
        //2。创建Directory目录对象，指定索引库位置
        //FSDirectory : file system directory 写到硬盘里
        Directory directory = FSDirectory.open(Paths.get(dir));
        //3。指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4。创建输出流
        return new IndexWriter(directory, config);
    }

    public static IndexReader getIndexReader(String dir) throws IOException {

        Directory directory = FSDirectory.open(Paths.get(dir));

        //4。创建输出流
        return DirectoryReader.open(directory);
    }


    public static TopDocs handleQuery(String dir, Query query, boolean print) throws IOException {
        //5。创建输入流
        IndexReader reader = Utils.getIndexReader(dir);
        //6。创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //7.搜索并返回结果
        TopDocs topDocs = indexSearcher.search(query, 10);
        if (print) {
            //8。获取结果集
            System.out.println("=========总记录数========" + topDocs.totalHits);
            //9。遍历结果集
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            if (scoreDocs != null) {
                for (ScoreDoc scoreDoc : scoreDocs) {
                    int docID = scoreDoc.doc;
                    Document doc = indexSearcher.doc(docID);
                    System.out.println("==========" + doc.get("id"));
                    System.out.println("==========" + doc.get("content"));
                    System.out.println("==========" + doc.get("idx"));
                    System.out.println("==========");
                }
            }
        }
        reader.close();
        return topDocs;
    }
}
