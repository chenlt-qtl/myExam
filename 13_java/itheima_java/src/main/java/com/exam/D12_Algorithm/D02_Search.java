package com.exam.D12_Algorithm;

import java.util.Arrays;

public class D02_Search {
    public static void main(String[] args) {
        //二分查找
        System.out.println("-------二分查找--------");
        int[] arr = {7, 23, 79, 81, 103, 127, 131, 147};
        int i = binarySearch(arr, 79);
        if (i != -1) {
            System.out.println("找到了，位置在第 " + i);
        } else {
            System.out.println("没有找到数据");
        }

        System.out.println(Arrays.binarySearch(arr,131));
    }

    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (right + left) / 2;
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
