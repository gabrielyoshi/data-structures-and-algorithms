package algorithms.examples;

import algorithms.sorting.common.SortUtil;

public class CW5_MergeSort extends SortUtil{
	
	public static void swapLargestAndSmallest(Integer [] a) {
		int n = a.length;
		int min = 0;
		int max = 0;
		
		for (int i = 0; i<n; i++) {
			if (less(a[i], a[min])) // compares from 0 to N-1-i = (N-1)+(N-2)+...+2+1+0 
				// = [(N-1)N]/2 ~ N^2/2
				min = i; // smallest item in array
		}
		for (int i = n-1; i>0; i--) {
			if (less(a[max], a[i])) {
				max = i; // largest item in array
			}
		}

		
		exch(a, max, min); // exchange smallest item in a[i+1...n] into position max 
	}

	public static void main(String[] args) {
		Integer [] array = {1, 2, 9, 2, 1, 9, 3};
		show(array);
		
		swapLargestAndSmallest(array);
		
		
		System.out.println(SortUtil.isSorted(array));
		
		show(array);
		
		
		
	}

}
