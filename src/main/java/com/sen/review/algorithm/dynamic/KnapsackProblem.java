package com.sen.review.algorithm.dynamic;

/**
 * @Author: Sen
 * @Date: 2020/5/2 18:05
 * @Description: 动态规划解决背包问题（0-1背包问题）即商品数量只有一个
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        // 每种商品的商品价值
        int[] value = {1500, 3000, 2000};
        // 每种商品重量
        int[] weight = {1, 4, 3};
        // 背包容纳重量
        int limitWeight = 4;
        // 表格记录各种商品在符合背包重量下方案
        // 行+1，用第一行表示背包空的情况，列+1，用空一列表示容量0的情况
        int[][] table = new int[value.length + 1][limitWeight + 1];
        // 记录背包防止策略
        int[][] path = new int[value.length + 1][limitWeight + 1];

        // 填表
        // 遍历商品，i=1表示第一个商品开始
        for (int i = 1; i < table.length; i++) {
            // 遍历背包重量的状况，j=1表示从第一个单位重量开始
            for (int j = 1; j < table[0].length; j++) {
                /*背包重量:   0   1    2    3   4
                            0   0    0    0    0
                     商品1   0 1500 1500 1500 1500
                     商品2   0 1500 1500 1500 3000
                     商品3   0 1500 1500 2000 3500*/
                // 当前商品重量大于背包当前可用重量
                if (weight[i - 1] > j) {
                    // 采用上一单元格的值
                    table[i][j] = table[i - 1][j];
                }
                // 当前背包可用重量>=当前商品重量
                else {
                    // 添加当前商品后背包的总价值
                    // table[i - 1][j - weight[i - 1]表示添加当前商品前上一单元格背包总价值
                    int worth = value[i - 1] + table[i - 1][j - weight[i - 1]];
                    // 添加当前商品后背包的总价值大于上一单元格的总价值
                    if (table[i - 1][j] < worth) {
                        table[i][j] = worth;
                        // 跟新策略标记
                        path[i][j] = 1;
                    } else {
                        // 否则采用上一单元格的策略
                        table[i][j] = table[i - 1][j];
                    }
                }
            }
        }
        // 填表完成输出
        for (int[] row : table) {
            for (int column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
        System.out.println("------------ 策略表 ----------");
        for (int[] row : path) {
            for (int column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
        // 输出最优策略
        int i = table.length - 1;
        int j = table[0].length - 1;
        while (i >= 0 && j >= 0) {
            int flag = path[i][j];
            while (flag > 0) {
                System.out.printf("放置商品：%s\n", i);
                // 移动到没放置当前商品前对应的单元格
                j -= weight[i - 1];
                flag = path[i][j];
            }
            i--;
        }
    }
}
