package com.lucence.sentence;

import com.lucence.Utils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IndexSearchTest {

    @Test
    public void searchIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new EnglishAnalyzer();
        //2。创建查询对象
        QueryParser queryParser = new QueryParser("content", analyzer);
        //3。设置搜索关键词
        Query query = queryParser.parse("likes");
        handleQuery(query);

    }

    /**
     * 根据范围查询
     *
     * @throws Exception
     */
    @Test
    public void rangeSearch() throws Exception {

        //根据价格范围查询
        Query query = IntPoint.newRangeQuery("idx", 10, 12);

        //7.搜索并返回结果
        handleQuery(query);
    }

    /**
     * 组合查询，多个查询条件
     *
     * @throws Exception
     */
    @Test
    public void boolSearch() throws Exception {

        //2。创建查询对象
        QueryParser queryParser = new QueryParser("content", new EnglishAnalyzer());

        Query query1 = queryParser.parse("content: do AND mean");
        //根据价格范围查询
        Query query2 = IntPoint.newRangeQuery("idx", 10, 100);

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(query1, BooleanClause.Occur.MUST);
        builder.add(query2, BooleanClause.Occur.MUST);

        //7.搜索并返回结果
        handleQuery(builder.build());
    }

    /**
     * 人为影响相关度
     *
     * @throws Exception
     */
    @Test
    public void searchBootsIndex() throws Exception {
        //1。创建分词器
        Analyzer analyzer = new EnglishAnalyzer();
        //设置从多个域中进行查询
        String[] fields = {"content", "idx"};
        //设置影响相关度排序的参数
        Map<String, Float> boots = new HashMap<>();
        boots.put("idx", 100000000000000000000F);
        //2。创建多个域查询对象
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boots);

        //3。设置搜索关键词
        Query query = queryParser.parse("do");

        //7.处理结果
        handleQuery(query);

    }

    private void handleQuery(Query query) throws IOException {
        Utils.handleQuery(Utils.sentenceDir,query,true);
    }

}
