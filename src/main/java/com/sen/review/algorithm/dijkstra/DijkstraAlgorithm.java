package com.sen.review.algorithm.dijkstra;

import java.security.PublicKey;
import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2020/5/4 18:43
 * @Description: 迪杰斯特拉算法--解决邮差送信最短路径问题
 * 思想：
 * 1.起始顶点开始遍历所有可达的顶点并且把权值保存到数组中
 * 2.在起始顶点可达的顶点中选择权值（距离）最小的顶点作为途径顶点，遍历所有经过途径顶点可达的顶点，当前通过途径顶点可达
 * 的顶点的权值比起始顶点直接可达的权值小则更新其为最小权值，并且把可到顶点的前驱节点记为途径顶点
 * 3.如此循环直到所有节点遍历完，就得到连通网格中的最小生成树。
 */
public class DijkstraAlgorithm {

    public static void main(String[] args) {
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertexes.length][vertexes.length];
        matrix[0] = new int[]{UNREACHABLE, 5, 7, UNREACHABLE, UNREACHABLE, UNREACHABLE, 2};
        matrix[1] = new int[]{5, UNREACHABLE, UNREACHABLE, 9, UNREACHABLE, UNREACHABLE, 3};
        matrix[2] = new int[]{7, UNREACHABLE, UNREACHABLE, UNREACHABLE, 8, UNREACHABLE, UNREACHABLE};
        matrix[3] = new int[]{UNREACHABLE, 9, UNREACHABLE, UNREACHABLE, UNREACHABLE, 4, UNREACHABLE};
        matrix[4] = new int[]{UNREACHABLE, UNREACHABLE, 8, UNREACHABLE, UNREACHABLE, 5, 4};
        matrix[5] = new int[]{UNREACHABLE, UNREACHABLE, UNREACHABLE, 4, 5, UNREACHABLE, 6};
        matrix[6] = new int[]{2, 3, UNREACHABLE, UNREACHABLE, 4, 6, UNREACHABLE};
        Graph graph = new Graph(vertexes, matrix);
        graph.dijkstra();
        graph.print();
    }

    private static final int UNREACHABLE = 65535;

    /**
     * 描述初始顶点的类
     */
    private static class InitialVertex{

        /**
         * 保存当前顶点到其他所有顶点的距离或者通过途径顶点的距离
         * ，index表示顶点的下标，distance[index]表示到其的距离，特别地到自身的距离为0
         */
        private final int[] distance;

        /**
         * 保存前驱顶点，index表示顶点的下标，pre_vertex[index]表示其前驱顶点的下标
         * 特别地，没有前驱顶点时前驱顶点为其本身，默认值为0
         */
        private final int[] pre_vertex;

        /**
         * 用于记录index下标对应的顶点是否已经访问，1--已经访问，0--未访问，默认值0
         */
        private final int[] isVisited;

        public InitialVertex(int length, int initialIndex) {
            this.distance = new int[length];
            this.pre_vertex = new int[length];
            this.isVisited = new int[length];
            // 初始化distance,默认未所有顶点不可达
            Arrays.fill(this.distance, UNREACHABLE);
            // 把起始位置的距离置为0
            this.distance[initialIndex] =0;
            // 把当前顶点标记为已访问
            this.isVisited[initialIndex] = 1;
            // 初始化起始顶点前驱顶点
            this.pre_vertex[initialIndex] = initialIndex;
        }

        /**
         * 获取起始顶点形成最小权值的途径顶点并且该顶点还没有被访问过
         * @param vertex 起始顶点所描述的类
         * @return 途径顶点的下标,不存在返回-1
         */
        public int getNextVertex(InitialVertex vertex) {
            // 记录最小值
            int midValue = UNREACHABLE;
            // 记录最小值的坐标
            int index = -1;
            for (int i = 0; i < distance.length; i++) {
                // 获取没有访问过并且与起始顶点所形成的权值最小的顶点下标
                if (isVisited[i] == 0 && distance[i] < midValue) {
                    midValue = distance[i];
                    index = i;
                }
            }
            // 把该顶点标记为已访问
            isVisited[index] = 1;
            return index;
        }
    }

    /**
     * 图
     */
    private static class Graph{

        /**
         * 保存顶点
         */
        private final char[] vertexes;

        /**
         * 邻接矩阵
         */
        private final int[][] matrix;

        /**
         * 创建起始顶点描述类
         */
        private final InitialVertex initialVertex;

        public Graph(char[] vertexes, int[][] matrix) {
            this.vertexes = vertexes;
            this.matrix = matrix;
            this.initialVertex = new InitialVertex(vertexes.length, 6);
        }

        /**
         * 更新当前顶点于起始顶点的权值和前驱顶点
         * @param index 起始顶点/途径顶点的下标
         * @param vertex 起始顶点描述类
         */
        private void update(int index, InitialVertex vertex) {
            for (int i = 0; i < vertexes.length; i++) {
                // 起始顶点的到其他所有顶点的权值
                int weight = vertex.distance[index] + matrix[index][i];
                // 起始通过该顶点到达的当前顶点的值比原来的要小，更新距离数组
                if (weight < vertex.distance[i]) {
                    // 更新记录顶点的距离
                    vertex.distance[i] = weight;
                    // 把当前顶点的前驱顶点记录为index这个顶点
                    vertex.pre_vertex[i] = index;
                }
            }
        }

        /**
         * 迪杰斯特拉算法
         */
        public void dijkstra(){
            // 初始化起始顶点
            update(6, initialVertex);
            // 从剩余顶点中获取权值最小的顶点
            for (int i = 1; i < vertexes.length; i++) {
                int nextVertex = initialVertex.getNextVertex(initialVertex);
                // 以当前顶点作为途径顶点更新到顶点的距离和前驱顶点
                update(nextVertex, initialVertex);
            }
        }

        /**
         * 输出邻接矩阵、距离数组、前驱顶点记录数组
         */
        public void print(){
            for (int[] rows : matrix) {
                for (int column : rows) {
                    System.out.printf("%8d",column);
                }
                System.out.println();
            }

            System.out.printf("距离数组：%s\n",Arrays.toString(initialVertex.distance));
            System.out.printf("前驱顶点数组：%s\n", Arrays.toString(initialVertex.pre_vertex));
            System.out.println("最终结果：");
            for (int i = 0; i < initialVertex.distance.length; i++) {
                System.out.printf("%s(%d), ",vertexes[i],initialVertex.distance[i]);
            }
        }
    }
}
