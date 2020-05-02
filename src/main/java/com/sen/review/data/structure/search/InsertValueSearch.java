package com.sen.review.data.structure.search;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 插值查找
 * 核心：（公式）
 * int mid = left + (right-left)*(target-arr[left])/(arr[right]-arr[left])
 * 优点：适合数据量比较大且分布比较均匀，但是在数据分布不均匀时其效率不一定比二分查找高
 */
public class InsertValueSearch {

    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        System.out.println(insertValueSearch(arr, 0, arr.length - 1, 99));
    }

    /**
     * 插值查找
     * @param arr 查找位置
     * @param left 开始位置
     * @param right 结束位置
     * @param target 目标值
     * @return 存在返回下标，否则返回-1
     */
    private static int insertValueSearch(int[] arr, int left, int right, int target) {
        // 开始位置大于结束位置时结束
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) * (target - arr[left]) / (arr[right] - arr[left]);
        if (target > arr[mid]) {
            // 目标值比中间值大向右查找
            return insertValueSearch(arr, mid + 1, right, target);
        }
        if (target < arr[mid]) {
            // 目标值比中间值小，向左查找
            return insertValueSearch(arr, left, mid - 1, target);
        }
        // 找到了
        return mid;
    }
}
