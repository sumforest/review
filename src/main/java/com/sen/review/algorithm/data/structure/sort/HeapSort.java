package com.sen.review.algorithm.data.structure.sort;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/4/30
 * @Description: 堆排序，最坏、最好、平均时间复杂度： O(n log n),是一种不稳定的排序
 * 思想：
 * 1.将数组构建成一个大顶堆，利用大顶堆的特性：堆顶根节点的值 >= 所有子节点的的值（注意：左子节点的值不一定 < 右子节点）
 * 2.然后把对应元素与数组的最后一个元素交换位置，然后在arr.length - 1的长度数组中再构建大顶堆
 * 3.一直重复直到数组有序
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9, -234, 345, 34, 765, 2341, 4, 7};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 调整大顶堆
     *
     * @param arr    需要调整为大顶堆的数组
     * @param index  堆顶根节点下标
     * @param length 调整的长度
     */
    private static void adjustHeap(int[] arr, int index, int length) {
        // 记录堆顶值
        int top = arr[index];
        // 以index作为堆顶调整成大顶堆，调整后检查其子节点是否符合大顶堆
        for (int i = 2 * index + 1; i < length; i = 2 * i + 1) {
            /*
             * 当前节点的左子节点要小于右子节点 4, 6, 8, 5, 9
             * 第一轮：    4        第二轮：       4
             *          /  \                   / \
             *         6    8                 9   8
             *        / \                    / \
             *        5  9                  5   6
             * */
            // 比较左、右子节点的值，将i指向最小节点
            if (i + 1 < length && arr[i] < arr[i + 1]) {
                // i指向比左子节点大的右子节点
                i++;
            }
            // 判断子节点的值是否比堆顶节点大
            if (top < arr[i]) {
                // 子节点的值替换堆顶节点
                arr[index] = arr[i];
                // 堆顶指向i
                index = i;
            }
            // 由于从最后一个左节点开始调整的当前不需要调整证明符合大顶堆
            else{
                break;
            }
        }
        // 把调整前堆顶的值赋给调整后的节点
        arr[index] = top;
    }

    /**
     * 堆排序
     * @param arr 待排序数组
     */
    private static void heapSort(int[] arr) {
        /*
        * 先把数组调整成一个大顶堆
        * 从最后一个左子节点开始调整堆，最后一个左子节点的下标 = arr.length / 2 - 1
        * */
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        // 把堆顶元素和最后一个元素交换，然后以根节点为堆顶再构建成大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            // 交换
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // 再原来基础上，长度-1调整成大顶堆
            adjustHeap(arr, 0, i);
        }
    }
}
