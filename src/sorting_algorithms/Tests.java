package sorting_algorithms;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sorting_algorithms.Algorithms.*;

public class Tests {

    static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1])
                return false;
        }
        return true;
    }

    int[][] testMatrix;

    @BeforeEach
    void init(TestInfo testInfo) {
        if ((testInfo.getTags().contains("Ignore-Init"))) return;
        testMatrix = new int[][]{
                {},
                {1},
                {1, 2},
                {2, 1},
                {3, 1, 2},
                randomArray(15),
                randomArray(151),
                randomArray(781),
                randomArray(782),
                randomArray(10000),
                randomArray(25301),
        };
    }

    static int[] randomArray(int size, int min, int max) {
        int[] res = new int[size];
        Random random = new Random();
        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextInt((max - min) + 1) + min;
        }
        return res;
    }

    static int[] randomArray(int size) {
        return randomArray(size, -100000, 100000);
    }


    void testSort(SortAlgorithm sortAlgorithm, boolean doubleCheck) {
        for (int[] arr : testMatrix) {
            sortAlgorithm.sort(arr);
        }
        for (int[] arr : testMatrix) {
            assertTrue(isSorted(arr), () -> "Output is: " + Arrays.toString(arr));
            if (doubleCheck) assertArrayEquals(arr, Arrays.stream(arr).sorted().toArray());
        }
    }

    void testSort(SortAlgorithm sortAlgorithm) {
        testSort(sortAlgorithm, false);
    }

    @Test
    @Tag("Slow")
    void bubbleSortTest() {
        testSort(new BubbleSort());
    }

    @Test
    @Tag("Slow")
    void InsertionSortTest() {
        testSort(new InsertionSort());
    }

    @Test
    @Tag("Slow")
    void ShellSortTest() {
        testSort(new ShellSort());
    }

    @Test
    @Tag("Slow")
    void SelectionSortTest() {
        testSort(new SelectionSort());
    }

    @Test
    void mergeSortTest() {
        testSort(new MergeSort(), true);
    }

    @Test
    void quickSortTest() {
        testSort(new QuickSort());
    }

    @Test
    void radixSortTest() {
        for (int i = 0; i < testMatrix.length; i++) {
            for (int j = 0; j < testMatrix[i].length; j++) {
                testMatrix[i][j] = Math.abs(testMatrix[i][j]);
            }

        }
        testSort(new RadixSort());
    }

    @Test
    @Tag("Ignore-Init")
    void CountingSort() {
        testMatrix = new int[][]{
                {},
                {1},
                {1, 2},
                {2, 1},
                {3, 1, 2},
                randomArray(15, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
                randomArray(151, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
                randomArray(781, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
                randomArray(782, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
                randomArray(10000, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
                randomArray(25301, CountingSort.MIN_VALUE, CountingSort.MAX_VALUE),
        };
        testSort(new CountingSort());
    }

    @Test
    @Tag("Ignore-Init")
    @Timeout(value = 5)
    void shuffleSort() {
        int[] arr = {4, 1, 3, 2, 8, 7, 53, 41, 89, 78};
        new ShuffleSort().sort(arr);
        assertTrue(isSorted(arr));
    }

}
