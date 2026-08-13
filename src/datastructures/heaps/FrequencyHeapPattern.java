package datastructures.heaps;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * General frequency-counting and max-heap pattern.
 *
 * Use this pattern when a problem requires:
 * 1) Counting the occurrences of elements
 * 2) Processing elements from highest frequency to lowest frequency
 *
 * General process:
 * HashMap -> Max Heap -> Construct result
 */

public class FrequencyHeapPattern {
	 /**
     * Demonstrates the frequency + max-heap pattern using characters.
     *
     * Time Complexity:
     * O(n + k log k)
     *
     * Space Complexity:
     * O(k)
     *
     * n = total number of characters
     * k = number of distinct characters
     */
    public static String orderByFrequency(String input) {

        // Maps each character to the number of times it occurs.
        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Count the frequency of every character.
        for (char character : input.toCharArray()) {
            frequencyMap.put(
                    character,
                    frequencyMap.getOrDefault(character, 0) + 1);
        }

        // Max-heap ordered by decreasing frequency.
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap =
                new PriorityQueue<>(
                        (a, b) ->
                                Integer.compare(
                                        b.getValue(),
                                        a.getValue()));

        // Add each distinct character and its frequency to the heap.
        maxHeap.addAll(frequencyMap.entrySet()); // entrySet returns collection of k-v pairs

        StringBuilder result = new StringBuilder();

        // Process the most frequent character first.
        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> entry = maxHeap.poll();

            char character = entry.getKey();
            int frequency = entry.getValue();

            // Process the current character once per occurrence.
            for (int i = 0; i < frequency; i++) {
                result.append(character);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String input = "tree";
        String result = orderByFrequency(input);

        System.out.println("Input:  " + input);
        System.out.println("Result: " + result);
    }
}
