package algorithms.selection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * QuickSelect
 *
 * Finds the ith-smallest element without fully sorting the array.
 *
 * The order statistic is 1-based:
 * - order 1 finds the smallest value
 * - order 2 finds the second-smallest value
 * - order n finds the largest value
 *
 * Two implementations are included for study:
 * - ITERATIVE: uses a while loop
 * - RECURSIVE: follows the CLRS RANDOMIZED-SELECT structure
 *
 * Expected runtime: Theta(n)
 * Worst-case runtime: Theta(n^2)
 *
 * Iterative auxiliary space: Theta(1)
 * Recursive expected space: Theta(log n)
 * Recursive worst-case space: Theta(n)
 *
 * Important: QuickSelect rearranges the input array.
 */
public final class QuickSelect {

    /**
     * Available QuickSelect implementations.
     */
    public enum Implementation {
        ITERATIVE,
        RECURSIVE
    }

    private QuickSelect() {
        // Prevent creation of utility-class objects.
    }

    /**
     * Uses the iterative implementation by default.
     *
     * @param array array to search
     * @param order desired 1-based order statistic
     * @return the ith-smallest value
     */
    public static int select(int[] array, int order) {
        return select(array, order, Implementation.ITERATIVE);
    }

    /**
     * Uses the requested QuickSelect implementation.
     *
     * @param array          array to search
     * @param order          desired 1-based order statistic
     * @param implementation iterative or recursive
     * @return the ith-smallest value
     */
    public static int select(
            int[] array,
            int order,
            Implementation implementation) {

        validateInput(array, order);

        if (implementation == null) {
            throw new IllegalArgumentException(
                    "Implementation cannot be null.");
        }

        // Convert the 1-based order into a 0-based target index.
        int targetIndex = order - 1;

        if (implementation == Implementation.RECURSIVE) {
            return quickSelectRecursive(
                    array,
                    0,
                    array.length - 1,
                    targetIndex);
        }

        return quickSelectIterative(array, targetIndex);
    }

    /**
     * Finds an order statistic without modifying the original array.
     *
     * Creating the clone requires Theta(n) additional space.
     */
    public static int selectCopy(
            int[] array,
            int order,
            Implementation implementation) {

        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        return select(array.clone(), order, implementation);
    }

    /**
     * Iterative QuickSelect.
     *
     * It repeatedly narrows the active range to the partition that
     * contains targetIndex.
     * 
     * Auxiliary Space: Theta(1)
     */
    private static int quickSelectIterative(
            int[] array,
            int targetIndex) {

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int pivotIndex = randomizedPartition(array, low, high);

            // The pivot is the requested order statistic.
            if (pivotIndex == targetIndex) {
                return array[pivotIndex];
            }

            if (targetIndex < pivotIndex) {
                // Discard the pivot and everything to its right.
                high = pivotIndex - 1;
            } else {
                // Discard the pivot and everything to its left.
                low = pivotIndex + 1;
            }
        }

        /*
         * Valid input should always find the target before the loop ends.
         */
        throw new IllegalStateException(
                "QuickSelect failed to find the requested value.");
    }

    /**
     * Recursive QuickSelect following the CLRS RANDOMIZED-SELECT idea.
     *
     * Unlike QuickSort, this method recursively searches only one side.
     */
    private static int quickSelectRecursive(
            int[] array,
            int low,
            int high,
            int targetIndex) {

        // Base case: the active range contains one element.
        if (low == high) {
            return array[low];
        }

        int pivotIndex = randomizedPartition(array, low, high);

        // The pivot is now in its final sorted position.
        if (pivotIndex == targetIndex) {
            return array[pivotIndex];
        }

        if (targetIndex < pivotIndex) {
            // Search only the left partition.
            return quickSelectRecursive(
                    array,
                    low,
                    pivotIndex - 1,
                    targetIndex);
        }

        // Search only the right partition.
        return quickSelectRecursive(
                array,
                pivotIndex + 1,
                high,
                targetIndex);
    }

    /**
     * Chooses a random pivot from array[low...high], moves it to
     * array[high], and then applies the standard Lomuto partition.
     */
    private static int randomizedPartition(
            int[] array,
            int low,
            int high) {

        /*
         * nextInt includes low but excludes the upper bound.
         * Therefore, high + 1 allows index high to be selected.
         */
        int randomPivotIndex =
                ThreadLocalRandom.current().nextInt(low, high + 1);

        // Lomuto partition expects the pivot at array[high].
        swap(array, randomPivotIndex, high);

        return partition(array, low, high);
    }

    /**
     * CLRS-style Lomuto partition.
     *
     * After partitioning:
     *
     * values <= pivot | pivot | values > pivot
     *
     * @return the pivot's final sorted index
     */
    private static int partition(
            int[] array,
            int low,
            int high) {

        int pivot = array[high];

        // End of the region containing values <= pivot.
        int boundary = low - 1;

        for (int scanner = low; scanner < high; scanner++) {
            if (array[scanner] <= pivot) {
                boundary++;
                swap(array, boundary, scanner);
            }
        }

        // Place the pivot after the <= pivot region.
        swap(array, boundary + 1, high);

        return boundary + 1;
    }

    /**
     * Exchanges two array elements.
     */
    private static void swap(
            int[] array,
            int first,
            int second) {

        int temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }

    /**
     * Validates the array and requested order statistic.
     */
    private static void validateInput(int[] array, int order) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        if (order < 1 || order > array.length) {
            throw new IllegalArgumentException(
                    "Order must be between 1 and "
                            + array.length
                            + ".");
        }
    }

    /**
     * Demonstrates both implementations.
     */
    public static void main(String[] args) {
        int[] numbers = {8, 3, 10, 1, 6, 4, 7};
        int order = 3;

        int iterativeResult = QuickSelect.selectCopy(
                numbers,
                order,
                Implementation.ITERATIVE);

        int recursiveResult = QuickSelect.selectCopy(
                numbers,
                order,
                Implementation.RECURSIVE);

        System.out.println(
                "Original array:  " + Arrays.toString(numbers));

        System.out.println(
                order + "rd smallest, iterative: "
                        + iterativeResult);

        System.out.println(
                order + "rd smallest, recursive: "
                        + recursiveResult);
    }
}