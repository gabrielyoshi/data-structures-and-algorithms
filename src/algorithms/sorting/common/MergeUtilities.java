package algorithms.sorting.common;

public class MergeUtilities extends SortUtil {
	/**
	 * Midterm Review
	 * Takes two sorted arrays and merges from the back
	 * Assumption: array "A" has enough space at the end to hold array "B"
	 * 
	 *Runtime: Θ(lastA + lastB)
	 *Space: Θ(1)
	 * @param a
	 */
	
	public static void mergeIntoFirstArray(Integer[] a, Integer[] b, int lastA, int lastB) {
		assert isSorted(a, lastA): "Array a is not sorted.";
		assert isSorted(b, lastB): "Array b is not sorted.";
		
		int i = lastA; // index of last valid element in A
		int j = lastB; // index of last valid element in B
		int k = lastA + lastB + 1; // pointer to last position in merged array
		
		assert a.length >= k + 1: "Array a does not have enough space";
		
		while (j >= 0) { // Array B is not empty
			if (i >= 0 && a[i] > b[j]) {
				a[k] = a[i]; // CASE a[i] > b[j]
				i--;
			} else {
				a[k] = b[j]; // take element from B(B larger of A empty)
				j--;
			}
			k--; // move last position of merged array to left
		}
		
	}
	
	/**
	 * Merges two Integer subarrays a[lo...mid] and a[mid+1...hi] into a single sorted subarray a[lo...hi]
	 * 
	 * Follows CLRS sentinel approach. Left and right halves copied into temp arrays L and R
	 * that each contain sentinel value (Integer.MAX_VALUE) at end. Eliminates need for boundary checks
	 * 
	 * Stable: when keys are equal, element from left subarray chosen first
	 * 
	 * Runtime: Θ(hi - lo + 1)
	 * Extra Space: Θ(hi - lo + 1)
	 * 
	 * @param a		array containing two sorted subarrays
	 * @param lo	start index of left subarray
	 * @param mid	end index of left subarray
	 * @param hi	end index of right subarray
	 */
	public static void mergeIntListsCLRS(Integer[] a, int lo, int mid, int hi) {
		
		int n1 = mid - lo + 1; // size of left half
		int n2 = hi - mid;	// size of right half
		
		Integer[] L = new Integer[n1 + 1]; // +1 for sentinel
		Integer[] R = new Integer[n2 + 1]; // +1 for sentinel
		
		for (int i = 0; i < n1; i++) {
			L[i] = a[lo + i];
		}
		for (int j = 0; j < n2; j++) {
			R[j] = a[mid + 1 + j];
		}
		
		//eliminates boundary check in merge()
		L[n1] = Integer.MAX_VALUE; //sentinel value marks end of left half
		R[n2] = Integer.MAX_VALUE; //sentinel value marks end of right half
		
		int i = 0, j = 0; 
		
		// Merge back into a[lo...hi]. 
		for (int k = lo; k <= hi; k++) {
			// Takes the left element when keys are equal
			if (!less(R[j], L[i])) // equivalent to L[i] <= R[j]
				a[k] = L[i++];
			else
				a[k] = R[j++];
		}
	}
}
