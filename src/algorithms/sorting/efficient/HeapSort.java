package algorithms.sorting.efficient;
import algorithms.sorting.common.SortUtil;


public class HeapSort extends SortUtil {
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
	public static void sink(Comparable[] a, int k, int heapSize) {
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
	public static void sort(Comparable[] a) {
		// Convert array into max heap (bottom-up)
		buildMaxHeap(a);
		
		// loop invariant: Before, 
		//     a[0...end] is a max-heap, and
		//     a[end+1...n-1] is sorted in ascending order with largest elements
		//	   in final sorted positions.
		for (int end = a.length - 1; end > 0; end--) {
			exch(a, 0, end); // moves index 0 (max element) to end of array
			sink(a, 0, end); // restore heap in a[0...end-1]
		}
	}
	
	/**
	 * Converts an array a[0...n-1] into a max-heap
	 * 
	 * Runtime:
	 *   O(n) Each call to sink() is O(log n) but true for only root
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
			sink(a, i, a.length);
		}
	}
}
