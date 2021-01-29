package com.example.sortAlgorithm;

public class InsertionSort {

    public static int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++)
            for (int j = i; j > 0 && array[j - 1] > array[j]; j--) {
                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
            }
        return array;
    }
}
