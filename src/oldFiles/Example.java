package oldFiles;

import algorithms.sorting.common.SortUtil;
import datastructures.heaps.MaxPQ;

/**
 * Algorithms
 */

public class Example extends SortUtil {
	
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
	public static void selectionSort(Comparable[] a) {
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
	public static void insertionSort(Comparable[] a) {
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
	public static void mergeSort(Comparable[] a) {
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
	
	/**
	 * Restores a max-heap using a top-down approach whereby
	 * element at index k "sinks" until it is greater than or equal to
	 * both children
	 * 
	 * Uses 0-based indexing
	 * 
	 * Runtime:
	 *   O(lg heapSize)
	 * 
	 * @param a array representing the heap
	 * @param k index of node to sink
	 * @param heapSize heap size; valid indices are 0 through heapSize - 1
	 */
	protected static void topDown0Based(Comparable[] a, int k, int heapSize) {
    	while (2*k+1 < heapSize)
    	{
    		int child = 2 * k + 1; // specifically left child
    		
    		// if right child valid and larger
    		if (child + 1 < heapSize && less(a[child], a[child + 1])) 
    			child++; // use right child
    		if (!less(a[k], a[child])) 
    			break; // node is largest. Heap order restored.
    		
    		exch(a, k, child); // exchange node with largest child
    		k = child; // check child subtree
    		
    	}
    }
	
	/**
	 * Sorts array in ascending order using HeapSort.
	 * 
	 * 1) Builds max-heap from input array
	 * 2) Exchanges root (max-element) with last element in heap, reduces heap size
	 * by 1, and restores max-heap property
	 * 
	 * Runtime:
	 *   Best: Θ(n log n)
	 *   Average: Θ(n log n)
	 *   Worst: Θ(n log n)
	 *   
	 *  Space Complexity: Θ(1)
	 * @param a input array to be sorted
	 */
	public static void heapSort(Comparable[] a) {
		// Convert array into max heap (bottom-up)
		buildMaxHeap(a);
		
		// loop invariant: Before, 
		//     a[0...end] is a max-heap, and
		//     a[end+1...n-1] is sorted in ascending order with largest elements
		//	   in final sorted positions.
		for (int end = a.length - 1; end > 0; end--) {
			exch(a, 0, end); // moves index 0 (max element) to end of array
			topDown0Based(a, 0, end); // restore heap in a[0...end-1]
		}
	}
	
	/**
	 * Converts an array a[0...n-1] into a max-heap
	 * 
	 * Runtime:
	 *   O(n) Each call to topDown0Based() is O(log n) but true for only root
	 *   Most nodes near bottom and sink less. 
	 *   	Total Work:
	 *   		Σ (#nodes at level i) × (maximum sink distance)
	 *   		= Σ 2^i(h − i)
	 *   		= Θ(n)
	 *   	
	 * @param a input array to be sorted into max-heap
	 */
	public static void buildMaxHeap(Comparable[] a) {
		
		int lastParentIndex = (a.length - 2)/2;
		for (int i = lastParentIndex; i >= 0; i--) {
			topDown0Based(a, i, a.length);
		}
	}
	
	public static void main(String[] args) {
		// Read strings from input, sort, and print
		System.out.println("=== Selection Sort Test ===");
		
		String[] selectionTest = {

		        "M", "E", "R", "G", "E",

		        "S", "O", "R", "T"

		    };
		
		System.out.println("Before: ");
		show(selectionTest);
		selectionSort(selectionTest);
		
		System.out.print("After: ");
		show(selectionTest);
		
		System.out.println("Sorted? " + isSorted(selectionTest));
		assert isSorted(selectionTest);
		
		System.out.println("\n=== Insertion Sort Test ===");
		String [] insertionTest = {
				"I", "N", "S", "E", "R", "T", "I", "O", "N"
				};
		
		System.out.print("Before: ");
		show(insertionTest);
		
		insertionSort(insertionTest);
		
		System.out.print("After: ");
		show(insertionTest);
		
		System.out.println("Sorted? " + isSorted(insertionTest));
		assert isSorted(insertionTest);
		
		System.out.println("\n=== CLRS Merge Test 1 ===");
		Integer[] mergeTest1 = {2,4,5,7,1,2,3,6};
		// Left half:  [2, 4, 5, 7]
	    // Right half: [1, 2, 3, 6]
		
		System.out.print("Before: ");
		show(mergeTest1);
		mergeIntListsCLRS(mergeTest1, 0, 3, mergeTest1.length-1);
		
		System.out.print("After: ");
		show(mergeTest1);
		
		System.out.println("Sorted? " + isSorted(mergeTest1));
		assert isSorted(mergeTest1);
		
		System.out.println("\n=== CLRS Merge Test 2 ===");
		Integer[] mergeTest2 = {1, 3, 8, 10, 2, 4, 6, 9};
		// Left half:  [1, 3, 8, 10]
	    // Right half: [2, 4, 6, 9]
		
		System.out.print("Before: ");
		show(mergeTest2);
		mergeIntListsCLRS(mergeTest2, 0, 3, mergeTest2.length-1);
		
		System.out.print("After: ");
		show(mergeTest2);

		System.out.println("Sorted? " + isSorted(mergeTest2));
		assert isSorted(mergeTest2);
		
		System.out.println("\n=== MergeSort Test ===");
		Integer[] mergeSortTest = {5,2,4,7,1,3,2,6};
		
		System.out.print("Before: ");
		show(mergeSortTest);
		
		mergeSort(mergeSortTest);
		
		System.out.print("After: ");
		show(mergeSortTest);

		System.out.println("Sorted? " + isSorted(mergeSortTest));
		assert isSorted(mergeSortTest);
		
		System.out.println("\n=== MaxPQ Test ===");
		
		MaxPQ<Integer> pq = new MaxPQ<>(10);
		
		pq.insert(5);
		pq.insert(12);
		pq.insert(3);
		pq.insert(20);
		pq.insert(8);
		
		while (!pq.isEmpty()) {
			System.out.print(pq.delMax() + " ");
		}
		System.out.println();
		
		System.out.println("\n=== HeapSort Test ===");
		Integer[] heapSortTest = {5, 12, 3, 20, 8, 1, 7};
		
		System.out.print("Before: ");
		show(heapSortTest);
		
		heapSort(heapSortTest);
		
		System.out.print("After: ");
		show(heapSortTest);
		
		System.out.println("Sorted? " + isSorted(heapSortTest));
		assert isSorted(heapSortTest);
		
		System.out.println("\n=== Recursive Max-Heapify Test ===");
		
		// index 0 unused for 1-based heap
		Integer[] heap = {null, 10, 30, 25, 20, 15, 5, 2};
		
		System.out.print("Before: ");
		show(heap);
		
		MaxPQ.topDownRecursive1Based(heap, 1, 7);
		
		System.out.print("After: ");
		show(heap);

		System.out.println("\n=== Merge from Back Test ===");
		Integer[] a = {1, 3, 5, null, null, null};
		Integer[] b = {2, 4, 6};
		
		System.out.print("Before: ");
		show(a);
		System.out.print("Before: ");
		show(b);
		
		mergeIntoFirstArray(a, b, 2, 2);
		
		System.out.print("After: ");
		show(a);
		
	}

}
