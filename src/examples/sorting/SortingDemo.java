package examples.sorting;
import algorithms.sorting.elementary.InsertionSort;
import algorithms.sorting.elementary.SelectionSort;
import algorithms.sorting.efficient.HeapSort;
import algorithms.sorting.efficient.MergeSort;
import algorithms.sorting.common.*;
import datastructures.heaps.MaxPQ;


public class SortingDemo extends SortUtil {
	public static void main(String[] args) {
		// Read strings from input, sort, and print
		System.out.println("=== Selection Sort Test ===");
		
		String[] selectionTest = {

		        "M", "E", "R", "G", "E",

		        "S", "O", "R", "T"

		    };
		
		System.out.println("Before: ");
		show(selectionTest);
		SelectionSort.sort(selectionTest);
		
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
		
		InsertionSort.sort(insertionTest);
		
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
		MergeUtilities.mergeIntListsCLRS(mergeTest1, 0, 3, mergeTest1.length-1);
		
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
		MergeUtilities.mergeIntListsCLRS(mergeTest2, 0, 3, mergeTest2.length-1);
		
		System.out.print("After: ");
		show(mergeTest2);

		System.out.println("Sorted? " + isSorted(mergeTest2));
		assert isSorted(mergeTest2);
		
		System.out.println("\n=== MergeSort Test ===");
		Integer[] mergeSortTest = {5,2,4,7,1,3,2,6};
		
		System.out.print("Before: ");
		show(mergeSortTest);
		
		MergeSort.sort(mergeSortTest);
		
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
		
		HeapSort.sort(heapSortTest);
		
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
		
		MergeUtilities.mergeIntoFirstArray(a, b, 2, 2);
		
		System.out.print("After: ");
		show(a);
		
	}
}
