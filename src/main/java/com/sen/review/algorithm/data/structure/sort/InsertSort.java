package com.sen.review.algorithm.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 插入排序，O(n^2)
 * 思路：
 * 1.把数组 arr[0] 值看成一个有序序列
 * 2.选择 arr[1] 插入有序序列
 * 3.选择合适的插入位置
 * ...
 * 以此类推
 * 一共需要插入 arr.length()-1 次
 */
public class InsertSort {

    public static void main(String[] args) {
        // int[] arr = {101, 34, 119, 1};
        int[] arr = {101, 34, 119, 1, 0, -2};
        insetSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insetSort(int[] arr) {
        // 把arr[0] 看成时有序序列，所以i从1开始
        for (int i = 1; i < arr.length; i++) {
            // 待插入数值
            int value = arr[i];
            // 有序序列的最后一个元素所在的下标
            int index = i - 1;

            // 寻找合适的插入位置
            while (index >= 0 && value < arr[index]) {
                // 将下标为index元素往后移动一个位置，腾出插入元素的位置
                arr[index + 1] = arr[index];
                // index前移
                index--;
            }
            // index为i时无需插入, 在寻找位置时多减了一次，所以 index+1
            if (index + 1 != i) {
                // 插入数值
                arr[index + 1] = value;
            }

        }
    }
}
