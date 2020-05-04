package com.sen.review.algorithm.kruskal;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/5/3 18:07
 * @Description: 克鲁斯卡尔算法--解决公交站（最短路径）问题
 * 思路：
 * 1.利用邻接矩阵创建所有的边
 * 2.对边数组按照边的权值进行升序排序
 * 3.遍历所有边，按照顺序依次选择边并且保证新添加的边不会和已存在的边形成回路
 * 4.循环结束后得到最小生成树
 */
public class KruskalAlgorithm {

    public static void main(String[] args) {
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 12, INF, INF, INF, 16, 14},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {14, INF, INF, INF, 8, 9, 0}};
        KruskalAlgorithm kruskal = new KruskalAlgorithm(vertexes, matrix);
        kruskal.printMatrix();
        kruskal.kruskal();
    }

    private static final int INF = Integer.MAX_VALUE;

    /**
     * 边总数
     */
    private int countOfEdge;

    /**
     * 顶点数组
     */
    private final char[] vertexes;

    /**
     * 邻接矩阵，描述顶点、边、权值
     */
    private final int[][] matrix;

    /**
     * 保存边
     */
    private Edge[] edges;

    public KruskalAlgorithm(char[] vertexes, int[][] matrix) {
        this.vertexes = vertexes;
        this.matrix = matrix;
        // 统计边的总数量，由于邻接矩阵沿着对角线对称所以只统计一般邻接矩阵
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (matrix[i][j] != INF) {
                    this.countOfEdge++;
                }
            }
        }
        this.edges = createEdge();
    }

    /**
     * 打印邻接矩阵
     */
    public void printMatrix() {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("%15d", ints[j]);
            }
            System.out.println();
        }
    }

    /**
     * 创建边
     *
     * @return 保存边的数组
     */
    private Edge[] createEdge() {
        Edge[] edges = new Edge[this.countOfEdge];
        // 记录当前操作边数组的下标
        int index = 0;
        // 统计边的总数量，由于邻接矩阵沿着对角线对称所以只统计一般邻接矩阵,避免重复
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new Edge(vertexes[i], vertexes[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }


    /**
     * 对边根据权值进行升序排序（冒泡排序）
     */
    private void sort() {
        boolean flag = false;
        // length - 1 轮
        for (int i = 0; i < edges.length - 1; i++) {
            // 每轮 length - i - 1 次
            for (int j = 0; j < edges.length - i - 1; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    // 交换
                    Edge temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                // 经行一轮没有发生交换，说明已经有序
                break;
            } else {
                // 重置标志
                flag = false;
            }
        }
    }

    /**
     * 获取顶点对应的下标
     *
     * @param vertex 顶点
     * @return 不存在返回-1
     */
    private int getPosition(char vertex) {
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == vertex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取终点
     *
     * @param ends        保存终点的数组
     * @param vertexIndex 起始顶点的下标
     * @return 终点下标
     */
    private int getEdgePointIndex(int[] ends, int vertexIndex) {
        // 存在终点，获取终点的位置
        // ends[vertexIndex] == 0 那么vertexIndex就是终点的下标
        while (ends[vertexIndex] != 0) {
            // 终点的下标保存在数组对应的值中
            vertexIndex = ends[vertexIndex];
        }
        return vertexIndex;
    }

    /**
     * 克鲁斯卡尔算法，解决公交站最短路径问题
     */
    public void kruskal() {
        // 对边进行排序
        sort();
        // 创建保存每个顶点对应的终点下标数组
        int[] ends = new int[vertexes.length];
        // 保存结果数组
        Edge[] result = new Edge[vertexes.length];
        // 记录操作数组的下标
        int index = 0;
        // 从所有存在的边选择权值最小的n-1条边把n个顶点连通
        /*
         * <E, F> <C, D> <D, E> <B, F> <E, G> <A, B> 权值=36
         * */
        for (Edge edge : edges) {
            // 获取边的起始顶点下标
            int startIndex = getPosition(edge.start);
            // 获取边的结束顶点下标
            int endIndex = getPosition(edge.end);

            // 获取开始和结束顶点的终点
            int destination1 = getEdgePointIndex(ends, startIndex);
            int destination2 = getEdgePointIndex(ends, endIndex);
            // 终点不相等说明新加的边没有构成回路
            if (destination1 != destination2) {
                // 保存结果
                result[index++] = edge;
                // 保存当前边的起始顶点的终点保存为结束顶点的终点
                ends[destination1] = destination2;
            }
        }
        // 输出结果
        System.out.println(Arrays.toString(result));
    }

    /**
     * 描述边的类
     */
    private static class Edge {

        /**
         * 边的起始顶点
         */
        private final char start;

        /**
         * 边的结束顶点
         */
        private final char end;

        /**
         * 边对应的权值
         */
        private final int weight;

        public Edge(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }
    }
}
