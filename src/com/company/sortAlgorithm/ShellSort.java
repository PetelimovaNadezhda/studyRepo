package com.company.sortAlgorithm;

public class ShellSort {

    public static int[] sort(int[] array) {
        for (int n = array.length / 2; n > 0; n /= 2) {
            for (int i = n; i < array.length; i++) {
                for (int j = i; j - n >= 0 && array[j - n] > array[j]; j = j - n) {
                    int temp = array[j];
                    array[j] = array[j - n];
                    array[j - n] = temp;
                }
            }
        }
        return array;
    }
}
