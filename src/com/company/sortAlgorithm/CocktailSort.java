package com.company.sortAlgorithm;

public class CocktailSort {

    public static int[] sort(int[] array) {
        boolean isSort = false;
        int start = 0;
        int newStart = start;
        int finish = array.length - 1;
        int newFinish = finish;
        while (!isSort) {
            for (int i = start; i < finish; i++) {
                if (i == start) {
                    isSort = true;
                }
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    newFinish = i + 1;
                    isSort = false;
                }
            }
            for (int i = finish; i > start; i--) {
                if (i == finish) {
                    isSort = true;
                }
                if (array[i] < array[i - 1]) {
                    int temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                    newStart = i - 1;
                    isSort = false;
                }
            }
            start = newStart;
            finish = newFinish;
        }
        return array;
    }
}
