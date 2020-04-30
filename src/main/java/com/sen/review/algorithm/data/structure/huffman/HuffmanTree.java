package com.sen.review.algorithm.data.structure.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Sen
 * @Date: 2020/4/30
 * @Description: 创建赫夫曼树，是一颗最优二叉树
 * 思路：
 * 1.将数组升序排序
 * 2.取出最小和次小的两个元素构建成一个二叉树，根节点权值 = 这两颗子树权值之和
 * 3.这颗新建的二叉树参与排序，然后再取出取出最小和次小的两个元素构建成一个二叉树,再参与排序
 * 4.直到集合中只有一颗元素时赫夫曼树构建完成
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        root.preOrder();
    }


    private static class Node implements Comparable<Node> {

        private final int weight;

        private Node left;

        private Node right;

        public Node(int weight) {
            this.weight = weight;
        }

        /**
         * 前序遍历
         */
        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    /**
     * 构建赫夫曼树
     * @param arr 普通数组
     * @return 根节点
     */
    private static Node createHuffmanTree(int[] arr) {
        // 把数据转成节点集合
        List<Node> nodes = new ArrayList<>();
        for (int weight : arr) {
            nodes.add(new Node(weight));
        }
        // 当集合大小超过1时构建按
        while (nodes.size() > 1) {
            // 对集合进行升序排序
            Collections.sort(nodes);
            // 取出最小和次小的两个元素
            Node left = nodes.remove(0);
            Node right = nodes.remove(0);
            // 以左右节点权值之和作为父节点权值
            Node parent = new Node(left.weight + right.weight);
            // 构建新二叉树
            parent.left = left;
            parent.right = right;
            // 把新构建的二叉树根节点加入结合
            nodes.add(parent);
        }
        // 返回赫夫曼树根节点
        return nodes.get(0);
    }
}
