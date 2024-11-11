package com.seed;

import com.alibaba.fastjson.JSON;
import com.seed.dao.ArticleDao;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;


@SpringBootTest
//@Transactional
public class AppTest {


    @Autowired
    private ArticleDao articleDao;
//
//    @Test
//    public void test(){
//        Article article = articleDao.selectById(2);
//        System.out.println(article);
//    }


    private RestHighLevelClient client;

    @BeforeEach
    void setUp() {
        RestClientBuilder builder = RestClient.builder(HttpHost.create("http://localhost:9200"));
        client = new RestHighLevelClient(builder);
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }

    @Test
    void testCreate() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("article");
        //设置请求参数

        String json = "{\n" +
                "    \"mappings\": {\n" +
                "        \"properties\": {\n" +
                "            \"id\": {\n" +
                "                \"type\": \"keyword\"\n" +
                "            },\n" +
                "            \"title\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\",\n" +
                "                \"copy_to\": \"all\"\n" +
                "            },\n" +
                "            \"comment\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\",\n" +
                "                \"copy_to\": \"all\"\n" +
                "            },\n" +
                "            \"all\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testCreateDoc() throws IOException {
        Article article = articleDao.selectById(2);
        IndexRequest request = new IndexRequest("article").id(article.getId().toString());
        String json = JSON.toJSONString(article);
        request.source(json, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            String message = e.getMessage();
            if (!message.contains("created") && !message.contains("200")) {
                throw new RuntimeException(e);
            }
        }

    }

    @Test
    void testCreateDocAll() throws IOException {
        List<Article> articles = articleDao.selectList(null);
        BulkRequest bulkRequest = new BulkRequest();
        articles.forEach(article -> {
            IndexRequest request = new IndexRequest("article").id(article.getId().toString());
            String json = JSON.toJSONString(article);
            request.source(json, XContentType.JSON);
            bulkRequest.add(request);
        });

        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            String message = e.getMessage();
            if (!message.contains("created") && !message.contains("200")) {
                throw new RuntimeException(e);
            }
        }

    }

    @Test//按ID查
    void testGet() throws IOException {
        GetRequest getRequest = new GetRequest("article","2");
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        String sourceAsString = documentFields.getSourceAsString();
        System.out.println(sourceAsString);

    }

    @Test//按条件查
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("article");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("all","1"));
        searchRequest.source(builder);

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        hits.forEach(hit->{
            String sourceAsString = hit.getSourceAsString();
            System.out.println(JSON.parseObject(sourceAsString, Article.class));

        });

    }

}
