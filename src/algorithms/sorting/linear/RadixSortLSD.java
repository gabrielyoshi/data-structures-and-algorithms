package algorithms.sorting.linear;

import java.util.Arrays;

/**
 * ================================================================
 * LSD RADIX SORT
 * ================================================================
 *
 * Category:
 *   Linear-time, non-comparison sorting algorithm
 *
 * Core idea:
 *   Sort the numbers one digit at a time, beginning with the
 *   least significant digit:
 *
 *       ones -> tens -> hundreds -> thousands -> ...
 *
 * Each digit pass must use a STABLE sorting algorithm.
 * This implementation uses Counting Sort for each digit.
 *
 * Example:
 *
 *   Input:
 *       [170, 45, 75, 90, 802, 24, 2, 66]
 *
 *   Sort by ones:
 *       [170, 90, 802, 2, 24, 45, 75, 66]
 *
 *   Sort by tens:
 *       [802, 2, 24, 45, 66, 170, 75, 90]
 *
 *   Sort by hundreds:
 *       [2, 24, 45, 66, 75, 90, 170, 802]
 *
 * Requirements:
 *   - Input values must be nonnegative integers.
 *   - The digit-level sort must be stable.
 *
 * Runtime:
 *
 *   Let:
 *       n = number of elements
 *       d = number of digits in the largest value
 *       k = number of possible digit values
 *
 *   General:
 *       Theta(d(n + k))
 *
 *   For decimal digits, k = 10:
 *       Theta(d(n + 10)) = Theta(dn)
 *
 *   If d is considered constant:
 *       Theta(n)
 *
 * Space:
 *       Theta(n + k)
 *       Theta(n) for decimal integers
 *
 * Properties:
 *   - Stable: Yes
 *   - In-place: No
 *   - Comparison-based: No
 *   - Worst-case Theta(n log n) lower bound applies: No
 *
 * Why LSD works:
 *   Each stable pass preserves the ordering established by the
 *   less significant digits processed during earlier passes.
 * ================================================================
 */
public final class RadixSortLSD {

    private static final int RADIX = 10;

    private RadixSortLSD() {
        // Prevent creation of utility-class objects.
    }

    /**
     * Sorts an array of nonnegative integers in ascending order.
     *
     * @param array array to sort
     * @throws IllegalArgumentException if the array contains
     *                                  a negative value
     */
    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        validateNonnegative(array);

        int maximum = findMaximum(array);

        /*
         * exponent identifies the digit currently being processed:
         *
         * exponent = 1   -> ones digit
         * exponent = 10  -> tens digit
         * exponent = 100 -> hundreds digit
         *
         * long prevents exponent * 10 from overflowing before the
         * loop condition is checked.
         */
        for (long exponent = 1;
             maximum / exponent > 0;
             exponent *= RADIX) {

            countingSortByDigit(array, exponent);
        }
    }

    /**
     * Performs a stable Counting Sort using one decimal digit.
     *
     * Digit extraction:
     *
     *     digit = (value / exponent) % 10
     *
     * Examples:
     *
     *     value = 472
     *
     *     exponent = 1:
     *         (472 / 1) % 10 = 2
     *
     *     exponent = 10:
     *         (472 / 10) % 10 = 7
     *
     *     exponent = 100:
     *         (472 / 100) % 10 = 4
     */
    private static void countingSortByDigit(
            int[] array,
            long exponent) {

        int[] output = new int[array.length];

        /*
         * Decimal digits range from 0 through 9.
         *
         * count[d] initially stores the number of values whose
         * current digit equals d.
         */
        int[] count = new int[RADIX];

        // Count the frequency of each current digit.
        for (int value : array) {
            int digit = (int) ((value / exponent) % RADIX);
            count[digit]++;
        }

        /*
         * Convert frequencies into cumulative counts.
         *
         * After this loop, count[d] tells us how many values have
         * a current digit less than or equal to d.
         *
         * This determines the final output position of each value.
         */
        for (int digit = 1; digit < RADIX; digit++) {
            count[digit] += count[digit - 1];
        }

        /*
         * Traverse from right to left to preserve stability.
         *
         * Equal digits remain in the same relative order they had
         * before this digit pass.
         */
        for (int index = array.length - 1; index >= 0; index--) {
            int value = array[index];
            int digit = (int) ((value / exponent) % RADIX);

            int outputIndex = count[digit] - 1;
            output[outputIndex] = value;

            count[digit]--;
        }

        // Copy the digit-sorted values back into the original array.
        System.arraycopy(output, 0, array, 0, array.length);
    }

    /**
     * Returns the largest value in the array.
     *
     * The largest value determines how many digit passes are needed.
     */
    private static int findMaximum(int[] array) {
        int maximum = array[0];

        for (int value : array) {
            if (value > maximum) {
                maximum = value;
            }
        }

        return maximum;
    }

    /**
     * Radix Sort requires nonnegative values in this implementation.
     */
    private static void validateNonnegative(int[] array) {
        for (int value : array) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "RadixSortLSD supports only nonnegative integers.");
            }
        }
    }

    /**
     * Demonstrates LSD Radix Sort.
     */
    public static void main(String[] args) {
        int[] numbers = {
                170, 45, 75, 90, 802, 24, 2, 66
        };

        System.out.println(
                "Before: " + Arrays.toString(numbers));

        RadixSortLSD.sort(numbers);

        System.out.println(
                "After:  " + Arrays.toString(numbers));
    }
}