package com.sen.review.algorithm.data.structure.stack;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 栈实现综合计算器(只支持 + - * /) 中缀表达式
 * <p>
 * ASCII码数字范围： 48 - 57（十进制）
 */
public class Calculator {

    public static void main(String[] args) {
        String expression = "30+2*6/2-20";
        // 创建数栈
        MyStack numbStack = new MyStack(10);
        // 创建符号栈
        MyStack operatorStack = new MyStack(10);

        // 表达式指针
        int index = 0;
        // 用于记录数字
        String temp = "";

        // 遍历表达式
        while (index < expression.length()) {
            char ch = expression.charAt(index);
            // ch是操作符
            if (ch < 48 || ch > 57) {
                // 符号栈为空直接加入
                if (operatorStack.isEmpty()) {
                    operatorStack.push(ch);
                }
                // 符号栈不为空
                else {
                    // 比较符号栈栈顶元素和ch优先级
                    if (getPriority(operatorStack.peek()) < getPriority(ch)) {
                        // ch优先级比符号栈高，直接入符号栈
                        operatorStack.push(ch);
                    }
                    // ch优先级不比栈顶元素优先级大
                    else {
                        // 从数栈弹出栈顶元素作为被减数/被除数
                        int num1 = numbStack.pop();
                        int num2 = numbStack.pop();
                        // 从符号栈弹出符号
                        int operator = operatorStack.pop();
                        int result = count(num1, num2, operator);

                        // 结果压回数栈
                        numbStack.push(result);
                        // 把ch压入符号栈
                        operatorStack.push(ch);
                    }
                }
            }
            // ch是数字
            else {
                // 把当前的数字与上一位的数字拼接
                temp += ch;
                // ch已经是最后一位
                if (index == expression.length() - 1) {
                    numbStack.push(Integer.parseInt(temp));
                } else {
                    // 往后探一位是否为符号位
                    if (expression.charAt(index + 1) < 48 || expression.charAt(index + 1) > 57) {
                        // 若为符号位表示可以压入数栈
                        numbStack.push(Integer.parseInt(temp));
                        // 重置temp
                        temp = "";
                    }
                }
            }
            index++;
        }

        // 把数栈和符号栈依次pop进行计算
        while (!operatorStack.isEmpty()) {
            // 从数栈弹出栈顶元素作为被减数/被除数
            int num1 = numbStack.pop();
            int num2 = numbStack.pop();
            // 从符号栈弹出符号
            int operator = operatorStack.pop();
            int result = count(num1, num2, operator);
            // 结果压回数栈
            numbStack.push(result);
        }
        System.out.println("结果：" + numbStack.peek());
    }

    /**
     * 计算结果
     *
     * @param num1     操作数1
     * @param num2     操作数2
     * @param operator 操作符
     * @return 结果
     */
    private static int count(int num1, int num2, int operator) {
        int result = Integer.MIN_VALUE;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2 - num1;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num2 / num1;
                break;
            default:
        }
        return result;
    }

    /**
     * 获取符号栈优先级
     *
     * @param operator 操作符
     * @return 优先级
     */
    private static int getPriority(int operator) {
        int result;
        switch (operator) {
            case '+':
            case '-':
                result = 1;
                break;
            case '*':
            case '/':
                result = 2;
                break;
            default:
                result = -1;
        }
        return result;
    }

    private static class MyStack {

        /**
         * 栈顶标记,默认值 -1 表示空，包含栈顶元素
         */
        private int top = -1;

        private final int maxSize;

        private final int[] arr;

        public MyStack(int maxSize) {
            this.maxSize = maxSize;
            this.arr = new int[maxSize];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == this.maxSize - 1;
        }

        public int peek() {
            return arr[top];
        }

        public void push(int val) {
            if (this.isFull()) {
                throw new IllegalStateException("Stack is full!");
            }
            // 添加元素并移动指针
            arr[++top] = val;
        }

        public int pop() {
            if (this.isEmpty()) {
                throw new IllegalStateException("Stack is empty!");
            }
            return arr[top--];
        }

        /**
         * 从栈顶开始遍历
         */
        public void print() {
            if (this.isEmpty()) {
                throw new IllegalStateException("Stack is empty!");
            }
            System.out.print("[");
            for (int i = top; i >= 0; i--) {
                System.out.print(arr[i] + ",");
            }
            System.out.print("]");
        }
    }
}
