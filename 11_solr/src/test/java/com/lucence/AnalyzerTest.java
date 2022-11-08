package com.lucence;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.UnicodeWhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.LimitTokenCountAnalyzer;
import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.analysis.query.QueryAutoStopWordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.collation.CollationKeyAnalyzer;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

public class AnalyzerTest {

    public static final String TEXT_CN_1 = "中华人民共和国";
    public static final String TEXT_EN_1 = "The spring Framework provides a comprehensive programming and configuration model.";
    public static final String TEXT_EN_2 = "I'm thanks so much for your great work. It looks good. Happy a one apple likely";

    /**
     * 中文可以  英文不行
     * @throws IOException
     */
    @Test
    public void testIk() throws IOException {
        testAnalyzer(new IKAnalyzer(), TEXT_CN_1);
        testAnalyzer(new IKAnalyzer(), TEXT_EN_2);
    }

    /**
     * 不会去掉s stop,不转词性，会转小写
     * @throws IOException
     */
    @Test
    public void testStandard() throws IOException {
        testAnalyzer(new StandardAnalyzer(), TEXT_EN_1);
        testAnalyzer(new StandardAnalyzer(), TEXT_EN_2);

    }

    /**
     * 会去掉stop 转小写 去掉s 转词性，但有的词会出现错误 如 happy->happi apples->appl(使用这个配合内部转词)
     * @throws IOException
     */
    @Test
    public void testEnglist() throws IOException {
        testAnalyzer(new EnglishAnalyzer(), TEXT_EN_2);
    }

    /**
     * 原样输出（排除）
     * @throws IOException
     */
    @Test
    public void testKeyword() throws IOException {
        testAnalyzer(new KeywordAnalyzer(), TEXT_EN_2);
    }

    /**
     * 原样，只去掉空格，句号还在（排除）
     * @throws IOException
     */
    @Test
    public void testWhiteSpace() throws IOException {
        testAnalyzer(new WhitespaceAnalyzer(), TEXT_EN_2);
    }

    /**
     * 不会去掉s stop,不转词性，会转小写，符号会全拆掉
     * @throws IOException
     */
    @Test
    public void testSimple() throws IOException {
        testAnalyzer(new SimpleAnalyzer(), TEXT_EN_2);
    }

    /**
     * 句号不会去掉（排除）
     * @throws IOException
     */
    @Test
    public void testUnicodeWhitespace() throws IOException {
        testAnalyzer(new UnicodeWhitespaceAnalyzer(), TEXT_EN_2);
    }

    private void testAnalyzer(Analyzer analyzer, String text) throws IOException {
        //2.调用Analyzer对象的tokenStream方法获取TokenStream对象，此对象包含了所有的分词结果
        TokenStream tokenStream = analyzer.tokenStream("", text);
        //3.给tokenStream对象设置一个指针，指针在哪当前就在哪一个分词上
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //4.调用tokenStream对象的reset方法，重置指针，不调用会报错
        tokenStream.reset();
        //5.利用while循环，拿到分词列表的结果 incrementToken方法返回值如果为false代表读取完毕 true代表没有读取完毕
        System.out.println("==============" + text + "===============");
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }
        System.out.println("=============================");
        //6.关闭
        tokenStream.close();
    }
}
