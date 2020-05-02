package com.sen.review.algorithm.divide.and.conquer;

/**
 * @Author: Sen
 * @Date: 2020/5/2 15:51
 * @Description: 分治算法--解决汉诺塔问题
 * 思想：将问题分解成可以求解的相互独立的问题进行求解，然后再合并小问题的解组成原始问题的解
 * 解决汉诺塔问题思想：
 * 1.不管有多少个盘子都看看成只有上面一个盘子和最底下的一个盘子
 * 2.把上面的盘子移动到中间柱子b
 * 3.把最底下的盘子移动到目标柱子c
 * 4.把中间柱子b的盘子移动到目标柱子c
 */
public class DivideAndConquer {

    public static void main(String[] args) {
        resolveTowerOfHanoi(3, "A", "B", "C");
    }

    /**
     * 解决汉诺塔问题
     *
     * @param number 盘子总数，从上到下开始数
     * @param a      起始柱子
     * @param b      中间柱子
     * @param c      结束柱子
     */
    private static void resolveTowerOfHanoi(int number, String a, String b, String c) {
        // 问题阈值,只有一个盘子时直接移动到目标柱子
        if (number == 1) {
            System.out.println("移动第1个盘子：" + a + "->" + c);
        } else {
            /*
            第1个盘子：A->C
            第2个盘子：A->B
            第1个盘子：C->B
            第3个盘子：A->C
            第1个盘子：B->A
            第2个盘子：B->C
            第1个盘子：A->C
            */
            // 把number-1个盘子移动到中间柱子b
            resolveTowerOfHanoi(number - 1, a, c, b);
            // 移动第number个盘子到目标柱子c
            System.out.println("移动第" + number + "个盘子：" + a + "->" + c);
            // 把中间柱子b的number-1个盘子移动到目标柱子
            resolveTowerOfHanoi(number-1, b, a, c);
        }
    }
}
