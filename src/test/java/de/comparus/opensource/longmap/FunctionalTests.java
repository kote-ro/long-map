package de.comparus.opensource.longmap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Functional tests
 * TestMethodOrder is used for simplifying test results representation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTests {

    private static LongMap<Integer> longMap = new LongMapImpl<>();

    @BeforeAll
    static void setUp() {
        longMap.clear();
    }

    @AfterEach
    void clearAfterEach(){
        longMap.clear();
    }

    @Test
    @Order(1)
    void testIsEmpty() {
        assertTrue(longMap.isEmpty());
    }

    @Test
    @Order(2)
    void testAddMakesIsEmptyFalse() {
        longMap.put(5L, 555);
        assertFalse(longMap.isEmpty());
    }

    @Test
    @Order(3)
    public void testSizeForNewMap() {
        assertEquals(0, longMap.size());
    }
    
    @Test
    @Order(4)
    public void testSizeIncrementsWhenAddingElements() {
        longMap.put(22L, 222);
        assertEquals(1, longMap.size());

        longMap.put(33L, 333);
        assertEquals(2, longMap.size());
    }
    
    @Test
    @Order(5)
    void testGetReturnsCorrectValue() {
        longMap.put(44L, 4444);
        longMap.put(55L, 5555);
        assertEquals(4444, longMap.get(44L));
        assertEquals(5555, longMap.get(55L));
    }
    
    @Test
    @Order(6)
    void testReplacesValueWithSameKey() {
        longMap.put(55L, 5);
        longMap.put(55L, 6);

        assertEquals(6, longMap.get(55L));
    }
    
    @Test
    @Order(7)
    void testDoesNotOverwriteSeparateKeysWithSameHash() {
        longMap.put(1, 5);
        longMap.put(2, 6);

        assertEquals(5, longMap.get(1));
        assertEquals(6, longMap.get(2));
    }

    @Test
    @Order(8)
    void testRemoveDoesNotEffectNewMap() {
        longMap.remove(1);

        assertEquals(0, longMap.size());
    }

    @Test
    @Order(9)
    void testRemoveDecrementsSize() {
        longMap.put(1L, 5);
        longMap.put(2L, 6);

        longMap.remove(1L);

        assertEquals(1, longMap.size());

        longMap.remove(2L);

        assertEquals(0, longMap.size());
    }

    @Test
    @Order(10)
    void testContainsKeyForNonExistingKey() {
        longMap.put(1L, 5);
        assertFalse(longMap.containsKey(2L));
    }

    @Test
    @Order(11)
    void testContainsKeyForExistingKey() {
        longMap.put(1L, 5);
        assertTrue(longMap.containsKey(1L));
    }

    @Test
    @Order(12)
    void testContainsKeyForNonExistingValue() {
        longMap.put(1L, 5);
        assertFalse(longMap.containsValue(6));
    }

    @Test
    @Order(13)
    void testContainsKeyForExistingValue() {
        longMap.put(1L, 5);
        assertTrue(longMap.containsValue(5));
    }
}
