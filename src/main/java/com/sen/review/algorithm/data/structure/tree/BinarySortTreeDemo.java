package com.sen.review.algorithm.data.structure.tree;

/**
 * @Author: Sen
 * @Date: 2020/5/1 13:59
 * @Description: BST二叉排序树/二叉搜索树
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        /*
         *            7
         *          /   \
         *         3    10
         *        / \   / \
         *       1   5 9  12
         *        \
         *         2
         * */
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int value : arr) {
            binarySortTree.add(new Node(value));
        }
        binarySortTree.print();
        System.out.println("----------------------------");
        // binarySortTree.deleteNode(5);
        // binarySortTree.print();
        // System.out.println("----------------------------");
        // binarySortTree.deleteNode(3);
        // binarySortTree.print();
        // System.out.println("----------------------------");
        binarySortTree.deleteNode(2);
        binarySortTree.print();
        // System.out.println("----------------------------");
        // binarySortTree.deleteNode(7);
        // binarySortTree.print();
    }

    private static class Node {

        private int value;

        private Node left;

        private Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        /**
         * 添加新节点
         * 约定：当前节点的左子节点比当前节点小，右子节点比当前节点大，
         * 特别地，当左子节点=当前节点，那么同一的挂到右子节点
         *
         * @param node 新添加节点
         */
        public void add(Node node) {
            // 递归结束条件
            if (node == null) {
                return;
            }
            // 当前节点的值比新增节点的值大，新节点成为左子节点
            if (this.value > node.value) {
                // 当前节点的左子节点为null
                if (this.left == null) {
                    this.left = node;
                } else {
                    // 递归查找合适的位置
                    this.left.add(node);
                }
            }
            // 当前节点的值<=新增节点那么，新节点成为右子节点
            else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    // 以右子子节点作为跟节点查找合适的位置
                    this.right.add(node);
                }
            }
        }

        /**
         * 中序遍历
         */
        public void infixOrder() {
            // 递归寻找最左节点
            if (this.left != null) {
                this.left.infixOrder();
            }
            // 输出当前节点
            System.out.println(this);
            // 递归寻找最有节点
            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        /**
         * 查找
         *
         * @param value 值
         * @return 目标节点, 不存在返回null
         */
        public Node findTargetNode(int value) {
            // 找到目标值
            if (this.value == value) {
                return this;
            }
            // 目标值比当前值小，并且当前节点的左节点不为空，向左递归查找
            if (this.left != null && value < this.value) {
                return this.left.findTargetNode(value);
            }
            // 目标值比当前值小，并且左节点为空，说明该节点不存在
            if (this.left == null && value < this.value) {
                return null;
            }
            // 目标值比当前值大，并且当前节点右节点不为空，向右递归查找
            if (this.right != null && value > this.value) {
                return this.right.findTargetNode(value);
            }
            // 没找到
            return null;
        }

        /**
         * 查找父节点
         *
         * @param value 子节点值
         * @return 父节点，不存在返回null
         */
        public Node findParent(int value) {
            /*
             *            7
             *          /   \
             *         3    10
             *        / \   / \
             *       1  5  9  12
             *        \
             *         2
             * 找到父节点
             * */
            if ((this.left != null && this.left.value == value) ||
                    (this.right != null && this.right.value == value)) {
                return this;
            }
            // 向左查找
            if (this.left != null && value < this.value) {
                return this.left.findParent(value);
            }
            // 向右查找
            if (this.right != null && value > this.value) {
                return this.right.findParent(value);
            }
            // 没找到父节点
            return null;
        }
    }

    /**
     * 二叉排序树
     */
    private static class BinarySortTree {

        /**
         * 当前这颗二叉排序树的根节点，默认为null
         */
        private Node root;

        /**
         * 添加节点
         *
         * @param node 新节点
         */
        public void add(Node node) {
            // 根节点为空
            if (root == null) {
                root = node;
            }
            // 查找合适的位置添加
            else {
                root.add(node);
            }
        }


        /**
         * 中序遍历
         */
        public void print() {
            if (root == null) {
                System.err.println("Current root is null!");
            } else {
                root.infixOrder();
            }
        }

        private Node findTarget(int value) {
            if (root == null) {
                return null;
            }
            if (root.value == value) {
                return root;
            }
            return root.findTargetNode(value);
        }

        private Node findParent(int value) {
            return root.findParent(value);
        }

        /**
         * 查找当前子树的最小值，同时删除最小节点
         *
         * @param node 子树根节点
         * @return 最小值
         */
        private int findMinValue(Node node) {
            // 由二叉排序树特性可知，最小值在最左节点
            while (node.left != null) {
                node = node.left;
            }
            int minValue = node.value;
            deleteNode(minValue);
            return minValue;
        }

        /**
         * 删除节点
         *
         * @param value 被删除节点的值
         */
        public void deleteNode(int value) {
            Node target = findTarget(value);
            // 被删除节点不存在，返回
            if (target == null) {
                return;
            }
            // 查找被删除节点的父节点
            Node parent = findParent(value);
            // 1.父节点不存在，说明被删除节点是BST的根节点
            if (parent == null) {
                root = null;
                return;
            }
            // 2.删除的是叶子节点
            /*
             *            7
             *          /   \
             *         3    10
             *        / \   / \
             *       1   5 9  12
             *        \
             *         2
             * */
            if (target.left == null && target.right == null) {
                // 被删除节点是父节点的左子节点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                    return;
                }
                // 被删除节点是父节点的右子节点
                if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
            }
            // 3.删除的是有两个子节点的非叶子节点
            else if (target.left != null && target.right != null) {
                // 查找被删除节点右子树的最小节点，同时删除最小节点
                // 把最小节节点的值赋给被删除节点
                target.value = findMinValue(target.right);
            }
            // 4.删除的是之有一个节点的非叶子节点
            else {
                // 被删除节点是parent左节点
                if (parent.left.value == target.value) {
                    // 被删除节点有左节点
                    if (target.left != null) {
                        parent.left = target.left;
                    }
                    // 被删除节点有右节点
                    else {
                        parent.right = target.right;
                    }
                }
                // 被删除节点是parent右节点
                if (parent.right.value == target.value) {
                    // 被删除节点有左子节点
                    if (target.left != null) {
                        parent.right = target.left;
                    }
                    // 被删除节点有右子节点
                    else{
                        parent.right = target.right;
                    }
                }
            }
        }
    }
}
