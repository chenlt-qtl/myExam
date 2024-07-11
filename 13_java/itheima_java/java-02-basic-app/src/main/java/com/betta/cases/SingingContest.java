package com.betta.cases;

import java.util.Objects;

/**
 * 唱歌比赛中，可能有多名评委要给选手打分，分数是[0-100]之间的整数。
 * 选手最后得分为:去掉最高分、最低分后剩余分数的平均分，请编写程序能够录入多名评委的分数，并算出选手的最终得分
 */
public class SingingContest {
    public static void main(String[] args) {

        System.out.println(getScore(new int[]{10, 12, 89, 13, 14}));
    }

    public static double getScore(int[] scores) {
        if (Objects.isNull(scores) || scores.length < 2) {
            return 0;
        }
        int max = 0;
        int min = 100;
        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            max = max > scores[i] ? max : scores[i];
            min = min < scores[i] ? min : scores[i];
            total += scores[i];
        }
        return (total - max - min) / (scores.length - 2) * 1.0;
    }
}
