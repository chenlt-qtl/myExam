package com.betta.d21_huangPu;

import java.util.Arrays;

/**
 * **需求：**
 * <p>
 * 给你一个按照非递减顺序排列的整数数组 `nums`，和一个目标值 `target`。请你找出给定目标值在数组中的开始位置和结束位置。
 * <p>
 * 如果数组中不存在目标值 `target`，返回 `[-1, -1]`。
 * <p>
 * **注意：必须确保程序的时间复杂度是o(log2n)，否则不给分数**
 * <p>
 * **具体功能点的要求如下：**
 * <p>
 * ```
 * 数组 nums = [5,7,7,8,8,10], target = 8
 * 得到结果是：[3,4]
 * ```
 * <p>
 * ```
 * 数组：nums = [5,7,7,8,8,10], target = 6
 * 得到结果是：[-1,-1]
 * ```
 * <p>
 * ```
 * 数组：nums = [], target = 0
 * 得到结果是：[-1,-1]
 * ```
 * <p>
 * 请设计一个方法完成以上需求，并编写测试代码完成上述测试。
 */
public class Question5 {
    public static void main(String[] args) {

        //使用二分法
        int[] arr = {5, 7, 7, 8, 8, 10};

        //[5,7,7,8,8,10], target = 8 得到结果是：[3,4]
        System.out.println(Arrays.toString(getPosition(arr, 8)));

        //[5,7,7,8,8,10], target = 7 得到结果是：[1,2]
        System.out.println(Arrays.toString(getPosition(arr, 7)));

        //[5,7,7,8,8,10], target = 6 得到结果是：[-1,-1]
        System.out.println(Arrays.toString(getPosition(arr, 6)));

        // [], target = 0 得到结果是：[-1,-1]
        System.out.println(Arrays.toString(getPosition(new int[]{}, 0)));
    }

    public static int[] getPosition(int[] arr, int target) {
        int leftIndex = getIndex(arr, target,true);
        int rightIndex = getIndex(arr, target,false);

        return new int[]{leftIndex,rightIndex};
    }

    public static int getIndex(int[] arr, int targer, boolean isLeft) {
        int result = -1;
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (arr[middle] > targer) {
                end = middle - 1;
            } else if (arr[middle] < targer) {
                start = middle + 1;
            } else {
                result = middle;
                if (isLeft) {
                    end = middle - 1;
                } else {
                    start = middle + 1;
                }
            }
        }
        return result;
    }
}
