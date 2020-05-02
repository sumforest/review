package com.sen.review.data.structure.circle.queue;

import java.util.Scanner;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 循环队列，基于数组实现（会空出一个空间用于判断队列是否已满）
 */
public class CircleQueueDemo {

    public static void main(String[] args) {

        CircleQueue circleQueue = new CircleQueue(8);
        Scanner scanner = new Scanner(System.in);
        boolean isEnd = false;

        while (!isEnd) {
            System.out.println("请输入相关指令：");
            String key = scanner.next();
            switch (key.charAt(0)) {
                case 's':
                    System.out.println(circleQueue.size());
                    break;
                case 'e':
                    System.out.println(circleQueue.isEmpty());
                    break;
                case 'f':
                    System.out.println(circleQueue.isFull());
                    break;
                case 'a':
                    try {
                        System.out.println("请输入数值，按回车键结束");
                        circleQueue.add(scanner.nextInt());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'r':
                    try {
                        System.out.println("被删除的值：" + circleQueue.remove());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'p':
                    try {
                        circleQueue.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'q':
                    isEnd = true;
                    break;
                default:
                    break;
            }
        }
    }
}

/**
 * 循环队列
 */
class CircleQueue {

    /**
     * 记录队列头的下标，默认值0，指向队列头元素
     */
    int front = 0;

    /**
     * 记录队列最后元素的下一个位置，默认值0，不包括最后一个元素
     */
    int rear = 0;

    /**
     * 存储数据数组
     */
    int[] queue;

    /**
     * 队列的最大空间
     */
    int maxSize;

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new int[maxSize];
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    /**
     * 入队
     *
     * @param val 入队值
     */
    public void add(int val) {
        // 判断队列是否满
        if (this.isFull()) {
            throw new IllegalStateException("Stack is Full!");
        }
        queue[rear] = val;
        // 更新rear指针,防止 rear 在 maxSize-1 处 +1 造成数组越界，此时应该回到0下标
        rear = (rear + 1) % maxSize;
    }

    /**
     * 出队
     */
    public int remove() {
        // 判断队列是否未空
        if (this.isEmpty()) {
            throw new IllegalStateException("Stack is Empty!");
        }
        int result = queue[front];
        // 移动front到下一个位置,同样因为是循环队列所有需要取模，在下标 = maxSize 时回到 0 下标处
        front = (front + 1) % maxSize;
        return result;
    }

    public void print() {
        if (this.isEmpty()) {
            System.err.println("Stack is Empty!");
        }
        // 从front开始，开始遍历，考虑循环队列
        System.out.println("front = " + front);
        for (int i = front; i < front + size(); i++) {
            System.out.printf("queue[%d] = %d,", i % maxSize, queue[i % maxSize]);
        }
    }
}
