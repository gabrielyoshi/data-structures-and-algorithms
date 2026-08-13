package algorithms.examples;
import algorithms.sorting.efficient.*;

import algorithms.sorting.common.*;

// Given an array of integers, check if it represents a min-heap or not.

public class CW7_Heaps extends SortUtil{
	
	public static boolean isMinHeap(Comparable[] a, int startIndex) {
		if (startIndex >= a.length) {
			return true;
		}
		
		
		int left = 2 * startIndex + 1;
		int right = left + 1;
		
		if(left < a.length && less(a[left], a[startIndex])) {
			return false;
		}
		if(right < a.length && less(a[right], a[startIndex])) {
			return false;
		}
		
		return isMinHeap(a, startIndex + 1);
	}
	
	public static void buildMaxHeap(Comparable[] a) {
		
		int lastParentIndex = (a.length - 2)/2;
		for (int i = lastParentIndex; i >= 0; i--) {
			HeapSort.sink(a, i, a.length);
		}
	}

	public static void main(String[] args) {
		Integer[] a = {5, 12, 3, 20, 8, 1, 7};
		
		show(a);
		
		System.out.println("Min Heap? " + isMinHeap(a, 0));
		
		Integer[] minHeap = {1, 5, 3, 12, 8, 20, 7};
		
		show(minHeap);
		
		System.out.println("Min Heap? " + isMinHeap(minHeap, 0));
		
		Integer[] CLRS = {5,3,17,10,84,19,6,22,9};
		
		show(CLRS);
		
		buildMaxHeap(CLRS);
		
		show(CLRS);
		
		System.out.println("Min Heap? " + isMinHeap(CLRS, 0));

	}

}
