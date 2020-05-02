package com.sen.review.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 冒泡排序，平均时间复杂度 n^2；最坏时间复杂度 n^2
 */
public class BubbleSort {

    public static void main(String[] args) {
        // int[] arr = {3, -1, 9, 10, -2};
        int[] arr = {3, -1, 9, 10, -2, 0, 15, 13};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序，按照升序排序
     *
     * @param arr 数组
     */
    public static void bubbleSort(int[] arr) {
        // 用于优化冒泡排序标记,true表示经过经过一轮排序后有发生交换，如果经过一轮比较后不发生交换，则表明已经数组有序
        boolean flag = false;
        // 控制比较的轮数，由冒泡排序特性可知，最多需要比较 arr.length()-1 轮
        for (int i = 0; i < arr.length - 1; i++) {
            // 控制需要比较的元素的下标,开始需要比较的轮数arr.length()-1，每一轮减少一个元素
            for (int j = 0; j < arr.length - i - 1; j++) {
                // 前一个比后一个大，交换顺序
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            // 优化
            if (!flag) {
                break;
            } else {
                // 重置标记用于下一轮排序做标记
                flag = false;
            }
        }
    }
}
