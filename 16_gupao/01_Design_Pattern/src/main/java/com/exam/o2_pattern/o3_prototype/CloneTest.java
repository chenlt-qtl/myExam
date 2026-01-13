package com.exam.o2_pattern.o3_prototype;

public class CloneTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        QiTianDaSheng clone = qiTianDaSheng.deepClone();
        System.out.println("深克隆:" + (qiTianDaSheng.jinGuBang == clone.jinGuBang));

        QiTianDaSheng q = new QiTianDaSheng();
        QiTianDaSheng n = q.shallowClone();
        System.out.println("浅克隆:" + (q.jinGuBang == n.jinGuBang));
    }
}
