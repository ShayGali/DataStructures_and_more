package sorting_algorithms;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sorting_algorithms.GenericAlgorithms.*;

public class TestsGeneric {

    Integer[][] testMatrix;

    @BeforeEach
    void init(TestInfo testInfo) {
        if ((testInfo.getTags().contains("Ignore-Init"))) return;
        testMatrix = new Integer[][]{
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

    static Integer[] randomArray(int size, int min, int max) {
        Integer[] res = new Integer[size];
        Random random = new Random();
        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextInt((max - min) + 1) + min;
        }
        return res;
    }

    static Integer[] randomArray(int size) {
        return randomArray(size, -100000, 100000);
    }


    void testSort(SortAlgorithm<Integer> sortAlgorithm, boolean doubleCheck) {
        for (Integer[] arr : testMatrix) {
            sortAlgorithm.setArr(arr);
            sortAlgorithm.sort();
        }
        for (Integer[] arr : testMatrix) {
            assertTrue(isSorted(arr), () -> "Output is: " + Arrays.toString(arr));
            if (doubleCheck) assertArrayEquals(arr, Arrays.stream(arr).sorted().toArray());
        }
    }

    void testSort(SortAlgorithm<Integer> sortAlgorithm) {
        testSort(sortAlgorithm, false);
    }

    @Test
    @Tag("Slow")
    void bubbleSortTest() {
        testSort(new BubbleSort<>(null));
    }

    @Test
    @Tag("Slow")
    void InsertionSortTest() {
        testSort(new InsertionSort<>(null));
    }

    @Test
    @Tag("Slow")
    void ShellSortTest() {
        testSort(new ShellSort<>(null));
    }

    @Test
    @Tag("Slow")
    void SelectionSortTest() {
        testSort(new SelectionSort<>(null));
    }

    @Test
    void mergeSortTest() {
        testSort(new MergeSort<>(null, Integer.class), true);
    }

    @Test
    void quickSortTest() {
        testSort(new QuickSort<>(null));
    }

    @Test
    void radixSortTest() {
        for (int i = 0; i < testMatrix.length; i++) {
            for (int j = 0; j < testMatrix[i].length; j++) {
                testMatrix[i][j] = Math.abs(testMatrix[i][j]);
            }

        }
        testSort(new RadixSort(null));
    }

    @Test
    @Tag("Ignore-Init")
    void CountingSort() {
        testMatrix = new Integer[][]{
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
        testSort(new CountingSort(null));
    }

    @Test
    @Tag("Ignore-Init")
    @Timeout(value = 5)
    void shuffleSort() {
        Integer[] arr = {4, 1, 3, 2, 8, 7, 53, 41, 89, 78};
        new ShuffleSort<>(arr).sort();
        assertTrue(isSorted(arr));
    }

}
