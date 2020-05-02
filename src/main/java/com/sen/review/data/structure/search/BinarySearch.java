package com.sen.review.data.structure.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 二分查找
 * 前提：数组必须有序
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {3, 29, 75, 99, 200, 239, 239, 239};
        // System.out.println(binarySearchOne(arr, 0, arr.length - 1, -1));
        List<Integer> result = binarySearchMany(arr, 0, arr.length - 1, 239);
        System.out.println(result);
    }

    /**
     * 二分查找（之查找一个结果）
     *
     * @param arr    查找位置
     * @param target 目标值
     * @return -1 没找到；否则返回下标
     */
    private static int binarySearchOne(int[] arr, int left, int right, int target) {
        // 当做指针超过右指针表示该值不存在
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (target > arr[mid]) {
            // 目标值在数组右半部分，向右递归查找
            return binarySearchOne(arr, mid + 1, right, target);
        }
        if (target < arr[mid]) {
            // 目标值在左半部分
            return binarySearchOne(arr, left, mid - 1, target);
        }
        // 找到该值，返回下标
        return mid;
    }

    /**
     * 二分查找（把所有符合条件的都找出来）
     *
     * @param arr    查找位置
     * @param left   起始位置
     * @param right  结束位置
     * @param target 目标值
     * @return 找到返回下表的集合，否则返回空集合
     */
    private static List<Integer> binarySearchMany(int[] arr, int left, int right, int target) {
        // 开始位置大于结束位置结束查找，当前目标值不存在
        if (left > right) {
            return new ArrayList<>();
        }
        int mid = (left + right) / 2;
        if (target > arr[mid]) {
            // 向右递归查找
            return binarySearchMany(arr, mid + 1, right, target);
        }
        if (target < arr[mid]) {
            // 向左递归查找
            return binarySearchMany(arr, left, mid - 1, target);
        }
        // 找到了
        List<Integer> list = new ArrayList<>();
        // 以当前位置的前一个位置为起点开始向左探索，是否还存在目标值
        int index = mid - 1;
        while (index >= 0 && arr[index] == target) {
            list.add(index);
            // 指针左移
            index--;
        }
        // 把当前下标加入集合
        list.add(mid);
        // 以当前位置的后一个位置为起点向右探索，是否还存在目标值
        index = mid+1;
        while (index <= right && arr[index] == target) {
            list.add(index);
            // 指针右移
            index++;
        }
        return list;
    }
}
