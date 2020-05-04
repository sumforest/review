package com.sen.review.algorithm.prim;

/**
 * @Author: Sen
 * @Date: 2020/5/3 15:42
 * @Description: 普利姆算法解决修路路径最小问题（本质最小生成树问题）
 * 思想：
 * 1.确定起始顶点
 * 2.从起始顶点开始查找能到达邻接顶点中权值最小的顶点，然后把该顶点标记为已访问
 * 3.循环n-1就能构建最小生成树，解决连通所有顶点总路径最小问题。
 */
public class PrimAlgorithm {

    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},
        };
        Graph graph = new Graph(data, weight);
        MinimumCostSpanningTree minimumCostSpanningTree = new MinimumCostSpanningTree(graph);
        minimumCostSpanningTree.prim(0);
    }

    /**
     * 图
     */
    private static class Graph {

        /**
         * 顶点
         */
        private final char[] vertexes;

        /**
         * 顶点总数
         */
        private final int countOfVertexes;

        /**
         * 邻接矩阵，记录图边、权重，权值==10000表示两顶点不可达
         */
        private final int[][] matrix;

        public Graph(char[] vertexes, int[][] matrix) {
            this.vertexes = vertexes;
            this.matrix = matrix;
            this.countOfVertexes = vertexes.length;
        }

        public void print() {
            for (int[] rows : matrix) {
                for (int column : rows) {
                    System.out.print(column + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * 最小生成树
     */
    private static class MinimumCostSpanningTree {

        /**
         * 图结构，保存最小生成树
         */
        private final Graph graph;

        /**
         * 标记顶点是否已经被访问,1--已访问 0--未访问，默认值为0
         */
        private final int isVisited[];

        private MinimumCostSpanningTree(Graph graph) {
            this.graph = graph;
            this.isVisited = new int[graph.countOfVertexes];
        }

        public void print() {
            this.graph.print();
        }

        /**
         * 普利姆算法创建最小生成树
         *
         * @param index 起始顶点下标
         */
        public void prim(int index) {
            // 把当前顶点标记为已访问
            isVisited[index] = 1;
            // 假设最小值,图中10000表示两顶点之间不连通
            int minValue = 10000;
            // 起始顶点下标
            int vertex1 = -1;
            // 起始顶点能到达顶点中权值最小的顶点下标
            int vertex2 = -1;
            /*
                <A,G> 权值：2
                <G,B> 权值：3
                <G,E> 权值：4
                <E,F> 权值：5
                <F,D> 权值：4
                <A,C> 权值：7
             */
            // 构造最小生成树的边，由最小生成树特点之，n个顶点一定有n-1条边所以i=1
            for (int i = 1; i < graph.countOfVertexes; i++) {
                // 以已访问的顶点开始遍历所有邻接顶点
                for (int j = 0; j < graph.countOfVertexes; j++) {
                    // 查找邻接顶点
                    for (int k = 0; k < graph.countOfVertexes; k++) {
                        // 起始顶点已访问，邻接顶点未访问，并且其权值有最小值
                        int temp = graph.matrix[j][k];
                        if (isVisited[j] == 1 && isVisited[k] == 0 && temp < minValue) {
                            // 更新最小权值
                            minValue = temp;
                            // 记录起始顶点下标
                            vertex1 = j;
                            // 记录权值最小邻接顶点下标
                            vertex2 = k;
                        }
                    }
                }
                // 经过一轮循环获取到一条边
                System.out.printf("<%s,%s>=%d\n", graph.vertexes[vertex1], graph.vertexes[vertex2]
                        ,graph.matrix[vertex1][vertex2]);
                // 把权值最小的邻接节点标记未已访问
                isVisited[vertex2] = 1;
                // 重置最小值
                minValue = 10000;
            }
        }
    }
}
