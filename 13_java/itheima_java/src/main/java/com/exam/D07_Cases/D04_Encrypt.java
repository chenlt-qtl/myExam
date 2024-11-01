package com.exam.D07_Cases;

/**
 * 某系统的数字密码是一个四位数，如1983，为了安全，需要加密后再传输，加密规则是:
 * 对密码中的每位数都加5,再对10求余，最后将所有数字顺序反转，得到一串加密后的新数，
 * 请设计出满足本需求的加密程序!
 */
public class D04_Encrypt {
    public static void main(String[] args) {

        System.out.println(encrypt(1983));
    }

    public static String encrypt(int code) {
        int[] result = split(code);
        for (int i = 0; i < result.length; i++) {
            result[i] = (result[i] + 5) % 10;
        }

        reverse(result);
        String str = "";
        for (int i = 0; i < result.length; i++) {
            str+= result[i];
        }
        return str;
    }

    private static void reverse(int[] result) {
        for (int i = 0, j = result.length - 1; i < j; i++, j--) {
            int temp = result[i];
            result[i] = result[j];
            result[j] = temp;
        }
    }

    /**
     * 把数字转成int数组
     *
     * @param code
     * @return
     */
    private static int[] split(int code) {
        int[] num = new int[4];
        // 1983
        num[0] = code / 1000;
        num[1] = (code / 100) % 10;//1983/100=19.83
        num[2] = (code / 10) % 10;//1983/10=198.3
        num[3] = code % 10;
        return num;
    }

}
