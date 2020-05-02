package com.sen.review.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 选择排序，时间复杂度 O(n^2)
 * 思想：
 * 1.数组中选出最小值，并记录其下标 index
 * 2.将arr[0] 和 arr[index]交换位置
 * 3.从剩下的 arr.length()-1 个元素中在选择查找最小的元素，并记录下标index
 * 4.将 arr[1] 和 arr[index] 交换位置
 * ...
 * 以此类推，一共需要查找 arr.length()-1 轮，最后一个元素必然是最大值
 */
public class SelectSort {

    public static void main(String[] args) {
        // int[] arr = {101, 34, 119, 1};
        int[] arr = {101, 34, 119, 1, -20, 80, 0, 1};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // 假定第i轮的最开始元素就是最小值
            int index = i;
            int min = arr[index];
            // 从第 i+1 个元素开始查找
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    // 记录下标
                    index = j;
                    // 记录最小
                    min = arr[index];
                }
            }
            // 如果假定不成立，交换元素
            if (index != i) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }
    }
}
