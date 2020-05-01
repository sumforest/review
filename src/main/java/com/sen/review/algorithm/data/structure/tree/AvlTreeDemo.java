package com.sen.review.algorithm.data.structure.tree;

/**
 * @Author: Sen
 * @Date: 2020/5/1 17:20
 * @Description: 平衡二叉排序树(AVL树)
 * 前提：必须是一颗二叉排序树
 * 特点：左右子树高度差绝对值不超过1
 */
public class AvlTreeDemo {

    public static void main(String[] args) {
        // int[] arr = {4, 3, 6, 5, 7, 8};
        // 测试左旋转
        /*
        Node a = new Node(4);
        Node b = new Node(3);
        Node c = new Node(6);
        Node d = new Node(5);
        Node e = new Node(7);
        Node f = new Node(8);
        a.left = b;
        a.right = c;
        c.left = d;
        c.right = e;
        e.right = f;
        System.out.println(a.height());
        System.out.println(a.left.height());
        System.out.println(a.right.height());
        a.leftRotate();
        System.out.println(a.height());
        System.out.println(a.left.height());
        System.out.println(a.right.height());
        */

        // 测试右旋
        // int[] arr = {10, 12, 8, 9, 7, 6};
       /* Node a = new Node(10);
        Node b = new Node(12);
        Node c = new Node(8);
        Node d = new Node(9);
        Node e = new Node(7);
        Node f = new Node(6);
        a.right = b;
        a.left = c;
        c.right = d;
        c.left = e;
        e.left = f;
        System.out.println(a.height());
        System.out.println(a.left.height());
        System.out.println(a.right.height());
        a.rightRotate();
        System.out.println(a.height());
        System.out.println(a.left.height());
        System.out.println(a.right.height());*/

        int[] arr = {10, 11, 7, 6, 8, 9};
        AvlTree avlTree = new AvlTree();
        for (int value : arr) {
            avlTree.add(new Node(value));
        }
        System.out.println("avl树高度：" + avlTree.height());
        System.out.println("左子树高度：" + avlTree.leftHeight());
        System.out.println("右子树高度：" + avlTree.rightHeight());
        avlTree.print();
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
         * 获取当前二叉树高度，根节点算一层
         */
        public int height() {
            /*
             *           10
             *          /  \
             *         7    11
             *        / \
             *       6   8
             *            \
             *             9
             * */
            return Math.max(this.left == null ? 0 : this.left.height()
                    , this.right == null ? 0 : this.right.height()) + 1;
        }

        /**
         * 左旋转
         */
        public void leftRotate() {
            // 以当前节点值创建一个新节点
            Node newNode = new Node(this.value);
            // 新节点右指针指向当前节点的右子节点的左子节点
            newNode.right = this.right.left;
            // 新节点左指针指向当前节点左子节点
            newNode.left = this.left;
            // 把当前节点右子节点的值赋给当前节点
            this.value = this.right.value;
            // 当前节点的右指针指向当前节点的右子节点的右子节点
            this.right = this.right.right;
            // 当前节点左指针指向新节点
            this.left = newNode;
        }

        /**
         * 右旋转
         */
        public void rightRotate() {
            /* 经过左旋转后：
             *           10
             *          /  \
             *         8    11
             *        / \
             *       7   9
             *      /
             *     6
             * */
            // 以当前节点值创建一个新节点
            Node newNode = new Node(this.value);
            // 新节点右指针指向当前节点的右子节点
            newNode.right = this.right;
            // 新节点左指针指向当前节点左子节点的右子节点
            newNode.left = this.left.right;
            // 当前节点左子节点的值赋给当前节点
            this.value = this.left.value;
            // 当前节点左指针址向当前节点的左子节点的左子节点
            this.left = this.left.left;
            // 当前节点右指针指向新节点
            this.right = newNode;
        }

        /**
         * 中序遍历二叉树
         */
        public void infixOrder() {
            if (this.left != null) {
                this.left.infixOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        public int leftHeight() {
            if (this.left == null) {
                return 0;
            }
            return this.left.height();
        }

        public int rightHeight() {
            if (this.right == null) {
                return 0;
            }
            return this.right.height();
        }

        /**
         * 添加新节点同时保证时平衡二叉排序树(AVL树)
         *
         * @param node 新加节点
         */
        public void add(Node node) {
            if (node == null) {
                return;
            }
            // 新加节点值比当前节点值小
            if (node.value < this.value) {
                if (this.left == null) {
                    this.left = node;
                }
                // 递归向左查找合适位置
                else {
                    this.left.add(node);
                }
            }
            // 新加节点值 >= 当前节点值
            else {
                if (this.right == null) {
                    this.right = node;
                }
                // 递归向右查找合适的位置
                else {
                    this.right.add(node);
                }
            }
            // 每次添加节点后检查当前节点的左右子树是否符合AVL树
            // 右子树高度大于左子树高度，需要进行左旋转
            if (this.rightHeight() - this.leftHeight() > 1) {
                // 当前节点右子树的左子树高度大于当前节点右子树右子树高度，需要先右旋再左旋
                if (this.right != null && (this.right.leftHeight() > this.right.rightHeight())) {
                    // 右子节点右旋
                    this.right.rightRotate();
                }
                // 否则只需要旋转一次
                this.leftRotate();
            }
            /* 需要先左旋转再右旋转       只需要进行右旋
             *           10                10
             *          /  \              /  \
             *         7    11           7    11
             *        / \               / \
             *       6   8             6   8
             *            \           /
             *             9         9
             * */
            // 左子树高度大于右子树高度，需要右旋
            if (this.leftHeight() - this.rightHeight() > 1) {
                // 当前节点左子树的右子树高度大于当前节点左子树的左子树高度需要先进行左旋
                if (this.left != null && (this.left.rightHeight() > this.left.leftHeight())) {
                    // 左子节点左
                    this.left.leftRotate();
                }
                // 当前节点进行右旋
                this.rightRotate();
            }
        }
    }

    /**
     * 平衡二叉排序树
     */
    private static class AvlTree {

        private Node root;

        public int height() {
            if (root == null) {
                throw new IllegalStateException("Root Node is null!");
            }
            return root.height();
        }

        public int leftHeight() {
            if (root == null) {
                throw new IllegalStateException("Root Node is null!");
            }
            return root.leftHeight();
        }

        public int rightHeight() {
            if (root == null) {
                throw new IllegalStateException("Root Node is null!");
            }
            return root.rightHeight();
        }

        /**
         * @param node
         */
        public void add(Node node) {
            if (root == null) {
                root = node;
                return;
            }
            root.add(node);
        }

        public void print() {
            if (root == null) {
                System.err.println("Root Node is null!");
            }
            root.infixOrder();
        }
    }
}
