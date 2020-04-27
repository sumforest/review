package com.sen.review.algorithm.data.structure.josephu;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 单向环形链表解决约瑟夫问题
 * 假设：
 * n = 5 有5个人
 * k = 1 从第一个人开始报数
 * m = 2 每次报2下
 * 正确出队顺序：
 * 2 -> 4 -> 1 -> 5 -> 3
 */
public class JosephuProblem {

    public static void main(String[] args) {
        CircleList circleList = new CircleList();
        circleList.init(5);
        circleList.print();
        System.out.println("----------- 出队顺序 -----------");
        circleList.resolveJosephuProblem(5, 1, 2);
    }

    private static class CircleList {

        /**
         * 标记环形链表第一个节点
         */
        private Human first;

        /**
         * 初始化环形链表
         *
         * @param number 节点总数
         */
        public void init(int number) {
            // 指向当前节点的指针
            Human curr = null;
            for (int i = 0; i < number; i++) {
                Human human = new Human(i + 1);
                if (i == 0) {
                    // 构建只有一个节点的环形链表
                    first = human;
                    // 指向first自身
                    first.next = first;
                    curr = first;
                    // 不再执行以下逻辑
                    continue;
                }
                // 当前节点指向新加节点
                curr.next = human;
                // 新加节点执行第一个节点
                human.next = first;
                // 更新当前节点
                curr = human;
            }
        }

        /**
         * 解决约瑟夫问题
         *
         * @param n 总人数
         * @param k 第几个人开始报数
         * @param m 每次报多少次
         */
        public void resolveJosephuProblem(int n, int k, int m) {
            if (n < 0 || k > n || m < 0) {
                throw new IllegalArgumentException("参数有误，请检查您的参数");
            }
            // 辅助指针，初始状态指向循环列表的最后一个元素
            Human helper = first;
            // 移动helper到正确位置
            while (helper.next != first) {
                helper = helper.next;
            }

            // 把first、helper移动到起始位置k，移动步长 = k-1
            for (int i = 0; i < k - 1; i++) {
                first = first.next;
                helper = helper.next;
            }

            // 开始报数,当 helper == first 说明队列中只剩下一个节点，退出循环
            while (helper != first) {
                // 移动m-1步长
                for (int i = 0; i < m - 1; i++) {
                    first = first.next;
                    helper = helper.next;
                }
                System.out.print(first.no + "->");
                // first所指向的节点出队
                first = first.next;
                helper.next = first;
            }
            System.out.print(first.no);
        }

        public void print() {
            if (first == null) {
                System.err.println("Current List is empty!");
            }
            Human curr = first;
            while (curr.next != first) {
                System.out.println(curr);
                curr = curr.next;
            }
            System.out.println(curr);
        }
    }

    /**
     * 人--单向环形链表节点
     */
    private static class Human {

        /**
         * 编号
         */
        int no;

        /**
         * 指针，指向下一个人
         */
        Human next;

        public Human(int no) {
            this.no = no;
        }

        @Override
        public String toString() {
            return "Human{" +
                    "no=" + no +
                    '}';
        }
    }
}
