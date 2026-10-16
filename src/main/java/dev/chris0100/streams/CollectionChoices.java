package dev.chris0100.streams;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/** Small, concrete answers to "which collection would you use and why?". */
public final class CollectionChoices {

    private CollectionChoices() {
    }

    /** Remove duplicates but keep the original order. */
    public static List<String> dedupKeepingOrder(List<String> input) {
        Set<String> seen = new LinkedHashSet<>(input);
        return List.copyOf(seen);
    }

    /** Top-K largest values in O(n log k) with a min-heap of size k. */
    public static List<Integer> topK(List<Integer> values, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int v : values) {
            heap.offer(v);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.stream().sorted((a, b) -> b - a).toList();
    }

    /** Balanced brackets with ArrayDeque as a stack. */
    public static boolean balanced(String text) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : text.toCharArray()) {
            switch (c) {
                case '(', '[', '{' -> stack.push(c);
                case ')' -> { if (stack.isEmpty() || stack.pop() != '(') return false; }
                case ']' -> { if (stack.isEmpty() || stack.pop() != '[') return false; }
                case '}' -> { if (stack.isEmpty() || stack.pop() != '{') return false; }
                default -> { }
            }
        }
        return stack.isEmpty();
    }

    /** Sorted unique values in a range (TreeSet range view). */
    public static Set<Integer> between(List<Integer> values, int fromInclusive, int toExclusive) {
        return new TreeSet<>(values).subSet(fromInclusive, toExclusive);
    }
}
