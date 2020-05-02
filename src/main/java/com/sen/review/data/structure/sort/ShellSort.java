package com.sen.review.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/28
 * @Description: 希尔排序，时间复杂度 O(n^s),1 < s < 2
 * 思路：缩小增量
 * 1.把需要排序的数组分成 arr.length / 2 组,每组步长5
 * 2.对每一组都进行插入排序
 * 3.在原来的基础上再分 arr.length / 2 / 2组
 * 4.对每一组进行插入排序
 * 5.直到分成 arr.length 组，每组步长1在进行插入排序，最后数组有序
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        shellSortByMove(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 交换法实现希尔排序
     * 缺点：效率低
     *
     * @param arr 排序数组
     */
    private static void shellSortByExchange(int[] arr) {
        // gap -- 每次分组的步长/数量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 按照步长划分分组,i代表每一组最后元素的下标
            for (int i = gap; i < arr.length; i++) {
                // j -- 每组开始元素的下标,对每一组元素进行插入排序
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        // 前一个比后一个大，交换
                        int temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    /**
     * 希尔排序，移动法实现效率更高
     *
     * @param arr
     */
    private static void shellSortByMove(int[] arr) {
        // 确定分组步长
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // i--每一组最后一个元素下标,遍历每一组
            for (int i = gap; i < arr.length; i++) {
                // 有序序列最后一个元素
                int index = i;
                int value = arr[i];
                // 前一个比后一个大进行移位
                if (arr[index] < arr[index - gap]) {
                    // 移位 3,1,0,9,7 步长2
                    while (index - gap >= 0 && value < arr[index - gap]) {
                        // 将有序序列的最后一个元素往后移动一步长位置，腾出空间给待插入元素
                        // 3,3,0,9,7 步长2
                        arr[index] = arr[index - gap];
                        // index往前移动一个步长
                        index -= gap;
                    }
                    // 交换
                    arr[index] = value;
                }
            }
        }
    }
}

