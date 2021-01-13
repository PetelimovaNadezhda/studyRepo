package com.company;

import com.company.sortAlgorithm.BubbleSort;
import com.company.sortAlgorithm.CocktailSort;
import com.company.sortAlgorithm.InsertionSort;
import com.company.sortAlgorithm.ShellSort;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Main {

    private static final int RUN_COUNT = 5;


    public static void main(String[] args) {

        int[] array = IntStream.generate(() -> new Random().nextInt(10000)).limit(5000).toArray();

        System.out.println("Not sorted    " + Arrays.toString(array));
        System.out.println("Bubble        " + Arrays.toString(BubbleSort.sort(Arrays.copyOf(array, array.length))));
        System.out.println("Cocktail      " + Arrays.toString(CocktailSort.sort(Arrays.copyOf(array, array.length))));
        System.out.println("InsertionSort " + Arrays.toString(InsertionSort.sort(Arrays.copyOf(array, array.length))));
        System.out.println("ShellSort     " + Arrays.toString(ShellSort.sort(Arrays.copyOf(array, array.length))));

        if (checkSort(BubbleSort.sort(Arrays.copyOf(array, array.length))) ||
                checkSort(CocktailSort.sort(Arrays.copyOf(array, array.length))) ||
                checkSort(InsertionSort.sort(Arrays.copyOf(array, array.length))) ||
                checkSort(ShellSort.sort(Arrays.copyOf(array, array.length)))) {
            throw new IllegalStateException();
        }

        measure("Bubble", () -> BubbleSort.sort(Arrays.copyOf(array, array.length)));
        measure("Cocktail", () -> CocktailSort.sort(Arrays.copyOf(array, array.length)));
        measure("InsertionSort", () -> InsertionSort.sort(Arrays.copyOf(array, array.length)));
        measure("ShellSort", () -> ShellSort.sort(Arrays.copyOf(array, array.length)));
    }

    static boolean checkSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Used by the measure method to determine how long a Supplier takes to
     * return a result.
     *
     * @param <T>      The type of the result provided by the Supplier
     * @param label    Description of what's being measured
     * @param supplier The Supplier to measure execution time of
     * @return
     */
    static <T> long measureOneRun(String label, Supplier<T> supplier) {
        long startTime = System.nanoTime();
        T result = supplier.get();
        long endTime = System.nanoTime();
        return (endTime - startTime + 500_000L) / 1_000_000L;
    }

    /**
     * Repeatedly generate results using a Supplier to eliminate some of the
     * issues of running a micro-benchmark.
     *
     * @param <T>      The type of result generated by the Supplier
     * @param label    Description of what's being measured
     * @param supplier The Supplier to measure execution time of
     * @return The last execution time of the Supplier code
     */
    static <T> long measure(String label, Supplier<T> supplier) {
        long result = 0;

        for (int i = 0; i < RUN_COUNT; i++)
            result += measureOneRun(label, supplier);

        System.out.printf("%s took %dms%n", label, result / RUN_COUNT);
        return result;
    }

}