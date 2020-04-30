package com.sen.review.algorithm.data.structure.tree;

/**
 * @Author: Sen
 * @Date: 2020/4/30
 * @Description: 线索化二叉树
 */
public class ThreadedBinaryTreeDemo {

    public static void main(String[] args) {
         /*
                  1
               /     \
              3        6
             /  \      /
            8   10    14
         */
        HeroNode a = new HeroNode(1, "a");
        HeroNode b = new HeroNode(3, "b");
        HeroNode c = new HeroNode(6, "c");
        HeroNode d = new HeroNode(8, "d");
        HeroNode e = new HeroNode(10, "e");
        HeroNode f = new HeroNode(14, "f");
        // 构建二叉树
        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        // 线索二叉树
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(a);
        threadedBinaryTree.threadBinaryTree();
        // 中序遍历线索二叉树
        threadedBinaryTree.infixOrder();
    }

    private static class HeroNode {

        private int no;

        private String name;

        /**
         * 当前节点的左子节点
         */
        private HeroNode left;

        /**
         * 0--当前节点的左指针指向左子树，1--前驱节点，默认值 0
         */
        private int leftType;

        /**
         * 当前节点右子节点
         */
        private HeroNode right;

        /**
         * 0--当前节点右指针指向右子树，1--指向后继节点，默认值 0
         */
        private int rightType;

        public HeroNode(int no, String name) {
            this.no = no;
            this.name = name;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * 线索化的二叉树
     */
    private static class ThreadedBinaryTree {

        /**
         * 二叉树根节点
         */
        private HeroNode root;

        /**
         * 记录当前节点的前驱节点
         */
        private HeroNode pre;

        public ThreadedBinaryTree(HeroNode root) {
            this.root = root;
        }

        public void threadBinaryTree(){
            this.threadBinaryTree(root);
        }

        /**
         * 中序线索化二叉树
         */
        private void threadBinaryTree(HeroNode node) {
            /*
                     1
                  /     \
                 3        6
                /  \      /
               8   10    14
            */
            if (node == null) {
                // 当前节点不存在递归结束
                return;
            }
            // 寻找到当前位置最左子节点
            threadBinaryTree(node.left);
            // 线序化当前节点--最左子节点
            if (node.left == null) {
                node.left = pre;
                // 把当前节点的左指针标记为前驱节点
                node.leftType = 1;
            }
            // 在下一个节点线索化前驱节点的后继节点
            if (pre != null && pre.right == null) {
                // 当前节点作为前驱节点的后继节点
                pre.right = node;
                // 把前驱节点右指针标记为后继节点
                pre.rightType = 1;
            }
            // 把当前节点作为前驱节点
            pre = node;
            // 向右递归查找最右节点
            threadBinaryTree(node.right);
        }

        /**
         * 中序遍历线索二叉树
         */
        private void infixOrder(){
            HeroNode cur = root;
            while (cur != null) {
                 /*
                          1
                       /     \
                      3        6
                     /  \      /
                    8   10    14
                 */
                // 查找最左节点
                while (cur.leftType != 1) {
                    cur = cur.left;
                }
                // 输出当前节点
                System.out.println(cur);
                // 输出当前节点的后继节点
                while (cur.rightType == 1) {
                    cur = cur.right;
                    System.out.println(cur);
                }
                // 当前节点没有后继节点时，把cur指向右子节点
                cur = cur.right;
            }

        }
    }
}
