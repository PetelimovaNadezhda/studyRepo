package com.example.sortAlgorithm;

//https://hsto.org/getpro/habr/post_images/dce/d5e/54d/dced5e54d0db3424d95cffbb6dcbe9f5.gif
public class InsertionSort {

    public static int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int keyIndex = i;
            //for (int j = i; j - 1 >= 0 && array[j - 1] > array[j]; j = j - 1) {
            for (int j = i; j > 0 && array[j - 1] > key; j--) {
                //the array on the left is always sorted
                array[j] = array[j - 1];
                keyIndex = j - 1;
            }
            array[keyIndex] = key;
        }
        return array;
    }
}
