package com.sen.review.algorithm.kmp;

/**
 * @Author: Sen
 * @Date: 2020/5/2 22:30
 * @Description: KMP算法最佳实践--解决字符串匹配
 */
public class KmpAlgorithm {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] kmpNext = kmpNext(str2);
        int index = kmpSearch(str1, str2, kmpNext);
        System.out.println(index);
    }

    /**
     * 生成子字符串部分匹配表
     *
     * @param subStr 子字符串
     * @return 部分匹配表
     */
    private static int[] kmpNext(String subStr) {
        int[] table = new int[subStr.length()];
        /*
         * 下标：     0 1 2 3 4 5 6
         *           A B C D A B D
         * 部分匹配值：0 0 0 0 1 2 0
         * */
        // 由部分匹配表特点知，第一个元素的值为0
        table[0] = 0;
        // 从第二字符开始讨论部分匹配值
        for (int i = 1, j = 0; i < subStr.length(); i++) {
            // 当j > 0并且charAt(i) == subStr.charAt(j)不成立说明部分匹配值为0
            while (j > 0 && subStr.charAt(i) != subStr.charAt(j)) {
                j = table[j - 1];
            }
            if (subStr.charAt(i) == subStr.charAt(j)) {
                j++;
            }
            table[i] = j;
        }
        return table;
    }

    /**
     * KMP字符串匹配算法
     *
     * @param str     字符串
     * @param subStr  子字符串
     * @param kmpNext 子字符串的部分匹配表
     * @return 返回第一次出现子字符串的下标，不存在返回-1
     */
    private static int kmpSearch(String str, String subStr, int[] kmpNext) {
        /*
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

                   A B C D A B D
         部分匹配值：0 0 0 0 1 2 0
        */
        for (int i = 0, j = 0; i < str.length(); i++) {
            // KMP算法核心
            if (j > 0 && str.charAt(i) != subStr.charAt(j)) {
                j = kmpNext[j - 1];
            }
            if (str.charAt(i) == subStr.charAt(j)) {
                j++;
            }
            // 匹配成功
            if (j == subStr.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
