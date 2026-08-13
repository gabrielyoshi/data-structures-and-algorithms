package algorithms.examples;

import algorithms.sorting.common.SortUtil;

// You are given a sorted array of strings that is interspersed with empty strings. 
// Write a method to find the location of a given string.

public class CW8_HeapSort extends SortUtil{
	
	public static int findStringIndex(String[] a, String value) {
		for (int i = 0; i < a.length; i++) {
			if (!a[i].equals("") && a[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

public static void main(String[] args) {
	
	String[] example = {"", "", "Bill", "", "", "Zoe", "", ""};
	
	show(example);
	
	System.out.println("String location: " + findStringIndex(example, "Bill"));
	System.out.println("String location: " + findStringIndex(example, "Zoe"));
	System.out.println("String location: " + findStringIndex(example, "Jane"));
	
	String[] test2 = {

		    "Amy", "", "", "Bill", "", "",

		    "Charlie", "", "David", "", "",

		    "Emma", "", "Frank", "", "Grace"

		};
	
	show(test2);
	
	System.out.println("String Amy location: " + findStringIndex(test2, "Amy"));
	System.out.println("String Bill location: " + findStringIndex(test2, "Bill"));
	System.out.println("String Charlie location: " + findStringIndex(test2, "Charlie"));
	System.out.println("String David location: " + findStringIndex(test2, "David"));
	System.out.println("String Emma location: " + findStringIndex(test2, "Emma"));
	System.out.println("String Frank location: " + findStringIndex(test2, "Frank"));
	System.out.println("String Grace location: " + findStringIndex(test2, "Grace"));
	
	
	}

}
