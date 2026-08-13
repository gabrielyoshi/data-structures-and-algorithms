package algorithms.sorting.elementary;
import algorithms.sorting.common.SortUtil;

public class SelectionSort extends SortUtil {
	
	/**
	 * Sorts array in ascending order using Selection Sort.
	 * 
	 * On each pass, finds smallest element in unsorted portion and places it into 
	 * final sorted position.
	 * 
	 * Loop Invariant:
	 * Before each iteration i, subarray a[0...i-1] contains smallest i elements
	 * in sorted, fixed order. 
	 * 
	 * Performance:
	 * 	Best Case: Θ(n²)
	 * 	Average Case: Θ(n²)
	 * 	Worst Case: Θ(n²)
	 * 
	 * Comparisons: n(n-1)/2 = Θ(n²)
	 * Exchanges: at most n = Θ(n)
	 * 
	 * Space Complexity: Θ(1)
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		//Selection sort O(n^2) Omega(n^2) Θ(n^2)
		//Best case: Θ(n^2) 
		//Worst case Θ(n^2)
		int n = a.length;
		
		for (int i = 0; i<n; i++) {
			int min = i;
			// loop invariant
			// everything to the left of i, already sorted and fixed
			for (int j = i+1; j<n; j++) {
				// Total comparisons:
				// (n-1) + (n-2) + ... + 1 + 0 = 0 to n - 1 - i
				// = n(n-1)/2 = Θ(n^2)
				if (less(a[j], a[min])) 
					min = j; // smallest item in array
			}
			// One exchange per outer-loop: N exchanges from 0 to N-1 = Θ(n)
			exch(a, i, min); // exchange smallest item in a[i...n-1] into position i 
		}
	}
}