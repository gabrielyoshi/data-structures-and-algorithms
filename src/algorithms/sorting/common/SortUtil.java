package algorithms.sorting.common;

/**
 * Shared helper methods
 */

public abstract class SortUtil {

    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    protected static boolean more(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }
    
    protected static boolean equal(Comparable v, Comparable w) {
    	return v.compareTo(w) == 0;
    }

    protected static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    protected static void show(Comparable[] a) {
        for (Comparable item : a)
            System.out.print(item + " ");
        System.out.println();
    }
    
    protected static boolean isSorted(Comparable[] a) {
		// Test order of array elements
		for (int i = 1; i<a.length; i++)
			if (less(a[i], a[i-1]))
				return false;
		return true;
	}
    
    protected static boolean isSorted(Integer[] a, int last) {
        for (int i = 1; i <= last; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }
}
