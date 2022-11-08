package com.lucence.service;

import com.lucence.Utils;
import com.lucence.bean.Sentence;
import com.lucence.vo.ResultModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    public static final int PAGE_SIZE = 20;

    public ResultModel query(String queryString, int page, String idxStr) throws IOException, ParseException {

        int end = page * PAGE_SIZE;

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        QueryParser queryParser = new QueryParser("content", new EnglishAnalyzer());
        Query query1 = null;
        if (StringUtils.isNotBlank(queryString)) {
            query1 = queryParser.parse(queryString);
        } else {
            query1 = queryParser.parse("*:*");
        }
        builder.add(query1, BooleanClause.Occur.MUST);

        if (StringUtils.isNotBlank(idxStr)) {
            String[] idxArray = idxStr.split("-");
            Query query2 = IntPoint.newRangeQuery("idx", Integer.parseInt(idxArray[0]), Integer.parseInt(idxArray[1]));
            builder.add(query2, BooleanClause.Occur.MUST);
        }

        ResultModel resultModel = new ResultModel();

        IndexReader reader = Utils.getIndexReader(Utils.sentenceDir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //7.搜索并返回结果
        TopDocs topDocs = indexSearcher.search(builder.build(), end);

        //9。遍历结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Sentence> list = new ArrayList<>();
        if (scoreDocs != null) {
            for (int i = end - PAGE_SIZE; i < scoreDocs.length; i++) {
                int docID = scoreDocs[i].doc;
                Document doc = indexSearcher.doc(docID);
                Sentence sentence = new Sentence();
                sentence.setId(doc.get("id"));
                sentence.setIdx(Integer.parseInt(doc.get("idx")));
                sentence.setContent(doc.get("content"));
                list.add(sentence);
            }
        }

        resultModel.setSentences(list);
        resultModel.setCurrentPage(page);
        resultModel.setTotalRow(topDocs.totalHits.value);
        long totalPage = resultModel.getTotalRow() % PAGE_SIZE == 0? resultModel.getTotalRow() / PAGE_SIZE : resultModel.getTotalRow() / PAGE_SIZE + 1;
        resultModel.setTotalPage(totalPage);

        return resultModel;

    }
}
