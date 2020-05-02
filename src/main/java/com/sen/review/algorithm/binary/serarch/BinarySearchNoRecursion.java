package com.sen.review.algorithm.binary.serarch;

/**
 * @Author: Sen
 * @Date: 2020/5/2 15:08
 * @Description: 二分查找（非递归实现）
 */
public class BinarySearchNoRecursion {

    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 10, 11, 67, 100};
        System.out.println(binarySearchNoRecursion(arr, -2));
    }

    /**
     * 二分查找
     *
     * @param arr    查找数组
     * @param target 目标值
     * @return 存在返回下标，else return -1
     */
    private static int binarySearchNoRecursion(int[] arr, int target) {
        // 开始查找位置
        int left = 0;
        // 结束查找位置
        int right = arr.length - 1;
        // 分割数组中间指针
        int mid;
        int midValue;
        // 当前开始位置不大于结束位置循环查找
        while (left <= right) {
            mid = (left + right) / 2;
            midValue = arr[mid];
            if (midValue == target) {
                // 找到返回下标
                return mid;
            }
            if (target < midValue) {
                // 向左查找
                right = mid - 1;
            }
            if (target > midValue) {
                // 向右查找
                left = mid + 1;
            }
        }
        // 没找到
        return -1;
    }
}
