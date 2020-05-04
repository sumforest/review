package com.sen.review.algorithm.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @Author: Sen
 * @Date: 2020/5/3 13:47
 * @Description: 贪心算法最佳实践--集合覆盖问题
 * 思想：
 * 1.每次从电台中选择可以覆盖最多未覆盖的地区的电台
 * 2.从未覆盖地区中移除以覆盖地区
 * 3.循环操作直到为覆盖地区集合为空
 * 注意：贪心算法的结果不一定是最优解，但是和最优解接近
 */
public class GreedyAlgorithm {

    public static void main(String[] args) {
        // 电台
        Map<String, HashSet<String>> broadcasts = new HashMap<>(20);
        HashSet<String> broadcast1 = new HashSet<>();
        broadcast1.add("北京");
        broadcast1.add("上海");
        broadcast1.add("天津");
        HashSet<String> broadcast2 = new HashSet<>();
        broadcast2.add("广州");
        broadcast2.add("北京");
        broadcast2.add("深圳");
        HashSet<String> broadcast3 = new HashSet<>();
        broadcast3.add("成都");
        broadcast3.add("上海");
        broadcast3.add("杭州");
        HashSet<String> broadcast4 = new HashSet<>();
        broadcast4.add("上海");
        broadcast4.add("天津");
        HashSet<String> broadcast5 = new HashSet<>();
        broadcast5.add("杭州");
        broadcast5.add("大连");
        broadcasts.put("k1", broadcast1);
        broadcasts.put("k2", broadcast2);
        broadcasts.put("k3", broadcast3);
        broadcasts.put("k4", broadcast4);
        broadcasts.put("k5", broadcast5);
        // 未覆盖地区
        HashSet<String> uncoverAreas = new HashSet<>();
        uncoverAreas.add("北京");
        uncoverAreas.add("上海");
        uncoverAreas.add("天津");
        uncoverAreas.add("广州");
        uncoverAreas.add("深圳");
        uncoverAreas.add("成都");
        uncoverAreas.add("杭州");
        uncoverAreas.add("大连");
        List<String> result = greedy(broadcasts, uncoverAreas);
        System.out.println(result);
    }

    /**
     * 贪心算法求解选择最少电台覆盖所有地区
     * @param broadcasts 电台集合
     * @param uncoverAreas 未覆盖地区
     * @return 电台集合
     */
    private static List<String> greedy(Map<String, HashSet<String>> broadcasts
            , HashSet<String> uncoverAreas) {
        List<String> selections = new ArrayList<>();
        // 记录当前覆盖最多地区电台的key,默认为空
        String maxKey = null;
        // 记录电台覆盖地区和未覆盖地区的交集
        HashSet<String> temp = new HashSet<>();
        // 当存在未覆盖地区
        while (!uncoverAreas.isEmpty()) {
            // 遍历电台,寻找覆盖最大未覆盖地区的电台
            for (String k : broadcasts.keySet()) {
                // 把当前电台所覆盖的地区赋值给临时集合
                temp.addAll(broadcasts.get(k));
                // 获取当前电台和未覆盖地区的交集
                temp.retainAll(uncoverAreas);
                // 获取电台的最大交集--覆盖最多未覆盖地区的电台
                HashSet<String> maxBroadcast;
                if (maxKey != null) {
                    maxBroadcast = broadcasts.get(maxKey);
                    maxBroadcast.retainAll(uncoverAreas);
                }else {
                    maxBroadcast = new HashSet<>();
                }
                // 当前电台覆盖地区比原先假定的电台覆盖地区多，当前电台作为最多覆盖地区电台
                if (maxKey == null || temp.size() > maxBroadcast.size()) {
                    maxKey = k;
                }
                // 清空临时集合
                temp.clear();
            }
            // 把当前最大地区电台记录到集合中
            selections.add(maxKey);
            // 从未覆盖地区中删除以覆盖地区
            uncoverAreas.removeAll(broadcasts.get(maxKey));
        }

        return selections;
    }
}
