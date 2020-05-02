package com.sen.review.data.structure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: Sen
 * @Date: 2020/4/27
 * @Description: 逆波兰表达式
 * 中缀表达式转后缀表达式 1+((2+3)*4)-5
 * 思路:
 * 1.先把中缀表达式字符串转换成字符串集合方便操作
 * 2.将1+((2+3)*4)-5中缀表达式转换成后缀表达式1 2 3 + 4 * + 5 -
 * 3.把中缀表达式List：[1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
 * 转换成后缀表达式List:[1,2,3,+,4,*,+,5,-]
 * <p>
 * ASCII码数字范围： 48 - 57（十进制）
 */
public class ReversePolandNotation {

    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String REG = "\\d+";

    public static void main(String[] args) {
        List<String> infixExpressions = toInfixExpression("1+((2+3)*4)-5");
        List<String> suffixExpressions = toSuffixExpression(infixExpressions);
        System.out.println(counter(suffixExpressions));
    }

    /**
     * 中缀表达式字符串转中缀表达式集合
     *
     * @param expression 中缀表达式
     * @return 中缀表达式集合
     */
    private static List<String> toInfixExpression(String expression) {
        List<String> inFixExpressions = new ArrayList<>();
        // 记录字符串下标指针
        int pointer = 0;
        while (pointer < expression.length()) {
            // 字符不是表示数值直接加入集合，比较ASCII
            if (expression.charAt(pointer) < 48 || expression.charAt(pointer) > 57) {
                inFixExpressions.add(String.valueOf(expression.charAt(pointer)));
                pointer++;
            }
            // 字符为数字，考虑多位数字
            else {
                StringBuilder builder = new StringBuilder();
                while (pointer < expression.length() &&
                        (expression.charAt(pointer) >= 48 && expression.charAt(pointer) <= 57)) {
                    builder.append(expression.charAt(pointer));
                    pointer++;
                }
                inFixExpressions.add(builder.toString());
            }
        }
        return inFixExpressions;
    }

    /**
     * 中缀表达式转后缀表达式
     *
     * @param expressions 中缀表达式集合
     * @return 后缀表达式集合
     */
    private static List<String> toSuffixExpression(List<String> expressions) {
        // 创建运算符栈S1
        Stack<String> stack = new Stack<>();
        // 由于中间结果栈S2只有入栈操作没有出栈操作所以使用结合来代替，这样最终结果可以减少逆序这一步骤
        List<String> suffixExpressions = new ArrayList<>();

        expressions.forEach(ex -> {
            // 遇到一位或多位数字直接加入集合
            if (ex.matches(REG)) {
                suffixExpressions.add(ex);
            }
            // 遇到左括号，入栈
            else if (LEFT_BRACKET.equals(ex)) {
                stack.push(ex);
            }
            // 遇到右括号
            else if (RIGHT_BRACKET.equals(ex)) {
                // 从栈中一次弹出操作符并添加进集合直到遇到左括号
                while (!stack.isEmpty() && !LEFT_BRACKET.equals(stack.peek())) {
                    suffixExpressions.add(stack.pop());
                }
                // 从栈中弹出左括号
                stack.pop();
            }
            // 遇到操作符
            else {
                // 栈为空 || 栈顶元素为 "(",入栈
                if (stack.isEmpty() || LEFT_BRACKET.equals(stack.peek())) {
                    stack.push(ex);
                }
                // 比较ex 与 栈顶元素的优先级
                else {
                    while (!stack.isEmpty() && Operator.getOperatorPriority(ex)
                            <= Operator.getOperatorPriority(stack.peek())) {
                        // ex优先级不大于栈顶元素优先级时，把栈顶元素弹出添加到集合中
                        suffixExpressions.add(stack.pop());
                    }
                    // 经过调整后ex符合比栈顶元素优先级高 || 栈为空
                    stack.push(ex);
                }
            }
        });

        // 最后把栈中的所有数据添加到集合内
        while (!stack.isEmpty()) {
            suffixExpressions.add(stack.pop());
        }
        return suffixExpressions;
    }

    /**
     * 计算
     * @param suffixExpressions 后缀表达式
     * @return 表达式结果
     */
    private static int counter(List<String> suffixExpressions) {
        // 用于存储数据的栈
        Stack<Integer> stack = new Stack<>();
        for (String ex : suffixExpressions) {
            // 遇到数字入栈
            if (ex.matches(REG)) {
                stack.push(Integer.valueOf(ex));
            }
            // 遇到操作符
            else{
                // 弹出栈顶元素作为被减数/被除数
                int num2 = stack.pop();
                // 弹出次顶元素作为减数/除数
                int num1 = stack.pop();
                switch (ex) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push( num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    default:
                        break;
                }
            }
        }

        return stack.pop();
    }

    /**
     * 操作符
     */
    private static class Operator {

        /**
         * +
         */
        private static final int ADD = 1;

        /**
         * -
         */
        private static final int SUB = 1;

        /**
         * *
         */
        private static final int MUL = 2;

        /**
         * /
         */
        private static final int DIV = 2;

        /**
         * 获取操作符优先级
         *
         * @param operator 操作符
         * @return priority
         */
        private static int getOperatorPriority(String operator) {
            int priority;
            switch (operator) {
                case "+":
                    priority = ADD;
                    break;
                case "-":
                    priority = SUB;
                    break;
                case "*":
                    priority = MUL;
                    break;
                case "/":
                    priority = DIV;
                    break;
                default:
                    throw new IllegalArgumentException("操作符有误！");
            }
            return priority;
        }

    }
}
