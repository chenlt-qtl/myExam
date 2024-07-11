package com.betta.cases;

/**
 * 用户购买机票时，机票原价会按照淡季、旺季，头等舱还是经济舱的情况进行相应的优惠，优惠方案如下:
 * 5-10月为旺季，头等舱9折，经济舱8.5折;
 * 11月到来年4月为淡季，头等舱7折，经济舱6.5折
 * 请开发程序计算出用户当前机票的优惠价。
 */
public class PlaneTicket {
    public static void main(String[] args) {
        System.out.println(getPrice(1000, 1, 8));
    }

    /**
     * @param originPrice 原始价格
     * @param type        1 经济舱 ，2头等舱
     * @param month       购买的月份
     * @return
     */
    public static double getPrice(double originPrice, int type, int month) {
        double discount = 1;
        if (month > 12 || month < 1) {
            System.out.println("请输入正确的月份");
            return originPrice;
        }
        if (type != 1 && type != 2) {
            System.out.println("请输入正确的类型 1:经济舱 ，2:头等舱");
            return originPrice;
        }
        if (5 <= month && month <= 10) {
            //旺季
            switch (type) {
                case 1:
                    discount = 0.85;
                    break;
                case 2:
                    discount = 0.9;
                    break;
            }
        } else {
            //淡季
            switch (type) {
                case 1:
                    discount = 0.65;
                    break;
                case 2:
                    discount = 0.7;
                    break;
            }
        }
        return originPrice * discount;
    }
}
