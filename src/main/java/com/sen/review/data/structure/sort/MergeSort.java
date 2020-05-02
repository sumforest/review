package com.sen.review.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 归并排序，时间复杂度 O(n log n)
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 归并排序
     *
     * @param arr   原始数组
     * @param start 开始位置
     * @param end   结束位置
     * @param temp  临时数组
     */
    private static void mergeSort(int[] arr, int start, int end, int[] temp) {
        // 开始指针和结束指针重合结束
        if (start < end) {
            int mid = (start + end) / 2;
            // 向左递归
            mergeSort(arr, start, mid, temp);
            // 向右递归
            mergeSort(arr, mid + 1, end, temp);
            // 合并有序子序列
            merge(arr, start, mid, end, temp);
        }
    }

    /**
     * 合并有序数组
     *
     * @param arr   原始数组
     * @param start 开始位置
     * @param mid   中间位置
     * @param end   结束位置
     * @param temp  临时数组
     */
    private static void merge(int[] arr, int start, int mid, int end, int[] temp) {
        // 左半部分数组开始位置指针
        int left = start;
        // 右半部分数组开始位置指针
        int right = mid + 1;
        // 记录操作临时数组指针
        int pointer = 0;
        // ①把左右两部分数组按照顺序吸入临时数组
        while (left <= mid && right <= end) {
            // 左半部分当前元素比右半部分当前元素小
            if (arr[left] <= arr[right]) {
                // 写入临时数组
                temp[pointer] = arr[left];
                // 移动指针
                left++;
                pointer++;
            }
            // 右半部分当前元素比左半部分当前元素小
            if (arr[right] < arr[left]) {
                // 吸入临时数组
                temp[pointer] = arr[right];
                right++;
                pointer++;
            }
        }

        // ②左半部分或者右半部分剩余的元素写入临时数组
        while (left <= mid) {
            temp[pointer] = arr[left];
            left++;
            pointer++;
        }
        while (right <= end) {
            temp[pointer] = arr[right];
            right++;
            pointer++;
        }

        // ③把临时数组复制到原数组
        int arrPo = start;
        pointer = 0;
        while (arrPo <= end) {
            arr[arrPo] = temp[pointer];
            arrPo++;
            pointer++;
        }
    }
}
