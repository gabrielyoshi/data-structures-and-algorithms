package algorithms.sorting.elementary;
import algorithms.sorting.common.SortUtil;

public class InsertionSort extends SortUtil {
	/**
	 * Sorts array in ascending order using Insertion Sort.
	 * 
	 * Builds a sorted prefix one element at a time. On each iteration,
	 * a[i] is inserted into correct position within already
	 * sorted subarray a[0...i-1]
	 * 
	 * Loop Invariant:
	 * Before each iteration i, subarray a[0...i-1] is sorted. These elements are not fixed
	 * (unlike Selection Sort), and may move during future insertions
	 * 
	 * Performance:
	 * 	Best Case: Θ(n)
	 * 	Average Case: Θ(n²)
	 * 	Worst Case: Θ(n²)
	 * 
	 * Best Case (already sorted):
	 * 	n - 1 comparisons, 0 exchanges
	 * Average and Worst Case (reverse sorted):
	 * 	~n²/4 comparisons
	 * 	~n²/4 exchanges
	 * 
	 * Runs in linear time when number of inversion is proportional to n, making it
	 * efficient for partially sorted arrays.
	 * 
	 * Space Complexity: Θ(1)
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		// ~N^2/4 compares and ~N^2/4 exchanges on average
		// worst case ~N^2/2 compares and ~N^2/2 exchanges
		// best case N-1 compares and 0 exchanges
		// efficient when number of inversions < C*array.length (i.e., partially sorted array)
		// quadratic with inversions >= N(N-1)/2
		int N = a.length;
		
		for (int i = 1; i<N; i++) {
		// loop invariant 
		// everything to the left of i (a[0...i-1]), already sorted but NOT fixed
			for (int j = i; j > 0 && less(a[j], a[j-1]); j--) 
				// compares = # exchanges + (N - number of times a[j] is the smallest
				exch(a, j, j-1); // exchange a[i=j] among a[i-1], a[i-2], ...
								// IOW, move a[j] left into its sorted position
								// called to address every inversion
		}
	}
}
