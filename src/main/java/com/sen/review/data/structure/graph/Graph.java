package com.sen.review.data.structure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Sen
 * @Date: 2020/5/1 22:39
 * @Description: 无向图（邻接矩阵实现）
 */
public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        // 添加边  A-B A-C B-C B-D B-E
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 1);
        graph.print();
        // System.out.println("深度优先搜索算法：");
        // graph.dfs();
        System.out.println("广度优先搜索算法：");
        graph.bfs();
    }

    /**
     * 记录图的顶点集合
     */
    private final List<String> vertexes;

    /**
     * 记录当前图的所有顶点所构成的边，1--两顶点连通，0--两顶点不连通
     * 形如：A B C D E F
     * A 0 1 0 0 0 0
     * B 1 0 0 0 0 0
     * C 0 0 0 0 0 0
     * D 0 0 0 0 0 0
     * E 0 0 0 0 0 0
     * F 0 0 0 0 0 0
     */
    private final int[][] edges;

    /**
     * 记录边的总数
     */
    private int countOfEdge;

    /**
     * 下标对应着顶点在集合总的下标，默认false--未被访问，true--已经被访问
     */
    private boolean[] isVisited;

    public Graph(int vertexesNumber) {
        this.vertexes = new ArrayList<>();
        this.edges = new int[vertexesNumber][vertexesNumber];
        this.isVisited = new boolean[vertexesNumber];
    }

    /**
     * 添加顶点
     *
     * @param vertex 顶点
     */
    public void addVertex(String vertex) {
        vertexes.add(vertex);
    }

    /**
     * 添加边,无向
     *
     * @param vertex1 顶点1
     * @param vertex2 顶点2
     * @param weight  边的权值，1--两顶点连通，0--两顶点不连通；默认值0
     */
    public void addEdge(int vertex1, int vertex2, int weight) {
        if (vertex1 > edges.length - 1 || vertex2 > edges.length - 1) {
            throw new IllegalArgumentException("该顶点不存在");
        }
        countOfEdge++;
        edges[vertex1][vertex2] = weight;
        edges[vertex2][vertex1] = weight;
    }

    /**
     * 获取顶点
     *
     * @param index 顶点下标
     * @return 顶点
     */
    public String getVertex(int index) {
        return vertexes.get(index);
    }

    /**
     * @return 获取顶点总数
     */
    public int getVertexCount() {
        return vertexes.size();
    }

    public int getEdgeCount() {
        return countOfEdge;
    }

    /**
     * 打印邻接矩阵
     */
    public void print() {
        System.out.print("  ");
        for (String vertex : vertexes) {
            System.out.printf("%s ", vertex);
        }
        System.out.println();
        int index = 0;
        for (int[] edge : edges) {
            System.out.print(vertexes.get(index++) + " ");
            for (int weight : edge) {
                System.out.print(weight + " ");
            }
            System.out.println();
        }
    }

    /**
     * 获取当前节点的邻接顶点
     *
     * @param index 当前顶点下标
     * @return 存在返回下标，否则return -1
     */
    private int getNeighborVertex(int index) {
        for (int i = 0; i < vertexes.size(); i++) {
            // 遍历当前顶点所在的
            if (edges[index][i] > 1) {
                // 权值大一1表示该顶是邻接顶点
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取当前节点的邻接节点的邻接节点
     * @param index1 当前节点
     * @param index2 邻接节点
     * @return 存在返回下标，否则return -1
     */
    private int getNextNeighbor(int index1, int index2) {
        // 以邻接节点的下一个位置作为起始点
        for (int i = index2 + 1; i < vertexes.size(); i++) {
            // 遍历当前顶点所在的行
            if (edges[index1][i] > 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 图遍历--深度优先搜索算法
     *
     * @param isVisited 标记顶点访问以否
     * @param index     顶点下标
     */
    private void dfs(boolean[] isVisited, int index) {
        /*   C -------B---------E
         *    \      / \
         *     \    /   \
         *      \  /     \
         *       \/       D
         *       A
         * */
        // 输出当前顶点
        String vertex = vertexes.get(index);
        System.out.print(vertex + "->");
        // 把当前顶点标记为以访问
        isVisited[index] = true;

        // 获取当前节点的邻接节点下标
        int neighbor = getNeighborVertex(index);
        // 邻接顶点存在时，以邻接顶点作为起始顶点继续深度优先遍历
        while (neighbor != -1) {
            // 当前节点未被访问过才继续
            if (!isVisited[neighbor]) {
                // 以邻接节点作为初始节点继续访问
                dfs(isVisited, neighbor);
            }
            // 获取邻接节点的邻接节点下标，以其作为下一次dfs的起始点
            neighbor = getNextNeighbor(index, neighbor);
        }
    }

    /**
     * 重载，用每一个顶点作为起始顶点访问，避免因某个顶点不可达而漏了
     */
    public void dfs(){
        for (int i = 0; i < vertexes.size(); i++) {
            if (!isVisited[i]) {
                // 当前顶点遍历完成后，以下一个未访问的顶点作为起始顶点
                // 回溯
                dfs(isVisited, i);
            }
        }
    }

    /**
     * 图遍历--广度优先搜索算法
     * @param isVisited 标记顶点访问以否
     * @param index     顶点下标
     */
    private void bfs(boolean[] isVisited, int index) {
        // 创建一个队列保存已经访问过的顶点的下标
        LinkedList<Integer> queue = new LinkedList<>();
        // 输出当前顶点
        String vertex = vertexes.get(index);
        System.out.print(vertex + "->");
        // 把当前顶点入队
        queue.addLast(index);
        /*   C -------B---------E
         *    \      / \
         *     \    /   \
         *      \  /     \
         *       \/       D
         *       A
         * */
        //队列不为空输出队头顶点的所有邻接节点
        while (!queue.isEmpty()) {
            // 队头顶点出队
            int u = queue.removeFirst();
            // 获取队头顶点的邻接节点
            int w = getNeighborVertex(u);
            // 邻接节点存在
            while (w != -1) {
                // 邻接顶点未被访问过
                if (!isVisited[w]) {
                    // 输出
                    System.out.print(queue.get(w) + "->");
                    // 入队
                    queue.addLast(w);
                }
                // 获取下一个邻接节点
                w = getNextNeighbor(index, w);
            }
        }
    }

    /**
     * 广度优先搜索算法，每个顶点都进行广度优先搜索算法，防止漏访问
     */
    public void bfs(){
        for (int i = 0; i < vertexes.size(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }
}
