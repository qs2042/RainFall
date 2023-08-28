package com.qing.erp.common.algorithm;

import java.util.Arrays;

// 你能帮我用java8写一个“插入排序”吗, 并且将执行流程都作为注释写进去, 我想用来学习
public class Sort {
    // 冒泡排序
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        // 外层循环控制每轮比较的次数, 共需要比较n-1轮
        for (int i = 0; i < n - 1; i++) {
            // 内层循环控制每轮比较的元素, 每轮比较需要比较n-i-1个元素
            for (int j = 0; j < n - i - 1; j++) {
                // 如果前一个元素比后一个元素大, 就交换它们的位置
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 插入排序
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        // 外层循环控制插入的元素, 从第2个元素开始插入
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            // 内层循环控制插入的位置, 如果前一个元素比插入元素大, 就将前一个元素后移
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            // 插入元素到正确的位置
            arr[j + 1] = key;
        }
    }

    // 插入排序
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        // 外层循环控制每轮选择的元素, 共需要选择n-1个元素
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // 内层循环控制在未排序部分中选择最小的元素
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 将选择的元素放到已排序部分的末尾
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 快速排序
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 将数组划分为两个部分, 并返回划分点的下标
            int pivotIndex = partition(arr, low, high);
            // 对划分点左边的部分进行快速排序
            quickSort(arr, low, pivotIndex - 1);
            // 对划分点右边的部分进行快速排序
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];  // 选择最后一个元素作为划分点
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // 将小于划分点的元素放到划分点左边
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // 将划分点放到正确的位置上
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // 归并排序
    public static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            // 对左半部分进行归并排序
            mergeSort(arr, low, mid);
            // 对右半部分进行归并排序
            mergeSort(arr, mid + 1, high);
            // 合并左右两部分
            merge(arr, low, mid, high);
        }
    }

    public static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {
            // 比较左右两部分的元素, 将较小的元素放入临时数组中
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 将左半部分剩余的元素放入临时数组中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 将右半部分剩余的元素放入临时数组中
        while (j <= high) {
            temp[k++] = arr[j++];
        }
        // 将临时数组中的元素复制回原数组中
        for (int p = 0; p < temp.length; p++) {
            arr[low + p] = temp[p];
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 3, 1};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));

        insertionSort(arr);
        System.out.println(Arrays.toString(arr));

        selectionSort(arr);
        System.out.println(Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
