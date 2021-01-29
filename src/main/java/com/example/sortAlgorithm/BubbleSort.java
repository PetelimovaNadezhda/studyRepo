package com.example.sortAlgorithm;

public class BubbleSort {

    public static int[] sort(int[] array) {
        boolean isSort = false;
        while (!isSort) {
            for (int i = 0; i < array.length - 1; i++) {
                if (i == 0) {
                    isSort = true;
                }
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    isSort = false;
                }
            }
        }
        return array;
    }
}
