package com.betta.cases;

import java.util.Random;

/**
 * 开发一个程序，可以生成指定位数的验证码，每位可以是数字、大小写字母。
 * a-z 97-122
 * A-Z 65-90
 */
public class VerificationCode {
    public static void main(String[] args) {
        System.out.println(createCode(6));
    }

    public static String createCode(int length) {
        if (length < 0 || length > 10) {
            System.out.println("长度应该在1-10之间");
            return "";
        }
        String code = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int type = random.nextInt(3);//随机type, 0:数字 1:小写字母, 2:大写字母
            switch (type) {
                case 0:
                    code += random.nextInt(10);
                    break;
                case 1://小写字母 97-122
                    code += (char) (random.nextInt(26) + 97);
                    break;
                case 2://大写字母 65-90
                    code += (char) (random.nextInt(26) + 65);
                    break;
            }
        }
        return code;
    }
}
