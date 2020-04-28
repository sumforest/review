package com.sen.review.algorithm.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 快速排序，平均时间复杂度 O(n log n)，最坏时间复杂度 O(n^2)
 * 思想：
 * 1.以数组中间元素作为基值，经过一轮排序后数组以基值为界被分成两部分，左边的一部分的所有值都比右边部分小
 *   注意：右边部分的数不一定比基值大，有可能存在与基值相等的元素
 * 2.左边部分进行递归
 * 3.右边部分进行递归
 * 4.递归结束后数组有序
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {-9, 70, 0, 23, -576, 0};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序
     * @param arr 需要被排数组
     * @param start 开始位置
     * @param end 结束位置
     */
    private static void quickSort(int[] arr, int start, int end) {
        // 记录数组左边的指针
        int left = start;
        // 记录数组右边指针
        int right = end;
        // 基值
        int pivot = arr[(left + right) / 2];
        // 当左右指针不重合进行排序
        while (left < right) {
            // 在数组左半部分寻找比基值大的数，退出循环表示当前值满足
            while (arr[left] < pivot) {
                // 当前值比基值小，指针左移
                left++;
            }
            // 在数组右半部分寻找比基值小的数，退出循环表示当前值满足
            while (arr[right] > pivot) {
                // 当前值比基值大
                right--;
            }
            // 当移动后左右指针重合结束
            if (left >= right) {
                break;
            }
            // 左右部分符合条件的元素互相交换
            int temp = arr[left];
            arr[left] = arr[right] ;
            arr[right] = temp;
            // 判断交换数据后left指针的值是否合基值相等 -9, 70, 0, 23, -576, 0
            //           |               |
            // 交换后 -9, 0, 0, 23, -576, 70
            //                 |      |
            // 交换后 -9, -576, 0, 23, 0, 70，不移动会造成死循环
            if (arr[left] == pivot) {
                // 右指针左移
                right--;
            }
            // 判断交换数据后right指针的值是否合基值相等
            if (arr[right] == pivot) {
                // 左指针右移
                left++;
            }
        }
        // 排序后left、right可能会相等响应的移动它们的指针
        if (left == right) {
            left++;
            right--;
        }
        // 开始位置小于结束位置
        if (start < right) {
            quickSort(arr, start, right);
        }
        if (left < end) {
            quickSort(arr, left, end);
        }
    }
}
