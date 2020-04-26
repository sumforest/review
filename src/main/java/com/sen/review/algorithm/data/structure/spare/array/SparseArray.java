package com.sen.review.algorithm.data.structure.spare.array;

/**
 * @Author: Sen
 * @Date: 2020/4/26
 * @Description: 稀疏数组，使用稀疏数组记录棋盘上的棋子
 */
public class SparseArray {

    public static void main(String[] args) {
        //创建一个11 * 11的棋盘，1代表黑棋，2代表白棋棋
        int[][] checkerBoard = new int[11][11];
        //给二维数组赋值
        checkerBoard[1][2] = 1;
        checkerBoard[2][3] = 2;
        checkerBoard[10][10] = 1;

        // 遍历二维数组
        for (int[] row : checkerBoard) {
            for (int column : row) {
                System.out.printf(" %d ", column);
            }
            System.out.println();
        }

        // 统计二维数组中有效值个数
        int sum = 0;
        for (int[] row : checkerBoard) {
            for (int column : row) {
                if (column != 0) {
                    sum++;
                }
            }
        }

        // 创建稀疏数组--sum + 1行 3列的二维数组
        int[][] sparseArr = new int[sum + 1][3];
        // 初始化稀疏数组
        // 原始二维数组的行
        sparseArr[0][0] = checkerBoard.length;
        // 原始二维数组的列
        sparseArr[0][1] = checkerBoard[0].length;
        // 原始二维数组的有效值总数
        sparseArr[0][2] = sum;

        // 给稀疏数组赋值
        // 记录当前操作稀疏数组的列
        int sparseRow = 1;
        // 遍历行
        for (int i = 0; i < checkerBoard.length; i++) {
            // 遍历列
            for (int j = 0; j < checkerBoard[0].length; j++) {
                if (checkerBoard[i][j] != 0) {
                    // 记录棋子的行
                    sparseArr[sparseRow][0] = i;
                    // 记录棋子所在的列
                    sparseArr[sparseRow][1] = j;
                    // 记录棋子的颜色
                    sparseArr[sparseRow][2] = checkerBoard[i][j];
                    // 移动的到稀疏数组下一行
                    sparseRow++;
                }
            }
        }
        System.out.println("------------------- 稀疏数组 ---------------------");
        // 遍历稀疏数组
        for (int[] row : sparseArr) {
            for (int column : row) {
                System.out.printf(" %d ", column);
            }
            System.out.println();
        }

        // 从稀疏数组中恢复棋盘，并展现数组
        System.out.println("------------------- 恢复原数组 ---------------------");
        int[][] recover = new int[sparseArr[0][0]][sparseArr[0][1]];
        // 从第2行遍历稀疏数组内容，给二维数组赋值
        for (int i = 1; i < sparseArr.length; i++) {
            recover[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        // 遍历二维数组
        for (int[] row : recover) {
            for (int column : row) {
                System.out.printf(" %d ", column);
            }
            System.out.println();
        }

    }
}
