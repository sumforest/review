package com.sen.review.algorithm.data.structure.hash.table;

import java.util.Scanner;

/**
 * @Author: Sen
 * @Date: 2020/4/29
 * @Description: 实现哈希表（数组+链表实现）
 */
public class HashTableDemo {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable(7);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("a:添加新员工");
            System.out.println("p:打印哈希表");
            System.out.println("f:查找员工");
            System.out.println("d:删除员工");
            System.out.println("e:退出程序");
            String command = scanner.next();
            switch (command) {
                case "a":
                    System.out.println("员工id：");
                    int id = scanner.nextInt();
                    System.out.println("员工姓名：");
                    String name = scanner.next();
                    hashTable.add(new Employee(id, name));
                    break;
                case "p":
                    hashTable.print();
                    break;
                case "f":
                    System.out.println("输入员工id：");
                    id = scanner.nextInt();
                    System.out.println(hashTable.find(id));
                    break;
                case "d":
                    System.out.println("输入员工id：");
                    id = scanner.nextInt();
                    hashTable.delete(id);
                    break;
                case "e":
                    scanner.close();
                    return;
                default:
            }
        }
    }

    /**
     * 员工类--链表节点
     */
    private static class Employee{

        private final int id;

        private final String name;

        /**
         * 指向下一个节点指针
         */
        private Employee next;

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * 链表类--维护{@link Employee}
     */
    private static class List{

        /**
         * 头节点--指向第一个员工，默认值为null即链表为空
         */
        private Employee head = null;

        /**
         * 添加节点,默认添加到链表尾
         * @param node 员工
         */
        private void add(Employee node) {
           if (find(node.id)!=null){
               throw new IllegalArgumentException("该id已存在");
           }
            // 头节点为空直接添加到头节点
            if (head == null) {
                head = node;
                return;
            }
            // 否则添加到链表末位
            Employee cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }

        /**
         * 查找节点，通过id比较
         */
        private Employee find(int id) {
            Employee cur = head;
            while (cur != null) {
                if (cur.id ==id) {
                    return cur;
                }
                cur = cur.next;
            }
            return null;
        }

        /**
         * 删除节点，通过id判断是否为同一个
         * @param id 被删除节点
         */
        private void delete(int id) {
            Employee cur = head;
            // 删除节点是头节点直接删除
            if (head.id == id) {
                head = head.next;
                return;
            }
            // 查询当前节点的前一个节点
            while (cur.next != null) {
                if (cur.next.id == id) {
                    // 删除节点
                    cur.next = cur.next.next;
                    return;
                }
                cur = cur.next;
            }
            System.err.println("Current node doesn't present!");
        }

        private void print() {
            Employee cur = head;
            while (cur != null) {
                System.out.print(cur + ", ");
                cur = cur.next;
            }
            System.out.println();
        }
    }

    /**
     * 哈希表
     */
    private static class HashTable{

        /**
         * 存储数据的链表数组
         */
        private final List[] lists;

        private final int size;

        public HashTable(int size) {
            this.size = size;
            this.lists = new List[size];
            // 初始化链表数组
            for (int i = 0; i < size; i++) {
                lists[i] = new List();
            }
        }

        /**
         * 散列函数，确定选择哪个链表存储数据
         * @param id 员工id
         * @return 链表数组下标
         */
        private int hash(int id) {
            return id % size;
        }

        public void add(Employee employee) {
            lists[hash(employee.id)].add(employee);
        }

        public void delete(int id) {
            lists[hash(id)].delete(id);
        }

        public Employee find(int id) {
            return lists[hash(id)].find(id);
        }

        public void print() {
            for (int i = 0; i < size; i++) {
                System.out.printf("第%d链表：" ,i+1);
                lists[i].print();
            }
        }
    }
}
