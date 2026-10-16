package dev.chris0100.streams;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CollectionChoicesTest {

    @Test
    void dedupKeepsInsertionOrder() {
        assertEquals(List.of("b", "a", "c"), CollectionChoices.dedupKeepingOrder(List.of("b", "a", "b", "c", "a")));
    }

    @Test
    void topKUsesAMinHeap() {
        assertEquals(List.of(9, 8, 7), CollectionChoices.topK(List.of(5, 1, 9, 3, 7, 8, 2), 3));
    }

    @Test
    void balancedBrackets() {
        assertTrue(CollectionChoices.balanced("{[()()]}"));
        assertFalse(CollectionChoices.balanced("{[(])}"));
        assertFalse(CollectionChoices.balanced("(("));
    }

    @Test
    void treeSetRangeView() {
        assertEquals(Set.of(3, 5, 7), CollectionChoices.between(List.of(9, 7, 1, 5, 3, 5), 2, 8));
    }
}
