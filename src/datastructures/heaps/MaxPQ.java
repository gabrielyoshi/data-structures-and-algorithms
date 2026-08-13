package datastructures.heaps;

import algorithms.sorting.common.SortUtil;

/**
 * Maximum Priority Queue Abstract Data Type (ADT)
 * Uses 1-based indexing
 * @param <Key>
 */
public class MaxPQ<Key extends Comparable<Key>> extends SortUtil{
	
	private Key[] pq; // heap-ordered complete binary tree
	private int N = 0; // in pq[1...N] with pq[0] unused
	
	/**
	 * Maximum priority queue constructor 
	 * @param maxN
	 */
	public MaxPQ(int maxN) {
		pq = (Key[]) new Comparable[maxN+1];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N; 
	}
	
	public void insert(Key v) {
		pq[++N] = v; // place new key in last position
		bottomUp(pq, N);
	}
	
	public Key delMax() {
		if (isEmpty() ) {
			throw new RuntimeException("Priority queue underflow");
		}
		Key max = pq[1]; // Retrieve max
		exch(pq, 1, N--); // exchange with last item
		pq[N+1] = null; 
		topDown1Based(pq, 1, N);
		return max;
	}
	
	
	protected static void bottomUp(Comparable[] a, int k) {
    	// (k/2) behaves like a floor function
		// identifies the parent of node k
    	while (k > 1 && less(a[k/2], a[k])) {
    		// if parent node exists and parent is smaller than node k
    		exch(a, k/2, k); // exchange node with parent
    		k = k / 2; // move up to parent
    	}
    }
    
    protected static void topDown1Based(Comparable[] a, int k, int N) {
    	while (2*k <= N)
    	{
    		int child = 2 * k; // specifically left child
    		
    		// if right child valid and larger
    		if (child < N && less(a[child], a[child + 1])) 
    			child++; // right child
    		if (!less(a[k], a[child])) 
    			break; // node k is largest
    		exch(a, k, child); // exchange node with largest child
    		k = child; // check child subtree iteratively
    		
    	}
    }
    
    /**
     * Restores max-heap recursively.
     * 
     * Assumption: left and right subtrees of node k are max-heaps.
     * 
     * If node k violates heap, it is exchanged with largest child, then continues
     * recursively
     * 
     * Runtime: Θ(log n)
     * 
     * @param a heap array (1-based indexing)
     * @param k index of node to heapify
     * @param N size of heap
     */
    public static void topDownRecursive1Based(Comparable[] a, int k, int N) {
    	int left = 2 * k;
    	int right = 2 * k + 1;
    	
    	int largest = k;
    	
    	if (left <= N && less(a[largest], a[left])) {
    		largest = left;
    	}
    	if (right <= N && less(a[largest], a[right])) {
    		largest = right;
    	}
    	
    	// implicit base case largest = k assumes subtrees are max-heaps
    	if (largest != k) { 
    		exch(a, k, largest);
    		// checks child subtree recursively
    		topDownRecursive1Based(a, largest, N); // only in subtree where "k" sank
    	}
    }

}
