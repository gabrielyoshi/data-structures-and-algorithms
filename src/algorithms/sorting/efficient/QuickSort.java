package algorithms.sorting.efficient;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * QuickSort implementation using the CLRS Lomuto partition scheme.
 *
 * Pivot strategies:
 * - LAST_ELEMENT: standard CLRS implementation
 * - RANDOM: randomly selects a pivot before partitioning
 *
 * Average runtime: O(n log n)
 * Worst-case runtime: O(n^2)
 * Average space: O(log n)
 * Worst-case space: O(n)
 *
 * QuickSort is in-place and not stable.
 */
public final class QuickSort {

    /**
     * Available pivot-selection strategies.
     */
    public enum PivotStrategy {
        LAST_ELEMENT,
        RANDOM
    }

    private QuickSort() {
        // Prevent creation of utility-class objects.
    }

    /**
     * Sorts using the standard CLRS last-element pivot.
     */
    public static void sort(int[] array) {
        sort(array, PivotStrategy.LAST_ELEMENT);
    }

    /**
     * Sorts using the selected pivot strategy.
     *
     * @param array    array to sort
     * @param strategy pivot-selection strategy
     */
    public static void sort(int[] array, PivotStrategy strategy) {
        if (array == null || array.length < 2) {
            return;
        }

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Pivot strategy cannot be null.");
        }

        quickSort(array, 0, array.length - 1, strategy);
    }

    /**
     * Recursively sorts array[low...high].
     */
    private static void quickSort(
            int[] array,
            int low,
            int high,
            PivotStrategy strategy) {

        // Base case: zero or one element.
        if (low >= high) {
            return;
        }

        int pivotIndex;

        if (strategy == PivotStrategy.RANDOM) {
            pivotIndex = randomizedPartition(array, low, high);
        } else {
            pivotIndex = partition(array, low, high);
        }

        quickSort(array, low, pivotIndex - 1, strategy);
        quickSort(array, pivotIndex + 1, high, strategy);
    }

    /**
     * Randomly selects a pivot, moves it to array[high],
     * and applies the standard CLRS partition method.
     */
    private static int randomizedPartition(
            int[] array,
            int low,
            int high) {

        int randomPivotIndex =
                ThreadLocalRandom.current().nextInt(low, high + 1);

        // Standard partition expects the pivot at array[high].
        swap(array, randomPivotIndex, high);

        return partition(array, low, high);
    }

    /**
     * CLRS Lomuto partition method.
     *
     * Loop invariant:
     *
     * array[low...boundary] contains values <= pivot
     * array[boundary + 1...scanner - 1] contains values > pivot
     * array[scanner...high - 1] contains unexamined values
     * array[high] contains the pivot
     *
     * @return the pivot's final sorted position
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int boundary = low - 1;

        for (int scanner = low; scanner < high; scanner++) {
            if (array[scanner] <= pivot) {
                boundary++;
                swap(array, boundary, scanner);
            }
        }

        swap(array, boundary + 1, high);

        return boundary + 1;
    }

    /**
     * Exchanges two array elements.
     */
    private static void swap(int[] array, int first, int second) {
        int temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }

    /**
     * Demonstrates both pivot strategies.
     */
    public static void main(String[] args) {
        int[] standard = {2, 8, 7, 1, 3, 5, 6, 4};
        int[] randomized = standard.clone();

        QuickSort.sort(standard);

        QuickSort.sort(
                randomized,
                PivotStrategy.RANDOM);

        System.out.println(
                "Standard:   " + Arrays.toString(standard));

        System.out.println(
                "Randomized: " + Arrays.toString(randomized));
    }
}