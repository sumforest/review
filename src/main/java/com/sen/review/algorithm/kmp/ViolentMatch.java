package com.sen.review.algorithm.kmp;

/**
 * @Author: Sen
 * @Date: 2020/5/2 18:39
 * @Description: 暴力匹配字符串
 */
public class ViolentMatch {

    public static void main(String[] args) {
        String str1 = "硅硅谷尚硅谷 你 尚硅尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        System.out.println(violentlyMatch(str1, str2));
    }

    /**
     * 暴力匹配字符串
     *
     * @param str    字符串
     * @param subStr 子字符串
     * @return 匹配成功返回字串所在字符串的下标，else return -1
     */
    private static int violentlyMatch(String str, String subStr) {
        /*
        String str1 = "硅硅谷尚硅谷 你 尚硅尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        */
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == subStr.charAt(j)) {
                j++;
            } else {
                // 从下一个位置开始匹配
                i = i - j;
                j = 0;
            }
            if (j == subStr.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
