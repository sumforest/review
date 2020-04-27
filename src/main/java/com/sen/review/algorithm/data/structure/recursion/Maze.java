package com.sen.review.algorithm.data.structure.recursion;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 递归回溯解决迷宫问题
 */
public class Maze {

    public static void main(String[] args) {
        // 迷宫二维数组，1-墙，2-已走过的路，3-死路
        int[][] maze = new int[8][7];

        // 初始上、下化墙
        for (int i = 0; i < maze[0].length; i++) {
            maze[0][i] = 1;
            maze[maze.length-1][i] = 1;
        }
        // 初始化左右墙
        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = 1;
            maze[i][maze[0].length-1] = 1;
        }

        // 添加障碍物
        maze[3][1] = 1;
        maze[3][2] = 1;
        //打印迷宫
        for (int[] row : maze) {
            for (int column : row) {
                System.out.print(column+" ");
            }
            System.out.println();
        }
        System.out.println("------------- 寻路后 -------------");
        // 假定从[1,1]出发
        findWay(maze, 1, 1);

        //打印路径
        for (int[] row : maze) {
            for (int column : row) {
                System.out.print(column+" ");
            }
            System.out.println();
        }
    }

    /**
     * 假定：
     * 当maze[6][5] == 2表示寻成功
     * 0-未走过；1-墙；2-已走过通路；3-死路
     * 寻路顺序：下->右->上->左
     * @param maze 迷宫
     * @param x 开始行
     * @param y 开始列
     * @return true寻路成功，false寻路失败
     */
    private static boolean findWay(int[][] maze, int x, int y) {
        if (maze[6][5] == 2) {
            return true;
        }else {
            // 只有当前位置maze[x][y] = 0才可以继续寻路
            if (maze[x][y] == 0) {
                // 先把当前位置标记为2，能走通
                maze[x][y] = 2;
                // 先向下走
                if (findWay(maze, x + 1, y)) {
                    return true;
                }
                // 回溯，再向右走
                else if (findWay(maze, x, y + 1)) {
                    return true;
                }
                // 回溯，再向上走
                else if (findWay(maze, x - 1, y)) {
                    return true;
                }
                // 回溯，再向左走
                else if (findWay(maze, x, y - 1)) {
                    return true;
                }
                // 都走不通，把当前位置标记为3
                maze[x][y] = 3;
            }
            // 寻路失败
            return false;
        }
    }
}
