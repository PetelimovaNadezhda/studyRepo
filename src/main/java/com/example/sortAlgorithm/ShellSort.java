package com.example.sortAlgorithm;

public class ShellSort {

    public static int[] sort(int[] array) {
        for (int step = array.length / 2; step > 0; step /= 2) {
            for (int i = step; i < array.length; i++) {
                int key = array[i];
                int keyIndex = i;
                for (int j = i; j - step >= 0 && array[j - step] > key; j = j - step) {
                    array[j] = array[j - step];
                    keyIndex = j - step;
                }
                array[keyIndex] = key;
            }
        }
        return array;
    }
}
