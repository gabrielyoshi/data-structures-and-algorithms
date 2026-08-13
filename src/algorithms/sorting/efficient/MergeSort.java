package algorithms.sorting.efficient;
import algorithms.sorting.common.SortUtil;

public class MergeSort extends SortUtil{
	/**
	 * Merges two sorted subarrays: a[lo...mid] and a[mid+1...hi]
	 * into a single sorted subarray a[lo...hi].
	 * 
	 * Preconditions:
	 * 	Both subarrays are sorted
	 * 	aux has length >= a.length
	 * 
	 * Process: 
	 * 	Copies a[lo...hi] to aux[lo...hi]
	 * 	Repeatedly selects smallest front element from left/right half and writes back to "a"
	 * 
	 * Stable: when keys are equal, element from left half is chosen first. Preserves relative order
	 * 
	 * Runtime: Θ(hi - lo + 1)
	 * Extra Space: Θ(hi - lo + 1) (uses the auxiliary array)
	 * @param a		array to be sorted
	 * @param aux	auxiliary array used during merging
	 * @param lo	starting index of left subarray
	 * @param mid	ending indes of left subarray (mid+1 is start of right subarray)
	 * @param hi	ending index of right subarray
	 */
	public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		int i = lo, j = mid + 1; //i represents current of left. j represents current of right
		//OLDER CODE
		//Comparable[] aux = new Comparable[a.length]; // allocates new array for every merge()
		
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k]; // copy a[lo...hi] to aux[lo...hi]
		}
		
		for (int k = lo; k <= hi; k++) { // merge back to a[lo...hi]
			if (i > mid) // CASE left half is empty
				a[k] = aux[j++]; // copy from right
			else if (j > hi) // CASE right half is empty
				a[k] = aux[i++]; // copy from left
			// Takes the left element when keys are equal
			else if (!less(aux[j], aux[i])) // equivalent to aux[i] <= aux[j]
				// CASE left is smaller or equal
				a[k] = aux[i++]; // take from left
			else // CASE right is smaller
				a[k] = aux[j++]; // take from right
		}
	}
	
	/**
	 * Sorts array using top-down MergeSort.
	 * 
	 * Allocates a single auxiliary array and passes it through all recursive calls to 
	 * avoid cost of creating a new temporary array during every merge operation
	 * 
	 * Runtime:  Θ(n log n)
	 * Space Complexity: Θ(n)
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		// Passes down single aux[] into mergeSort() to reuse across all recursive calls
		Comparable[] aux = new Comparable[a.length]; 
		mergeSort(a, aux, 0, a.length - 1);
	}
	
	/**
	 * Recursively sorts subarray a[lo...hi] using MergeSort.
	 * 
	 * Divide-and-Conquer Strategy:
	 * 	1. Divide: Split subarray into two halves
	 * 	2. Conquer: Recursively sort each half
	 * 	3. Combine: Merge two sorted halves
	 * 
	 * Base Case: When lo >= hi, subarray contains at most one element and is sorted
	 * 
	 * Runtime: Θ(n log n)
	 * Space Complexity: Θ(n)
	 * Recurrence: T(n) = 2T(n/2) + Θ(n)
	 * @param a 	array being sorted
	 * @param aux	auxiliary array used during merging
	 * @param lo	first index of subarray
	 * @param hi	last index of subarray
	 */
	public static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (lo < hi) {
			int mid = lo + (hi - lo)/2; // avoids integer overflow. DIVIDE = Θ(1)
			mergeSort(a, aux, lo, mid); // CONQUER1 T(n/2) left half
			mergeSort(a, aux, mid + 1, hi); //CONQUER2 T(n/2) right half
			merge(a, aux, lo, mid, hi); // COMBINE = Θ(n)
		}
	}
}
