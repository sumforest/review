package com.sen.review.algorithm.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 基数排序，时间复杂度 O(Kn)
 * 思想：
 * 1.创建10个桶每个桶用于存放相同位数的元素
 * 2.第一轮把元素个位相同的元素按照顺序放入桶中，然后依次按顺序从桶中取回
 * 3.第二轮把元素百分相同的元素按照顺序放入桶中，然后依次按顺序从桶中取回
 * ... 一直进行到数组中最高位元素的位数轮结束
 * <p>
 * 优点：速度快，稳定
 * 缺点：空间复杂度高，大量排序数据时会造成OutOfMemoryError，不适用于带有负数的排序
 */
public class RadixSort {

    private static final int BIT = 10;

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序
     *
     * @param arr 待排序数组
     */
    private static void radixSort(int[] arr) {
        // 用二维数组表示桶，一行表示一个桶，排序的数据是十进制所以需要十个桶
        // 考虑最坏的情况，每个桶的空间为 arr.length() 个
        int[][] bucket = new int[10][arr.length];
        // 创建一个一维数组，数组的下标对应每个桶，数组的值对应着每个桶的使用情况
        int[] record = new int[10];

        // 寻找数组中最大的值
        int maxValue = Integer.MIN_VALUE;
        for (int value : arr) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        // 最大值的位数确定需要比较的轮数
        int maxBit = String.valueOf(maxValue).length();

        // 进行排序
        for (int i = 0, n = 1; i < maxBit; i++, n *= BIT) {
            // 遍历数组，依次取出个、十、百位...
            for (int value : arr) {
                // 取出当前需要比较的位数 53, 3, 542, 748, 14, 214
                int mod = (value / n) % 10;
                // 根据取出的位数放入对应的桶
                bucket[mod][record[mod]] = value;
                // 桶指针后移
                record[mod]++;
            }
            // 经过一轮排序后按照顺序依次从桶中取回数据
            // 记录原数组当前操作位置指针
            int index = 0;
            for (int j = 0; j < bucket.length; j++) {
                // 桶不为空才进行取回操作
                if (record[j] > 0) {
                    // 遍历每个桶
                    for (int k = 0; k < record[j]; k++) {
                        arr[index] = bucket[j][k];
                        index++;
                    }
                    // 取回后把桶标记清零
                    record[j] = 0;
                }
            }
        }
    }
}
