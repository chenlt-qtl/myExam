package com.lucence.dao.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

public class Utils {
    public static IndexWriter getIndexWriter(String dir) throws IOException {
        //1。创建分词器
        Analyzer analyzer=new IKAnalyzer();
        //2。创建Directory目录对象，指定索引库位置
        //FSDirectory : file system directory 写到硬盘里
        Directory directory = FSDirectory.open(Paths.get(dir));
        //3。指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4。创建输出流
        return new IndexWriter(directory,config);
    }

    public static IndexReader getIndexReader(String dir) throws IOException {

        Directory directory = FSDirectory.open(Paths.get(dir));

        //4。创建输出流
        return DirectoryReader.open(directory);
    }
}
