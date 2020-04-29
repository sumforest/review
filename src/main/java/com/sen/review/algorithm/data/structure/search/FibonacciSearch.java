package com.sen.review.algorithm.data.structure.search;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 斐波那契（黄金分割查找）查找
 * 前提：数组必须有序
 * 思想：
 * 1.用斐波那契数列的值代表数组长度
 * 2.利用斐波那契数列特性：F(K) = F(k-1) + F(k-2)特性将变换得到F(K) -1 = (F(k-1) - 1) + (F(k-2)-1) + 1
 * 第k数值-1为了空出一个位置作为mid作为分界点,将数组分成长度=F(k-1) - 1和长度=(F(k-2)-1) + 1两部分
 * 3.再从左边或右边继续分割斐波那契数列，直到arr[mid] = target此时找到目标值，当开始位置大于结束位置时说明target不存在
 */
public class FibonacciSearch {

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibonacciSearch(arr, 12345));
    }

    /**
     * 构建一个斐波那契数列，其值代表数组长度,默认斐波那契数列为20项
     *
     * @return arr
     */
    private static int[] createFibonacci() {
        int[] fibonacci = new int[20];
        fibonacci[0] = 1;
        fibonacci[1] = 1;
        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci;
    }

    /**
     * 斐波那契查找算法
     *
     * @param arr    查找位置
     * @param target 目标值
     * @return 存在返回下标，否则返回-1
     */
    private static int fibonacciSearch(int[] arr, int target) {
        // 开始指针
        int left = 0;
        // 结束指针
        int right = arr.length - 1;
        // 获取斐波那契数列
        int[] fibonacci = createFibonacci();
        // 记录斐波那契数列指针
        int k = 0;

        // 由于数组长度不一定满足斐波那契数列长度，所以给数组扩容，直到满足arr.length() >= fibonacci[k] - 1
        // fibonacci[k] - 1，-1为了空出一个位置给mid用于分割数组
        while (arr.length > fibonacci[k] - 1) {
            k++;
        }
        // 把原数组的值拷贝到临时数组
        int[] temp = Arrays.copyOf(arr, fibonacci[k] - 1);
        // 临时数组长度比原数组长度大，给临时数组填充最大值
        if (temp.length > arr.length) {
            for (int i = right + 1; i < temp.length; i++) {
                temp[i] = arr[right];
            }
        }

        // 开始位置不大于结束位置才查找
        while (left <= right) {
            int mid = left + fibonacci[k - 1] - 1;
            // 1, 1, 2, 3
            //              |
            //向左查找 1, 8, 10, 89, 1000, 1234
            if (target < temp[mid]) {
                // 向左查找
                right = mid - 1;
                // 由斐波那契数列得知，左部分-1
                k--;
            }
            if (target > temp[mid]) {
                // 向右查找
                left = mid + 1;
                // 由斐波那契数列得知，左部分-1
                k -= 2;
            }
            // 找到
            if (target == temp[mid]) {
                return Math.min(mid, arr.length - 1);
            }
        }
        return -1;
    }
}
