package algorithms.examples;

public class MergeArrays {
	
	public static void merge(int[] a, int[] b, int lastA, int lastB) {
		assert a != null : "Array A cannot be null";
		assert b != null : "Array B cannot be null";
		
		assert lastA >= 0 && lastA < a.length : "lastA is out of bounds";
		assert lastB >= 0 && lastB < b.length : "lastB is out of bounds";
		
		assert isSorted(a, lastA) : "Array A is not sorted";
		assert isSorted(b, lastB) : "Array B is not sorted";
		
		assert enoughSpace(a, b, lastA, lastB) : "Array A does not have enough space";
		
		int indexA = lastA;
		int indexB = lastB;
		int indexMerged = lastA + lastB + 1;
		
		while (indexA >= 0 && indexB >= 0) {
			if (a[indexA] > b[indexB]) {
				a[indexMerged] = a[indexA];
				indexA--;
			} else {
				a[indexMerged] = b[indexB];
				indexB--;
			}
			indexMerged--;
		}
	 
	}

	public static boolean enoughSpace(int[] first, int[] second, int lastA, int lastB) {
		return (first.length - (lastA+1) >= lastB+1);
	}
	
	public static boolean isSorted(int[] list, int last) {
		for (int i = 0; i<last; i++) {
			if (list[i] > list[i+1])
				return false;
		}
		return true;
	}
	private static void show(int[] a) {
		//Print array on single line
		for (int i = 0; i<a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] a = {1,2,3,0,0,0};
		int[] b = {2,4,6};
		
		show(a);
		show(b);
		
		System.out.println(isSorted(a, 3));
		System.out.println(isSorted(b, 2));
		
		System.out.println(enoughSpace(a, b, 2, 2));
		
		merge(a,b, 2, 2);
		
		show(a);

	}

}
