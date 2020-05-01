package com.sen.review.algorithm.data.structure.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Sen
 * @Date: 2020/4/30
 * @Description: 赫夫曼编码
 */
public class HuffmanCode {

    /**
     * 用于存放字符--出现次数
     */
    private static Map<Byte, String> HUFFMAN_CODE = new HashMap<>();

    /**
     * 标记解压时最后一位是否需要补位，false--不补，true--补位
     */
    private static boolean lastFlag = false;

    public static void main(String[] args) {
        // i like like like java do you like a java iaa
        String message = "i like like like java do you like a java";
        byte[] bytes = message.getBytes();
        // List<Node> nodes = countChar(bytes);
        // Node root = createHuffmanTree(nodes);
        // 测试代码
        // root.preOrder();
        // createHuffmanCode(root);
        // HUFFMAN_CODE.forEach((k,v)->{
        //     System.out.println(k+": " + v);
        // });
        // byte[] encode = encode(bytes);
        // System.out.println(encode.length);

        /*byte[] encode = zip(bytes);
        byte[] decode = decode(encode);
        System.out.println("解压后的结果：" + new String(decode));*/

        // 压缩文件
        // zipFile("ASCII.gif", "ASCII.zip");
        unzipFile("ASCII.zip","ascii2.gif");
    }

    private static class Node implements Comparable<Node> {

        /**
         * 记录每个节点对用的字符的ASCII码
         */
        private final Byte data;

        /**
         * 权值--字符出现的次数
         */
        private final int weight;

        private Node left;

        private Node right;

        public Node(Byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }

        /**
         * 前序遍历
         */
        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    /**
     * 统计字符出现的次数
     *
     * @param data 进行字符对应的字节数组
     * @return 返回用于构建赫夫曼树的节点
     */
    private static List<Node> countChar(byte[] data) {
        Map<Byte, Integer> temp = new HashMap<>(data.length);
        for (byte ascii : data) {
            temp.merge(ascii, 1, Integer::sum);
        }
        // 遍历map返回用于构建赫夫曼树的节点
        List<Node> nodes = new ArrayList<>();
        temp.forEach((k, v) -> nodes.add(new Node(k, v)));
        return nodes;
    }

    /**
     * 构建赫夫曼树
     *
     * @param nodes 节点
     * @return 赫夫曼树根节点
     */
    private static Node createHuffmanTree(List<Node> nodes) {
        // 当集合大小 > 1进行构建二叉树
        while (nodes.size() > 1) {
            // 先对集合进行升序排序
            Collections.sort(nodes);
            // 取出两个最小的元素
            Node left = nodes.remove(0);
            Node right = nodes.remove(0);
            // 构建二叉树
            Node parent = new Node(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            // 新构建二叉树根节点加入集合
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * 创建赫夫曼编码表
     *
     * @param root 赫夫曼树根节点
     */
    private static void createHuffmanCode(Node root) {
        if (root == null) {
            return;
        }
        // 向左拼接
        appendPath(new StringBuilder(), root.left, "0");
        // 向右拼接
        appendPath(new StringBuilder(), root.right, "1");
    }

    /**
     * 拼接每个节点的路径
     *
     * @param builder 用于拼接
     * @param node    开始节点
     * @param code    向左路径为“0”，向右为“1”
     */
    private static void appendPath(StringBuilder builder, Node node, String code) {
        if (node != null) {
            builder = new StringBuilder(builder);
            // 先拼接当前路径
            builder.append(code);
            // 处理非叶子节点
            if (node.data == null) {
                // 递归向左拼接
                appendPath(builder, node.left, "0");
                appendPath(builder, node.right, "1");
            }
            // 遍历到了叶子节点，将路径写入Map中
            else {
                HUFFMAN_CODE.put(node.data, builder.toString());
            }
        }
    }

    /**
     * 使用赫夫曼表进行编码
     *
     * @param data 原始数据
     * @return 编码后字节数组
     */
    private static byte[] encode(byte[] data) {
        StringBuilder builder = new StringBuilder();
        // 用原数据作为key获取赫夫曼编码，以此进行编码
        for (byte datum : data) {
            builder.append(HUFFMAN_CODE.get(datum));
        }
        // 确定数组的长度
        String strDatum = builder.toString();
        int length;
        // %8 因为一个字节存储8bit二进制数字
        if (strDatum.length() % 8 == 0) {
            length = strDatum.length() / 8;
            lastFlag = true;
        } else {
            // 需要额外一个字节空间进行存储不足8bit的数据
            length = strDatum.length() / 8 + 1;
        }
        byte[] encode = new byte[length];
        // 记录字符串操作位置
        int index = 0;
        // 填充数组
        for (int i = 0; i < encode.length; i++) {
            if (index + 8 > strDatum.length()) {
                encode[i] = (byte) Integer.parseUnsignedInt(strDatum.substring(index), 2);
            } else {
                // 以8位字符串为一组进行切割
                encode[i] = (byte) Integer.parseUnsignedInt(strDatum.substring(index, index + 8), 2);
            }
            index += 8;
        }
        return encode;
    }

    /**
     * 使用赫夫曼编码表进行节码
     *
     * @param encode 使用赫夫曼编码字节数组
     * @return 解码后的字节数组
     */
    private static byte[] decode(byte[] encode) {
        StringBuilder builder = new StringBuilder();
        // 拼接赫夫曼编码后的二进制数字成二进制字符串
        for (int i = 0; i < encode.length; i++) {
            String code;
            if (i == encode.length - 1) {
                // 更具最后个元素存放是否恰好够8bit来确定是否进行补位操作，不然会造成数据错误
                if (lastFlag) {
                    code = toBinaryString(true, encode[i]);
                } else {
                    code = toBinaryString(false, encode[i]);
                }
            } else {
                code = toBinaryString(true, encode[i]);
            }
            builder.append(code);
        }
        String binaryStr = builder.toString();
        // 将编码表反转，由 字符->二进制字符串 反转成 二进制字符串-> 字符
        Map<String, Byte> reverse = new HashMap<>();
        HUFFMAN_CODE.forEach((k, v) -> reverse.put(v, k));


        List<Byte> tempDatum = new ArrayList<>();
        // 将字符串进行解码 110101000
        for (int i = 0; i < binaryStr.length(); ) {
            // 记录字符串截取的位置
            int count = 0;
            // 匹配成功后，改为false，结束当前匹配
            boolean flag = true;
            while (flag && i + count <= binaryStr.length()) {
                Byte data = reverse.get(binaryStr.substring(i, i + count));
                // 扫描赫夫曼编码
                if (data == null) {
                    count++;
                } else {
                    // 赫夫曼编码对应的数据
                    tempDatum.add(data);
                    flag = false;
                }
            }
            // 更新i
            i += count;
        }

        // 将集合装成数组
        byte[] result = new byte[tempDatum.size()];
        for (int i = 0; i < tempDatum.size(); i++) {
            result[i] = tempDatum.get(i);
        }
        return result;
    }

    /**
     * 将byte类型装成二进制字符串
     *
     * @param flag  补位标记，true--补位，false--不补位
     * @param datum 字节类型数组
     * @return 二进制字符串
     */
    private static String toBinaryString(boolean flag, byte datum) {
        int temp = datum;
        if (flag) {
            /*
             * 256 -- 1 0000 0000
             * 进行或位运算原因：计算机对于byte类型二进制整数如：28 -- 1 1100 不会进行补足8bit，而生成赫夫曼编码时
             * 28 -- 0001 1100 以8bit的形式存储的，不补位就会导致就导致赫夫曼编码错误。
             *
             * */
            temp |= 256;
        }
        String strBinary = Integer.toBinaryString(temp);
        if (strBinary.length() > 8) {
            // 当datum = 负数那么字符串长度 = 32位，需要经行截取
            strBinary = strBinary.substring(strBinary.length() - 8);
        }
        return strBinary;
    }

    /**
     * 使用赫夫曼编码压缩(封装了构建赫夫曼树、创建赫夫曼编码表过程)
     *
     * @param datum 字节数组
     * @return 压缩后的字节数组
     */
    private static byte[] zip(byte[] datum) {
        List<Node> nodes = countChar(datum);
        Node root = createHuffmanTree(nodes);
        createHuffmanCode(root);
        return encode(datum);
    }

    /**
     * 压缩文件
     *
     * @param source 源文件路径
     * @param target 压缩后存放路径
     */
    private static void zipFile(String source, String target) {

        try (
                FileInputStream inputStream = new FileInputStream(new File(source));
                FileOutputStream outputStream = new FileOutputStream(new File(target));
                ObjectOutputStream oos = new ObjectOutputStream(outputStream)
        ) {
            // 创建存储数据的中间数组
            byte[] datum = new byte[inputStream.available()];
            // 一次写入中间数组
            inputStream.read(datum);
            // 进行压缩
            byte[] zip = zip(datum);
            // 把压缩后的内容写回磁盘
            oos.writeObject(zip);
            // 把赫夫曼编码写进磁盘
            oos.writeObject(HUFFMAN_CODE);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    /**
     * 解压文件
     *
     * @param source 源文件路径
     * @param target 压缩后存放路径
     */
    private static void unzipFile(String source, String target) {
        try (
                FileInputStream inputStream = new FileInputStream(new File(source));
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                FileOutputStream outputStream = new FileOutputStream(new File(target))
        ) {
            // 从源文件中读取压缩后的字节数组
            byte[] datum = (byte[]) ois.readObject();
            // 从源文件中读出赫夫曼编码表
            HUFFMAN_CODE = (Map<Byte, String>) ois.readObject();
            // 进行解压
            byte[] decode = decode(datum);
            // 把解解压后的数据写回硬盘
            outputStream.write(decode);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
