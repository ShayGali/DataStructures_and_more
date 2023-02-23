package sorting_algorithms;


import java.util.Arrays;

/**
 * @see <a href="https://www.cs.usfca.edu/~galles/visualization/ComparisonSort.html">visuals the algorithem</a>
 */
public class Algorithms {

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    interface SortAlgorithm {
        void sort(int[] arr);
    }

    /**
     * Bubble sort is a sorting algorithm that repeatedly swaps adjacent elements
     * in an array if they are in the wrong order, until the entire array is sorted.
     */
    static class BubbleSort implements SortAlgorithm {
        public void sort(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) // take the biggest to the end
                        swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * Insertion sort is a sorting algorithm that builds the final sorted array one item at a time
     * by repeatedly inserting a selected unsorted element into its correct position in the sorted portion of the array.
     */
    static class InsertionSort implements SortAlgorithm {
        public void sort(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                int j = i - 1;
                int current = arr[i];
                while (j >= 0 && current < arr[j]) { // take the "current" to the right position
                    arr[j + 1] = current;
                    j--;
                }
                arr[j + 1] = current;
            }
        }
    }

    static class ShellSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {
            for (int gap = arr.length / 2; gap > 0; gap /= 2) {
                for (int i = 1; i < arr.length; i++) {
                    int j = i;
                    while (j >= gap && arr[j] < (arr[j - gap])) {
                        swap(arr, j, j - gap);
                        j -= gap;
                    }
                }
            }
        }
    }

    /**
     * Selection sort works by finding the smallest element from the unsorted part of the array
     * and swapping it with the first unsorted element until the entire array is sorted.
     */
    static class SelectionSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {
            int minIndex;
            for (int i = 0; i < arr.length; i++) {
                minIndex = i;
                for (int j = i + 1; j < arr.length; j++) { // find the min element of the subarray, from i+1 to arr.length
                    if (arr[j] < arr[minIndex]) minIndex = j;
                }
                if (minIndex != i) swap(arr, i, minIndex);
            }
        }
    }

    /**
     * Merge Sort is a divide-and-conquer sorting algorithm that recursively divides the input array into two halves,
     * sorts each half, and then merges them back together in order to produce a sorted array.
     */
    static class MergeSort implements SortAlgorithm {

        @Override
        public void sort(int[] arr) {
            System.arraycopy(mergeSort(arr), 0, arr, 0, arr.length); // for copping the return array to the input array
        }

        //THE REAL SORT
        private int[] mergeSort(int[] arr) {
            if (arr.length < 2)
                return arr;

            // split the array to two half
            int[] leftHalf = Arrays.copyOfRange(arr, 0, arr.length / 2);
            int[] rightHalf = Arrays.copyOfRange(arr, arr.length / 2, arr.length); // if the arr.length is odd, it will be +1 (rightHalf.length=leftHalf.length+1)
            return merge(mergeSort(leftHalf), mergeSort(rightHalf));
        }

        /**
         * take two sorted array and return merge sorted array
         *
         * @param left  sorted array
         * @param right sorted array
         * @return merge sorted array
         */
        private static int[] merge(int[] left, int[] right) {
            int[] res = new int[left.length + right.length];

            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] <= right[j]) res[k++] = left[i++]; // take the min(left[i], right[j])
                else res[k++] = right[j++];
            }
            while (i < left.length) { // if we have gone through all the elements of right
                res[k++] = left[i++];
            }
            while (j < right.length) { // if we have gone through all the elements of left
                res[k++] = right[j++];
            }
            return res;
        }
    }

    /**
     * QuickSort is a fast and efficient sorting algorithm that uses a divide-and-conquer approach
     * to recursively partition an array or list into smaller sub-arrays or sub-lists,
     * and sort them in place by selecting a pivot element, moving smaller elements to the left,
     * and larger elements to the right of the pivot.
     */
    static class QuickSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {
            quickSort(arr, 0, arr.length - 1);
        }

        void quickSort(int[] arr, int left, int right) {
            if (left >= right)
                return;
            int pivotIndex = partition(arr, left, right); // dived the array to two parts by the pivot and, get the pivot index
            quickSort(arr, left, pivotIndex - 1); // sort to the left of the pivot
            quickSort(arr, pivotIndex + 1, right);// sort to the right of the pivot
        }

        int partition(int[] arr, int left, int right) {
            int pivot = arr[right]; //chose the pivot
            int partitionIndex = left;
            for (int i = left; i < right; i++) { // put the elements that are smaller that the pivot in the left size of the array and the bigger element in the right size
                if (arr[i] < pivot) {
                    swap(arr, i, partitionIndex);
                    partitionIndex++;
                }
            }
            swap(arr, right, partitionIndex); // put the pivot in the "middle" - in the right position.
            return partitionIndex; // return the index of the pivot. (On his left there will be elements smaller than him and on his right there will be elements larger than him (not in order))
        }

    }


    static class BucketSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {

        }
    }

    /**
     * CountingSort is an efficient non-comparison sorting algorithm that sorts an array or list of integers by counting the number
     * of occurrences of each element, and then using this information to determine the position of each element in the sorted output.
     * <br><br>
     * <b>Limitation:</b>
     * <ol>
     *   <li>CountingSort can only be used to sort arrays or lists of integers.</li>
     *   <li>CountingSort can only be used to sort arrays with element in range.</li>
     *   <li>CountingSort is most efficient when the range of input values is not too large, otherwise it may require a significant amount of memory to store the frequency counts.</li>
     *   <li>CountingSort requires additional memory to store the frequency counts, which can be a disadvantage for large input data.</li>
     *   <li>Stability: CountingSort is not a stable sorting algorithm, meaning that it may not preserve the relative order of equal elements in the original input data.</li>
     *   <li>Skewed Data: CountingSort may become inefficient when the input data is skewed, meaning that some values occur much more frequently than others. This can result in a large number of empty counts, leading to memory wastage and reduced performance.</li>
     * </ol>
     */
    static class CountingSort implements SortAlgorithm {
        public static final int MAX_VALUE = 50;
        public static final int MIN_VALUE = -50;

        @Override
        public void sort(int[] arr) {
            int[] counter = new int[MAX_VALUE - MIN_VALUE + 1];

            for (int num : arr) {
                counter[num - MIN_VALUE]++;
            }
            for (int i = 1; i < counter.length; i++) {
                counter[i] += counter[i - 1];
            }

            int[] temp = new int[arr.length];

            for (int num : arr) {
                temp[--counter[num - MIN_VALUE]] = num;
            }
            System.arraycopy(temp, 0, arr, 0, arr.length);
        }
    }

    /**
     * Radix Sort is a sorting algorithm that sorts data by grouping elements based on their digits
     * and then sorting them based on each digit from least significant to most significant.
     * <br>
     *
     * @see <a href="https://www.cs.usfca.edu/~galles/visualization/RadixSort.html">visuals the algorithem</a>
     */

    static class RadixSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {
            // get the "longest" number in the array
            int maxDigits = 0;
            for (int k : arr) {
                maxDigits = Math.max(maxDigits, getNumberOfDigits(k));
            }

            // sort them by there digits
            for (int i = 0; i < maxDigits; i++) {
                int[] counter = new int[10];
                for (int n : arr) {
                    counter[getDigit(n, i)]++;
                }

                for (int j = 1; j < counter.length; j++) {
                    counter[j] += counter[j - 1];
                }

                int[] temp = new int[arr.length];
                for (int j = arr.length - 1; j >= 0; j--) {
                    temp[--counter[getDigit(arr[j], i)]] = arr[j];
                }
                System.arraycopy(temp, 0, arr, 0, arr.length);
            }
        }

        /**
         * @param n non-negative number
         * @return length of the number
         */
        private int getNumberOfDigits(int n) {
            return (int) Math.log10(n) + 1;
        }

        /**
         * get the digit from the index in a number
         *
         * @param n     non-negative number
         * @param place index
         * @return n[place]
         */
        private int getDigit(int n, int place) {
            return (int) (n / Math.pow(10, place) % 10);
        }
    }

    /**
     * Shuffle the array until it is sorted
     */
    static class ShuffleSort implements SortAlgorithm {
        @Override
        public void sort(int[] arr) {
            int count = 0;
            while (!Tests.isSorted(arr)) {
                count++;
                int m = arr.length;
                int i;
                while (m > 0) {
                    i = (int) (Math.random() * m--);
                    swap(arr, i, m);
                }
            }
            System.out.println(count);
        }
    }
}

