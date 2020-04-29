package com.sen.review.algorithm.data.structure.tree;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 顺序存储二叉树，用数组表示二叉树，实现前、中、后序遍历
 * 核心：
 * 1.数组中下标index的左子节点下标 = 2 * index + 1
 * 2.数组中下标index的右子节点下标 = 2 * index + 2
 * 3.数组中下标index的父节点下标 = (index - 1) / 2，其中n >= 1
 */
public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree binaryTree = new ArrayBinaryTree(arr);
        // 1,2,4,5,3,6,7
        // binaryTree.preOrder(0);
        // 4,2,5,1,6,3,7
        // binaryTree.infixOrder(0);
        // 4,5,2,6,7,3,1
        binaryTree.postOrder(0);
    }

    private static class ArrayBinaryTree {
        /**
         * 存储二叉树数组
         */
        private final int[] arr;

        public ArrayBinaryTree(int[] arr) {
            this.arr = arr;
        }

        /**
         * 前序遍历
         */
        public void preOrder(int index) {
            // 判断数组是否有效
            if (arr == null || arr.length == 0) {
                throw new IllegalArgumentException("Current array is empty!");
            }
            // 输出当前节点
            System.out.print(arr[index] + ",");
            // 判断当前节点是否存在左子节点
            if (2 * index + 1 < arr.length) {
                // 向左递归
                preOrder(2 * index + 1);
            }
            // 判断当节点是否存在右子节点
            if (2 * index + 2 < arr.length) {
                preOrder(2 * index + 2);
            }
        }

        /**
         * 中序遍历
         * @param index 跟节点下标
         */
        public void infixOrder(int index) {
            // 判断数组是否有效
            if (arr == null || arr.length == 0) {
                throw new IllegalArgumentException("Current array is empty!");
            }
            // 判断当前节点是否存在左子节点
            if (2 * index + 1 < arr.length) {
                // 向左递归
                infixOrder(2 * index + 1);
            }
            // 打印打印当前节点
            System.out.print(arr[index]+",");
            // 判断当前节点是否存在右子节点
            if (2 * index + 2 < arr.length) {
                // 向右递归
                infixOrder(2 * index + 2);
            }
        }

        /**
         * 后续遍历
         * @param index
         */
        public  void postOrder(int index) {
            // 判断数组是否有效
            if (arr == null || arr.length == 0) {
                throw new IllegalArgumentException("Current array is empty!");
            }
            // 判断当前节点左子节点是否存在
            if (2 * index + 1 < arr.length) {
                // 向左递归
                postOrder(2 * index + 1);
            }
            // 判断当前节点右子节点是否存在
            if (2 * index + 2 < arr.length) {
                // 向右递归
                postOrder(2 * index + 2);
            }
            // 输出当前节点
            System.out.print(arr[index] + ",");
        }
    }
}
