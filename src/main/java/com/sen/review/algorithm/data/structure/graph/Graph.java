package com.sen.review.algorithm.data.structure.graph;

import java.util.ArrayList;
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

    public Graph(int vertexesNumber) {
        this.vertexes = new ArrayList<>();
        this.edges = new int[vertexesNumber][vertexesNumber];
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
            System.out.print(vertexes.get(index++)+" ");
            for (int weight : edge) {
                System.out.print(weight + " ");
            }
            System.out.println();
        }
    }
}
