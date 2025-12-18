package com.exam.o2_pattern.o3_prototype;

import java.io.*;
import java.util.Date;

/**
 * 齐天大圣
 */
public class QiTianDaSheng extends Monkey implements Cloneable, Serializable {

    public JinGuBang jinGuBang;

    public QiTianDaSheng() {
        jinGuBang = new JinGuBang();
    }

    /**
     * 深克隆
     * @return
     * @throws CloneNotSupportedException
     */
    public QiTianDaSheng deepClone() throws CloneNotSupportedException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            QiTianDaSheng copy = (QiTianDaSheng) ois.readObject();
            copy.birthday = new Date();
            ois.close();
            bis.close();
            oos.close();
            bos.close();
            return copy;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 浅克隆
     * @return
     * @throws CloneNotSupportedException
     */
    public QiTianDaSheng shallowClone() throws CloneNotSupportedException {
        QiTianDaSheng copy = (QiTianDaSheng) this.clone();
        return copy;
    }
}
