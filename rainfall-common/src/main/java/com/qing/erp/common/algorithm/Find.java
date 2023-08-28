package com.qing.erp.common.algorithm;

import java.util.Arrays;
import java.util.List;

public class Find {

    // 顺序查找
    public static <T> int sequentialSearch(List<T> list, T key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(key)) {
                return i;
            }
        }
        return -1;
    }

    // 二分查找
    public static <T extends Comparable<? super T>> int binarySearch(List<T> list, T key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = key.compareTo(list.get(mid));

            if (compareResult < 0) {
                high = mid - 1;
            } else if (compareResult > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int index = sequentialSearch(Arrays.asList(5, 2, 7, 1, 9, 3), 7);

        int index2 = binarySearch(Arrays.asList(1, 2, 3, 5, 7, 9), 7);
    }
}



/*
TODO
图论算法：包括最短路径算法（如Dijkstra算法、Bellman-Ford算法、Floyd算法）、最小生成树算法（如Prim算法、Kruskal算法）等.
字符串算法：包括字符串匹配算法（如暴力匹配算法、KMP算法、Boyer-Moore算法）、字符串编辑距离算法（如Levenshtein距离算法）等.
数学算法：包括质数判断算法（如试除法、Miller-Rabin算法）、求最大公约数和最小公倍数的算法（如辗转相除法、欧几里得算法）、快速幂算法等.
机器学习算法：包括线性回归、逻辑回归、决策树、支持向量机、聚类算法、神经网络等.
图像处理算法：包括边缘检测、图像分割、图像去噪、图像识别等.
数据结构算法：包括链表、栈、队列、二叉树、图、哈希表等
 */