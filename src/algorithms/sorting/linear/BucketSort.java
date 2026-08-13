package algorithms.sorting.linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ================================================================
 * BUCKET SORT
 * ================================================================
 *
 * Category:
 *   Linear-time, non-comparison sorting algorithm
 *
 * Core idea:
 *   1. Divide the interval [0, 1) into n buckets.
 *   2. Distribute each value into its corresponding bucket.
 *   3. Sort each bucket individually.
 *   4. Concatenate the buckets in order.
 *
 * Example:
 *
 *   Input:
 *       [0.78, 0.17, 0.39, 0.26, 0.72]
 *
 *   With n = 5 buckets:
 *
 *       Bucket 0: [0.00, 0.20)
 *       Bucket 1: [0.20, 0.40)
 *       Bucket 2: [0.40, 0.60)
 *       Bucket 3: [0.60, 0.80)
 *       Bucket 4: [0.80, 1.00)
 *
 *   Distribution:
 *
 *       Bucket 0: [0.17]
 *       Bucket 1: [0.39, 0.26]
 *       Bucket 2: []
 *       Bucket 3: [0.78, 0.72]
 *       Bucket 4: []
 *
 * Assumptions:
 *   - Every value is in the interval [0, 1).
 *   - Values are approximately uniformly distributed.
 *
 * Runtime:
 *   Expected: Theta(n)
 *   Worst:    Theta(n^2)
 *
 * Why expected Theta(n)?
 *   Under a uniform distribution, each bucket contains only a
 *   constant expected number of elements.
 *
 * Why worst-case Theta(n^2)?
 *   If every value lands in the same bucket, insertion sort must
 *   sort all n values.
 *
 * Space:
 *   Theta(n)
 *
 * Properties:
 *   - Comparison-based inside each bucket: Yes
 *   - Overall comparison-sort lower bound applies: No
 *   - Stable: Yes, with this stable insertion-sort implementation
 *   - In-place: No
 * ================================================================
 */
public final class BucketSort {

    private BucketSort() {
        // Prevent creation of utility-class objects.
    }

    /**
     * Sorts values in ascending order.
     *
     * This CLRS-style implementation requires every value to be
     * in the interval [0, 1).
     *
     * @param array array to sort
     * @throws IllegalArgumentException if a value is outside [0, 1)
     *                                  or is NaN
     */
    public static void sort(double[] array) {
        if (array == null) {
            return;
        }

        validateRange(array);

        if (array.length < 2) {
            return;
        }

        int numberOfBuckets = array.length;

        /*
         * Create n initially empty buckets.
         *
         * Each bucket stores values from one subinterval of [0, 1).
         */
        List<List<Double>> buckets =
                new ArrayList<>(numberOfBuckets);

        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        /*
         * Distribute each value into a bucket.
         *
         * For n buckets:
         *
         *     bucketIndex = floor(n * value)
         *
         * Since 0 <= value < 1:
         *
         *     0 <= bucketIndex <= n - 1
         */
        for (double value : array) {
        	//converts each number into a bucket index
            int bucketIndex =
                    (int) (numberOfBuckets * value);
            // adds each number into a bucket
            buckets.get(bucketIndex).add(value);
        }

        /*
         * Sort each bucket separately.
         *
         * CLRS uses insertion sort because each bucket is expected
         * to contain only a small number of elements.
         * 
         * NOTE: Can be parallelized without a for-loop
         */
        for (List<Double> bucket : buckets) {
            insertionSort(bucket);
        }

        /*
         * Concatenate the buckets back into the original array.
         *
         * Every value in bucket i is less than every value in
         * bucket i + 1.
         */
        int outputIndex = 0;

        for (List<Double> bucket : buckets) {
            for (double value : bucket) {
                array[outputIndex] = value;
                outputIndex++;
            }
        }
    }

    /**
     * Sorts one bucket using stable insertion sort.
     *
     * For a bucket containing m elements:
     *
     * Best case:  Theta(m)
     * Worst case: Theta(m^2)
     */
    private static void insertionSort(List<Double> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            double key = bucket.get(i);
            int j = i - 1;

            /*
             * Shift larger values one position to the right.
             *
             * Using > rather than >= preserves the relative order
             * of equal values, making the sort stable.
             */
            while (j >= 0 && bucket.get(j) > key) {
                bucket.set(j + 1, bucket.get(j));
                j--;
            }

            bucket.set(j + 1, key);
        }
    }

    /**
     * Confirms that every value belongs to [0, 1).
     */
    private static void validateRange(double[] array) {
        for (double value : array) {
            if (Double.isNaN(value)
                    || value < 0.0
                    || value >= 1.0) {

                throw new IllegalArgumentException(
                        "BucketSort requires values in [0, 1). "
                                + "Invalid value: "
                                + value);
            }
        }
    }

    /**
     * Demonstrates Bucket Sort.
     */
    public static void main(String[] args) {
        double[] numbers = {
                0.78,
                0.17,
                0.39,
                0.26,
                0.72,
                0.94,
                0.21,
                0.12,
                0.23,
                0.68
        };

        System.out.println(
                "Before: " + Arrays.toString(numbers));

        BucketSort.sort(numbers);

        System.out.println(
                "After:  " + Arrays.toString(numbers));
    }
}