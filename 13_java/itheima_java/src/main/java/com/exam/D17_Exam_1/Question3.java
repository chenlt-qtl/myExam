package com.exam.D17_Exam_1;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * **需求**：
 * <p>
 * 某护士小花，作息规律为上二天班，休息一天，经常不确定休息日是否是周末（**注：首次休息日是2022年2月3日**）。
 * <p>
 * **具体功能点的要求如下**
 * <p>
 * 1、请你开发一个程序，当小花输入年以及月后，立即显示出该月份的休息日详情。
 * <p>
 * 示范（注意：示范信息重点在于参考格式，结果不一定是准确的，请自行确保计算结果正确性）：
 * <p>
 * **请小花输入查询的月份（月份必须是2022年2月之后的月份）： 2023-5
 * <p>
 * **2023-5-1[休息]   2023-5-2   2023-5-3  2023-5-4[休息] ...**
 * <p>
 * 2、显示出该月份哪些休息日是周六或周日（请依次列出具体的日期和其星期信息）。
 * <p>
 * 3、小花给自己设置了一个高考倒计时。高考的开始时间为：2023年06月07日 上午9：00 。
 * **请利用给的素材代码（在Timer文件夹下）**，补全代码，产生一个如下的倒计时效果，倒计时格式如下图所示：
 * ![image-20230508224310600](image-20230508224310600.png)
 */
public class Question3 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //休息日规则，2022年2月3日后隔两天休息一次
        //当小花输入年以及月后，立即显示出该月份的休息日详情。
        //显示出该月份哪些休息日是周六或周日（请依次列出具体的日期和其星期信息）。
        printSchedule();
    }

    public static void printSchedule() {
        Set<LocalDate> weekend = new TreeSet<>();
        LocalDate firstDate = LocalDate.of(2022, 2, 3);
        Pattern pattern = Pattern.compile("^(\\d{4})\\-(\\d{1,2})$");
        while (true) {
            System.out.println("请输入要查询的年月(格式：2023-5),必须大于2022年2月:");
            String str = "2023-5";
            //String str = scanner.nextLine();
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                String yearStr = matcher.group(1);
                String monthStr = matcher.group(2);
                LocalDate startDate = LocalDate.of(Integer.parseInt(yearStr), Integer.parseInt(monthStr), 1);
                if(startDate.isBefore(firstDate)){
                    System.out.println("必须大于2022年2月");
                }else {
                    LocalDate endDate = startDate.plusMonths(1);
                    while (startDate.isBefore(endDate)) {
                            System.out.print(startDate);
                            if ((startDate.toEpochDay() -firstDate.toEpochDay()) % 3 == 0) {
                                System.out.print("[休息]");
                                //如果是周末，加入weekend中
                                if(startDate.getDayOfWeek().getValue()==6||startDate.getDayOfWeek().getValue()==7) {
                                    weekend.add(startDate);
                                }
                            }
                            System.out.print("  ");
                            startDate = startDate.plusDays(1);
                    }
                    System.out.println("\n休息日是周六或周日:"+weekend);
                    return;
                }
            } else {
                System.out.println("输入的格式不正确，请重新输入");
            }
        }
    }
}
