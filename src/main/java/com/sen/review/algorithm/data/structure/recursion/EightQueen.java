package com.sen.review.algorithm.data.structure.recursion;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 递归回溯解决八皇后问题,棋盘是 8*8 大小
 */
public class EightQueen {

    private static final int MAX_QUEEN = 8;

    /**
     * 理论上记录棋盘和皇后的位置是要用二维数组表示，这里用一位数组表示
     * 数组含义：
     * 1.数组的下标 index 表示第 index+1 个皇后
     * 2.数组的下标 index 表示第 index+1 个皇后摆放在 index+1 行
     * 3.数组的下标 index 对应的值 value 表示di index+1 个皇后摆放在 value+1 列
     */
    private static final int[] CHESSBOARD = new int[MAX_QUEEN];

    /**
     * 记录解法
     */
    private static int count = 0;

    public static void main(String[] args) {
        // 从第一行第一个皇后开始摆放
        put(0);
        System.out.printf("一共有：%d种解法", count);
    }

    /**
     * 判断第 n+1 个皇后和已经摆放的皇后是否冲突
     *
     * @param n 表示第 n+1 个皇后
     * @return true 无冲突，false发生冲突
     */
    private static boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // CHESSBOARD[i] == CHESSBOARD[n] 表示第 i 个皇后和第 n 个皇后在同一列
            if (CHESSBOARD[i] == CHESSBOARD[n]
                    // 此成立表示第 i 个皇后和第 n 个皇后在同一斜线，由数学定义可知其代表 y=x 这条先的斜率
                    || Math.abs(n - i) == Math.abs(CHESSBOARD[n] - CHESSBOARD[i])) {
                return false;
            }
            // 无需判断行是否相同，定义数组时下标index代表的就是行而行是递增的
        }
        return true;
    }

    /**
     * 打印结果
     */
    private static void print() {
        count++;
        for (int val : CHESSBOARD) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    /**
     * 摆放皇后
     *
     * @param n 需要摆放的第 n+1 个皇后,同时也代表行
     */
    private static void put(int n) {
        // 当前需要摆放的是第 n 个皇后等于MAX_QUEEN表示前面的 n-1 个已经摆好了也就是 n=8 时结束递归，此时摆好了0-7个皇后
        // 也就是8个已经摆好了
        if (n == MAX_QUEEN) {
            print();
        } else {
            // i 代表列
            for (int i = 0; i < MAX_QUEEN; i++) {
                // 使用循环和递归实现回溯,先第一行第一列摆放一个皇后
                CHESSBOARD [n] = i;
                if (judge(n)) {
                    // 摆放下一个皇后
                    put(n + 1);
                }
            }
        }
    }
}
